@file:Suppress("UNUSED")

package org.firstinspires.ftc.phoboscode.auto.azul

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.github.serivesmejia.deltacommander.dsl.deltaSequence
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition.LEFT
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition.MIDDLE
import org.firstinspires.ftc.phoboscode.auto.AutonomoBase
import org.firstinspires.ftc.phoboscode.auto.azul.ParkPosition.*
import org.firstinspires.ftc.phoboscode.command.box.BoxSaveCmd
import org.firstinspires.ftc.phoboscode.command.box.BoxThrowCmd
import org.firstinspires.ftc.phoboscode.command.carousel.ACCarouselRotateForwardCmd
import org.firstinspires.ftc.phoboscode.command.carousel.CarouselStopCmd
import org.firstinspires.ftc.phoboscode.command.intake.IntakeStopCmd
import org.firstinspires.ftc.phoboscode.command.intake.IntakeWithColorSensorCmd
import org.firstinspires.ftc.phoboscode.command.lift.LiftMoveToPosCmd
import org.firstinspires.ftc.phoboscode.lastKnownRobotPose
import org.firstinspires.ftc.phoboscode.subsystem.LiftPosition

enum class StartPosition(
    val startPose: Pose2d,
    val startWobblePose: Pose2d
) {
    DUCKS_NEAREST(
        Pose2d(-39.0, 62.0, Math.toRadians(270.0)), // start
        Pose2d(-23.0, 37.0, Math.toRadians(150.0))
    ),
    WAREHOUSE_NEAREST(
        Pose2d(11.0, 63.0, Math.toRadians(270.0)),
        Pose2d(1.0, 38.8, Math.toRadians(75.0))
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
) : AutonomoBase() {

    val bigWobblePose = Pose2d(0.0, 38.5, Math.toRadians(60.0))

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
                    lineToLinearHeading(Pose2d(-63.6, 62.0, Math.toRadians(195.0)))
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
                        lineTo(Vector2d(-56.0, 55.5))
                        lineToLinearHeading(Pose2d(-24.0, 55.0, Math.toRadians(0.0)))
                    }

                    var currentGrabCubeX = 68.0
                    var minusBigWobblePose = Pose2d()

                    /*
                    Generating repetitive trajectories for each cycle
                     */
                    repeat(cycles) {
                        // to the warehouse
                        splineToSplineHeading(Pose2d(25.0, 63.0, Math.toRadians(0.0)), Math.toRadians(365.0))

                        UNSTABLE_addTemporalMarkerOffset(0.0) {
                            + IntakeWithColorSensorCmd(1.0)
                        }

                        // grab freight
                        splineTo(Vector2d(currentGrabCubeX, 63.2), 0.0)

                        // out of the warehouse
                        lineTo(Vector2d(18.0, 63.2))
                        UNSTABLE_addTemporalMarkerOffset(0.0) {
                            + IntakeStopCmd()
                            + LiftMoveToPosCmd(LiftPosition.HIGH)
                        }

                        UNSTABLE_addTemporalMarkerOffset(1.8) {
                            + freightDropSequence()
                        }
                        // put freight in big wobble
                        splineToSplineHeading(bigWobblePose.minus(minusBigWobblePose), Math.toRadians(270.0))
                        waitSeconds(0.9) // wait for the freight to fall

                        currentGrabCubeX *= 1.06
                        minusBigWobblePose = minusBigWobblePose.plus(Pose2d(-3.0, -0.7))
                    }
                }

                when(parkPosition) {
                    NONE -> this
                    WAREHOUSE -> {
                        // to the warehouse to park
                        splineToSplineHeading(Pose2d(25.0, 63.0, Math.toRadians(0.0)), Math.toRadians(3.0))
                        // park fully
                        lineTo(Vector2d(50.0, 64.0))
                        // in case alliance wants to park too
                        strafeTo(Vector2d(50.0, 40.0))
                    }
                    STORAGE_UNIT -> {
                        lineToSplineHeading(Pose2d(-62.0, 32.0, 0.0))
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