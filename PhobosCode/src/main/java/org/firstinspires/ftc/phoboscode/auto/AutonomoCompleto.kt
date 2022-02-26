@file:Suppress("UNUSED")

package org.firstinspires.ftc.phoboscode.auto

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.github.serivesmejia.deltacommander.dsl.deltaSequence
import org.firstinspires.ftc.commoncode.util.angleAdd
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition.*
import org.firstinspires.ftc.phoboscode.auto.ParkPosition.*
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

enum class ParkPosition {
    NONE, WAREHOUSE, STORAGE_UNIT
}

enum class Alliance {
    RED, BLUE
}

abstract class AutonomoCompleto(
        val startPosition: Pose2d,
        val startWobblePose: Pose2d? = null,
        val doDucks: Boolean = true,
        val cycles: Int = 4,
        val parkPosition: ParkPosition = WAREHOUSE,
        val alliance: Alliance = Alliance.RED
) : AutonomoBase() {

    val bigWobblePose = Pose2d(-10.4, -35.5, Math.toRadians(300.0)).invertIfNeeded()

    override fun runOpMode() {
        super.runOpMode()

        lastKnownRobotPose = drive.poseEstimate
    }

    override fun setup() {
        super.setup()

        drive.poseEstimate = startPosition
        liftSub.stopAndReset()
    }

    override fun sequence(teamMarkerPosition: TeamMarkerPosition) =
            drive.trajectorySequenceBuilder(startPosition).run {
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
                lineToLinearHeading(startWobblePose?.invertIfNeeded() ?: bigWobblePose)
                waitSeconds(2.0)

                if(doDucks) {
                    // duck spinny boi
                    lineToLinearHeading(Pose2d(-66.5, -59.0, Math.toRadians(180.0)).invertIfNeeded())
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
                        lineTo(Vector2d(-56.0, -55.5).invertIfNeeded())
                        lineToLinearHeading(Pose2d(-24.0, -55.0, Math.toRadians(0.0)).invertIfNeeded())
                    }

                    var currentGrabCubeX = 55.4
                    var minusBigWobblePose = Pose2d()

                    /*
                    Generating repetitive trajectories for each cycle
                     */
                    repeat(cycles) {
                        // to the warehouse
                        splineToSplineHeading(Pose2d(25.0, -63.7, Math.toRadians(90.0)).invertIfNeeded(), 0.0)
                        UNSTABLE_addTemporalMarkerOffset(0.0) {
                            + IntakeWithColorSensorCmd(1.0)
                        }

                        // grab freight
                        lineTo(Vector2d(currentGrabCubeX, -63.9).invertIfNeeded(),
                            SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL * 0.7, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                            SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL * 0.8)
                        )

                        // out of the warehouse
                        lineTo(Vector2d(23.0, -64.0).invertIfNeeded())
                        UNSTABLE_addTemporalMarkerOffset(0.0) {
                            + IntakeStopCmd()
                            + LiftMoveToPosCmd(LiftPosition.HIGH)
                        }

                        UNSTABLE_addTemporalMarkerOffset(1.8) {
                            + freightDropSequence()
                        }
                        // put freight in big wobble
                        splineToSplineHeading(bigWobblePose.minus(minusBigWobblePose), Math.toRadians((180.0).invertDegIfNeeded()))
                        waitSeconds(0.9) // wait for the freight to fall

                        currentGrabCubeX *= 1.087

                        minusBigWobblePose = minusBigWobblePose.plus(Pose2d(-2.0, 0.6))
                    }
                }

                when(parkPosition) {
                    NONE -> this
                    WAREHOUSE -> {
                        // to the warehouse to park
                        splineToSplineHeading(Pose2d(30.0, -64.0, Math.toRadians(0.0)).invertIfNeeded(), 0.0)
                        // park fully
                        lineTo(Vector2d(45.0, -64.0).invertIfNeeded())
                        // in case alliance wants to park too
                        strafeTo(Vector2d(40.0, -44.0).invertIfNeeded())
                    }
                    STORAGE_UNIT -> {
                        lineToSplineHeading(Pose2d(-62.0, -32.0, 0.0).invertIfNeeded())
                    }
                }
            }.build()

    private fun freightDropSequence() = deltaSequence {
        - BoxThrowCmd().dontBlock()
        - waitForSeconds(3.0)
        - BoxSaveCmd().dontBlock()

        - LiftMoveToPosCmd(LiftPosition.ZERO).dontBlock()
    }

    fun Vector2d.invertIfNeeded() = if(alliance == Alliance.BLUE) {
        Vector2d(x, -y)
    } else this


    fun Pose2d.invertIfNeeded() = if(alliance == Alliance.BLUE) {
        Pose2d(x, -y, heading.invertRadIfNeeded())
    } else this

    fun Double.invertRadIfNeeded() = if(alliance == Alliance.BLUE) angleAdd(Math.toDegrees(this), 180.0) else this
    fun Double.invertDegIfNeeded() = if(alliance == Alliance.BLUE) angleAdd(this, 180.0) else this

}