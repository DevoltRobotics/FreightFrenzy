@file:Suppress("UNUSED")

package org.firstinspires.ftc.phoboscode.auto.rojo

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.github.serivesmejia.deltacommander.dsl.deltaSequence
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition.*
import org.firstinspires.ftc.phoboscode.auto.AutonomoBase
import org.firstinspires.ftc.phoboscode.auto.rojo.ParkPosition.*
import org.firstinspires.ftc.phoboscode.command.box.BoxSaveCmd
import org.firstinspires.ftc.phoboscode.command.box.BoxThrowCmd
import org.firstinspires.ftc.phoboscode.command.carousel.ACCarouselRotateForwardCmd
import org.firstinspires.ftc.phoboscode.command.carousel.CarouselMoveCmd
import org.firstinspires.ftc.phoboscode.command.carousel.CarouselStopCmd
import org.firstinspires.ftc.phoboscode.command.intake.IntakeStopCmd
import org.firstinspires.ftc.phoboscode.command.intake.IntakeWithColorSensorCmd
import org.firstinspires.ftc.phoboscode.command.lift.LiftMoveToPosCmd
import org.firstinspires.ftc.phoboscode.rr.drive.DriveConstants
import org.firstinspires.ftc.phoboscode.rr.drive.SampleMecanumDrive
import org.firstinspires.ftc.phoboscode.subsystem.LiftPosition

enum class StartPosition(
    val startPose: Pose2d,
    val startWobblePose: Pose2d
) {
    DUCKS_NEAREST(
        Pose2d(-37.0, -62.0, Math.toRadians(270.0)), // start
        Pose2d(-28.0, -34.3, Math.toRadians(220.0)) // start big wobble pose
    ),
    WAREHOUSE_NEAREST(
        Pose2d(13.0, -62.0, Math.toRadians(270.0)),
        Pose2d(1.4, -34.0, Math.toRadians(310.0))
    )
}

enum class ParkPosition {
    NONE, WAREHOUSE, STORAGE_UNIT
}

enum class Alliance {
    RED, BLUE
}

abstract class AutonomoCompletoRojo(
    val startPosition: StartPosition,
    val parkPosition: ParkPosition = WAREHOUSE,
    val doDucks: Boolean = true,
    val cycles: Int = 4
) : AutonomoBase(true, false) {

    val bigWobblePose = Pose2d(0.5, -33.6, Math.toRadians(337.0))

    override fun setup() {
        super.setup()

        drive.poseEstimate = startPosition.startPose
        liftSub.stopAndReset()
    }

    override fun sequence(teamMarkerPosition: TeamMarkerPosition) =
        drive.trajectorySequenceBuilder(startPosition.startPose).run {

            UNSTABLE_addTemporalMarkerOffset(1.0) {
                + CarouselMoveCmd(0.3)
            }

            UNSTABLE_addTemporalMarkerOffset(2.0) {
                + CarouselStopCmd()
            }

            lineToSplineHeading(startPosition.startWobblePose)

            waitSeconds(1.6)

            if(doDucks) {
                // duck spinny boi
                lineToSplineHeading(Pose2d(-63.5, -59.0, Math.toRadians(180.0)))
                UNSTABLE_addTemporalMarkerOffset(0.0) {
                    + ACCarouselRotateForwardCmd()
                }
                waitSeconds(3.0)
                UNSTABLE_addTemporalMarkerOffset(0.0) {
                    + CarouselStopCmd()
                }
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

            //if(doDucks) {
            //    splineToSplineHeading(Pose2d(-33.0, 10.0, Math.toRadians(230.0)), Math.toRadians(90.0))
            // } else {
            lineToSplineHeading(startPosition.startWobblePose)
            //}

            waitSeconds(1.4)

            var goInsideY = -70.8


            if(cycles >= 1) {
                if(doDucks) {
                    // to the warehouse
                    lineTo(Vector2d(-56.0, -55.5))
                    lineToLinearHeading(Pose2d(-24.0, -55.0, Math.toRadians(0.0)))
                }

                var currentGrabCubeX = 60.0
                var minusBigWobblePose = Pose2d(2.0, 0.4)

                /*
                Generating repetitive trajectories for each cycle
                 */
                repeat(cycles) {
                    // align to wall
                    lineToSplineHeading(
                        Pose2d(-1.0, -56.0, Math.toRadians(0.0))//,
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

                    UNSTABLE_addTemporalMarkerOffset(1.1) {
                        + freightDropSequence()
                    }
                    // put freight in big wobble
                    splineToSplineHeading(bigWobblePose.minus(minusBigWobblePose), Math.toRadians(90.0))

                    waitSeconds(0.7) // wait for the freight to fall

                    currentGrabCubeX *= 1.095
                    goInsideY *= 1.09
                    minusBigWobblePose = minusBigWobblePose.plus(Pose2d(-16.0, -2.0, Math.toRadians(-5.0)))
                }
            }

            when(parkPosition) {
                NONE -> this
                WAREHOUSE -> {
                    goInsideY *= 1.08

                    // align to wall
                    lineToSplineHeading(
                        Pose2d(-1.0, -56.0, Math.toRadians(0.0)),
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL * 1.08, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                    )

                    UNSTABLE_addTemporalMarkerOffset(0.0) {
                        + IntakeWithColorSensorCmd(1.0)
                    }

                    // go inside (that's what she said)
                    splineToConstantHeading(Vector2d(41.0, goInsideY), Math.toRadians(0.0),
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL * 1.08, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                    )

                    // park fully
                    splineToConstantHeading(Vector2d(57.0, goInsideY), Math.toRadians(0.0),
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL * 1.08, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                    )

                    // in case alliance wants to park too
                    // splineToSplineHeading(Pose2d(57.0, goInsideY + 8.0, Math.toRadians(270.0)), Math.toRadians(180.0))
                }// mechrams el que lo copie
                STORAGE_UNIT -> {
                    lineToSplineHeading(Pose2d(-65.0, -30.0, 0.0))
                }
            }
        }.build()

    private fun freightDropSequence() = deltaSequence {
        - BoxThrowCmd().dontBlock()
        - waitForSeconds(2.0)
        - BoxSaveCmd().dontBlock()

        - LiftMoveToPosCmd(LiftPosition.ZERO).dontBlock()
    }

}