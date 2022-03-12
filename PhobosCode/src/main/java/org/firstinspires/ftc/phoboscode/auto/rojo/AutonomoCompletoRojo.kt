@file:Suppress("UNUSED")

package org.firstinspires.ftc.phoboscode.auto.rojo

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.github.serivesmejia.deltacommander.dsl.deltaSequence
import org.firstinspires.ftc.commoncode.util.angleAdd
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition.*
import org.firstinspires.ftc.phoboscode.auto.AutonomoBase
import org.firstinspires.ftc.phoboscode.auto.rojo.ParkPosition.*
import org.firstinspires.ftc.phoboscode.command.box.BoxSaveCmd
import org.firstinspires.ftc.phoboscode.command.box.BoxThrowCmd
import org.firstinspires.ftc.phoboscode.command.carousel.ACCarouselRotateForwardCmd
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
        Pose2d(-35.0, -62.0, Math.toRadians(90.0)), // start
        Pose2d(-36.0, -34.8, Math.toRadians(210.0)) // start big wobble pose
    ),
    WAREHOUSE_NEAREST(
        Pose2d(1.0, -62.0, Math.toRadians(90.0)),
        Pose2d(-10.2, -34.0, Math.toRadians(300.0))
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
    val alliance: Alliance = Alliance.RED,
    val doDucks: Boolean = true,
    val cycles: Int = 4
) : AutonomoBase() {

    val bigWobblePose = Pose2d(-10.2, -34.0, Math.toRadians(300.0))

    override fun setup() {
        super.setup()

        drive.poseEstimate = startPosition.startPose
        liftSub.stopAndReset()
    }

    override fun update() {
        lastKnownRobotPose = drive.poseEstimate
    }

    override fun sequence(teamMarkerPosition: TeamMarkerPosition) =
        drive.trajectorySequenceBuilder(startPosition.startPose).run {
            // put X cube in big wobble
            UNSTABLE_addTemporalMarkerOffset(0.0) {
                + LiftMoveToPosCmd(when(teamMarkerPosition) { // mapping barcode position to lift height
                    LEFT -> LiftPosition.LOW
                    MIDDLE -> LiftPosition.MID
                    else -> LiftPosition.HIGH
                })
            }
            UNSTABLE_addTemporalMarkerOffset(2.5) {
                + freightDropSequence()
            }

            lineToSplineHeading(startPosition.startWobblePose)

            waitSeconds(2.0)

            if(doDucks) {
                // duck spinny boi
                lineToLinearHeading(Pose2d(-66.5, -59.0, Math.toRadians(180.0)))
                UNSTABLE_addTemporalMarkerOffset(0.0) {
                    + ACCarouselRotateForwardCmd()
                }
                waitSeconds(3.0)
                UNSTABLE_addTemporalMarkerOffset(0.0) {
                    + CarouselStopCmd()
                }
            }

            if(cycles >= 1) {
                if(doDucks) {
                    // to the warehouse
                    lineTo(Vector2d(-56.0, -55.5))
                    lineToLinearHeading(Pose2d(-24.0, -55.0, Math.toRadians(0.0)))
                }

                var currentGrabCubeX = 52.5
                var minusBigWobblePose = Pose2d()

                /*
                Generating repetitive trajectories for each cycle
                 */
                repeat(cycles) {
                    // to the warehouse
                    splineToSplineHeading(
                        Pose2d(25.0, -63.3, Math.toRadians(0.0)),
                        Math.toRadians((-5.0))
                    )

                    UNSTABLE_addTemporalMarkerOffset(0.0) {
                        + IntakeWithColorSensorCmd(1.0)
                    }

                    // grab freight
                    splineTo(Vector2d(currentGrabCubeX, -63.2), 0.0)

                    // out of the warehouse
                    lineTo(Vector2d(18.0, -63.2))
                    UNSTABLE_addTemporalMarkerOffset(0.0) {
                        + IntakeStopCmd()
                        + LiftMoveToPosCmd(LiftPosition.HIGH)
                    }

                    UNSTABLE_addTemporalMarkerOffset(1.8) {
                        + freightDropSequence()
                    }
                    // put freight in big wobble
                    splineToSplineHeading(bigWobblePose.minus(minusBigWobblePose), Math.toRadians((90.0)))
                    waitSeconds(0.9) // wait for the freight to fall

                    currentGrabCubeX *= 1.06
                    minusBigWobblePose = minusBigWobblePose.plus(Pose2d(-3.0, 0.7))
                }
            }

            when(parkPosition) {
                NONE -> this
                WAREHOUSE -> {
                    // to the warehouse to park
                    splineToSplineHeading(Pose2d(30.0, -64.0, Math.toRadians(0.0)), 0.0)
                    // park fully
                    lineTo(Vector2d(50.0, -64.0))
                    // in case alliance wants to park too
                    strafeTo(Vector2d(50.0, -40.0))
                }
                STORAGE_UNIT -> {
                    lineToSplineHeading(Pose2d(-62.0, -32.0, 0.0))
                }
            }
        }.build()

    private fun freightDropSequence() = deltaSequence {
        - BoxThrowCmd().dontBlock()
        - waitForSeconds(3.0)
        - BoxSaveCmd().dontBlock()

        - LiftMoveToPosCmd(LiftPosition.ZERO).dontBlock()
    }

}