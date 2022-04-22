@file:Suppress("UNUSED")

package org.firstinspires.ftc.phoboscode.auto.azul

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.github.serivesmejia.deltacommander.dsl.deltaSequence
import org.firstinspires.ftc.commoncode.vision.TeamMarkerAprilTagPipeline
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition.LEFT
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition.MIDDLE
import org.firstinspires.ftc.phoboscode.auto.AutonomoBase
import org.firstinspires.ftc.phoboscode.auto.azul.ParkPosition.*
import org.firstinspires.ftc.phoboscode.command.box.BoxSaveCmd
import org.firstinspires.ftc.phoboscode.command.box.BoxThrowCmd
import org.firstinspires.ftc.phoboscode.command.carousel.ACCarouselRotateBackwardsCmd
import org.firstinspires.ftc.phoboscode.command.carousel.ACCarouselRotateForwardCmd
import org.firstinspires.ftc.phoboscode.command.carousel.CarouselMoveCmd
import org.firstinspires.ftc.phoboscode.command.carousel.CarouselStopCmd
import org.firstinspires.ftc.phoboscode.command.intake.IntakeStopCmd
import org.firstinspires.ftc.phoboscode.command.intake.IntakeWithColorSensorCmd
import org.firstinspires.ftc.phoboscode.command.lift.LiftMoveToPosCmd
import org.firstinspires.ftc.phoboscode.lastKnownRobotPose
import org.firstinspires.ftc.phoboscode.rr.drive.DriveConstants
import org.firstinspires.ftc.phoboscode.rr.drive.SampleMecanumDrive
import org.firstinspires.ftc.phoboscode.subsystem.LiftPosition

enum class StartPosition(
    val startPose: Pose2d,
    val startWobblePose: Pose2d
) {
    DUCKS_NEAREST(
        Pose2d(-37.0, 62.0, Math.toRadians(90.0)), // start
        Pose2d(-28.0, 32.5, Math.toRadians(130.0)) // start big wobble pose
    ),
    WAREHOUSE_NEAREST(
        Pose2d(13.0, 62.0, Math.toRadians(90.0)),
        Pose2d(1.4, 29.5, Math.toRadians(55.0))
    )
}

enum class ParkPosition {
    NONE, WAREHOUSE, STORAGE_UNIT
}

abstract class AutonomoCompletoAzul(
        val startPosition: StartPosition,
        val parkPosition: ParkPosition = WAREHOUSE,
        val doDucks: Boolean = true,
        val cycles: Int = 4
) : AutonomoBase(useOneDivider = startPosition == StartPosition.WAREHOUSE_NEAREST) {

    val bigWobblePose = Pose2d(0.2, 32.3, Math.toRadians(55.0))

    override fun setup() {
        super.setup()

        drive.poseEstimate = startPosition.startPose
        liftSub.stopAndReset()
    }

    override fun sequence(teamMarkerPosition: TeamMarkerPosition) =
        drive.trajectorySequenceBuilder(startPosition.startPose).run {
            UNSTABLE_addTemporalMarkerOffset(1.0) {
                + CarouselMoveCmd(0.7)
            }
            UNSTABLE_addTemporalMarkerOffset(1.4) {
                + CarouselStopCmd()
            }

            if (doDucks) {
                // duck spinny boi
                lineToSplineHeading(Pose2d(-65.0, 62.0, Math.toRadians(55.0)))

                UNSTABLE_addTemporalMarkerOffset(0.0) {
                    + ACCarouselRotateBackwardsCmd()
                }
                waitSeconds(3.0)
                UNSTABLE_addTemporalMarkerOffset(0.0) {
                    + CarouselStopCmd()
                }

                //|
            splineToSplineHeading(Pose2d(-66.0, 17.0, Math.toRadians(90.0)), Math.toRadians(0.0))
            }

            // put X cube in big wobble
            UNSTABLE_addTemporalMarkerOffset(0.8) {
                + LiftMoveToPosCmd(
                    when (teamMarkerPosition) { // mapping barcode position to lift height
                        LEFT -> LiftPosition.LOW
                        MIDDLE -> LiftPosition.MID
                        else -> LiftPosition.HIGH
                    }
                )
            }
            UNSTABLE_addTemporalMarkerOffset(1.35) {
                + freightDropSequence()
            }

            if(doDucks) {
                splineToSplineHeading(Pose2d(-33.0, 10.0, Math.toRadians(230.0)), Math.toRadians(90.0))
             } else {
                lineToSplineHeading(startPosition.startWobblePose)
            }

            waitSeconds(1.4)

            var goInsideY = 68.8
            var currentGrabCubeX = 60.7

            if (cycles >= 1) {
                if (doDucks) {
                    // to the warehouse
                    lineTo(Vector2d(-56.0, 55.5))
                    lineToLinearHeading(Pose2d(-24.0, 55.0, Math.toRadians(0.0)))
                }

                var minusBigWobblePose = Pose2d(-10.0, -1.5)

                /*
                Generating repetitive trajectories for each cycle
                 */
                repeat(cycles) {
                    // align to wall
                    lineToSplineHeading(
                        Pose2d(-1.0, 60.0, Math.toRadians(0.0))//,
                        //Math.toRadians(0.0)
                    )

                    UNSTABLE_addTemporalMarkerOffset(0.0) {
                        + IntakeWithColorSensorCmd(1.0)
                    }

                    // go inside
                    splineToConstantHeading(Vector2d(41.0, goInsideY), Math.toRadians(0.0))

                    // go even further inside
                    lineTo(Vector2d(currentGrabCubeX, goInsideY))

                    waitSeconds(0.2)

                    // out of the warehouse
                    lineTo(Vector2d(5.0, goInsideY))
                    UNSTABLE_addTemporalMarkerOffset(0.0) {
                        + IntakeStopCmd()
                        + LiftMoveToPosCmd(LiftPosition.HIGH)
                    }

                    UNSTABLE_addTemporalMarkerOffset(1.8) {
                        + freightDropSequence()
                    }
                    // put freight in big wobble
                    splineToSplineHeading(bigWobblePose.minus(minusBigWobblePose), Math.toRadians(270.0))

                    waitSeconds(1.2) // wait for the freight to fall

                    currentGrabCubeX *= 1.11
                    goInsideY *= 1.09
                    minusBigWobblePose = minusBigWobblePose.plus(Pose2d(-20.0, -7.0))
                }
            }

            when (parkPosition) {
                NONE -> this
                WAREHOUSE -> {
                    goInsideY *= 0.8

                    // align to wall
                    lineToSplineHeading(
                        Pose2d(-1.0, 56.0, Math.toRadians(0.0))//,
                        //Math.toRadians(0.0)
                    )

                    // go inside (that's what she said)
                    splineToConstantHeading(Vector2d(41.0, goInsideY), Math.toRadians(0.0))

                    // go even further inside
                    lineTo(Vector2d(currentGrabCubeX, goInsideY))
                }// mechrams el que lo copie
                STORAGE_UNIT -> {
                    if(doDucks) {
                        //splineToConstantHeading(Vector2d(-67.8, 10.0), Math.toRadians(0.0))
                    }

                    lineToSplineHeading(Pose2d(-69.0, 40.0, 0.0))
                }
            }
        }.build()

    private fun freightDropSequence() = deltaSequence {
        - BoxThrowCmd().dontBlock()
        - waitForSeconds(2.1)
        - BoxSaveCmd().dontBlock()

        - LiftMoveToPosCmd(LiftPosition.ZERO).dontBlock()
    }

}