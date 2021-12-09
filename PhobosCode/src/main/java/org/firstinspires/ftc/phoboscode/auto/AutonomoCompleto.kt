@file:Suppress("UNUSED")

package org.firstinspires.ftc.phoboscode.auto

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.github.serivesmejia.deltacommander.dsl.deltaSequence
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition.*
import org.firstinspires.ftc.phoboscode.commander.command.box.BoxSaveCmd
import org.firstinspires.ftc.phoboscode.commander.command.box.BoxThrowCmd
import org.firstinspires.ftc.phoboscode.commander.command.carousel.CarouselRotateForwardCmd
import org.firstinspires.ftc.phoboscode.commander.command.carousel.CarouselStopCmd
import org.firstinspires.ftc.phoboscode.commander.command.intake.IntakeInCmd
import org.firstinspires.ftc.phoboscode.commander.command.intake.IntakeStopCmd
import org.firstinspires.ftc.phoboscode.commander.command.lift.LiftMoveToPosCmd
import org.firstinspires.ftc.phoboscode.commander.subsystem.LiftPosition

abstract class AutonomoCompleto(
        val startPosition: Pose2d,
        val doDucks: Boolean = true,
        val cycles: Int = 4
) : AutonomoBase() {

    val bigWobblePose = Pose2d(-11.0, -43.0, Math.toRadians(-90.0))

    override fun setup() {
        super.setup()
        drive.poseEstimate = startPosition
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
                UNSTABLE_addTemporalMarkerOffset(0.5) {
                    + freightDropSequence()
                }
                lineToLinearHeading(bigWobblePose)
                waitSeconds(2.0)

                if(doDucks) {
                    // duck spinny boi
                    lineToLinearHeading(Pose2d(-60.0, -60.0, Math.toRadians(170.0)))
                    UNSTABLE_addTemporalMarkerOffset(0.0) {
                        + CarouselRotateForwardCmd()
                    }
                    waitSeconds(4.0)
                    UNSTABLE_addTemporalMarkerOffset(0.0) {
                        + CarouselStopCmd()
                    }
                }

                if(cycles >= 1) {
                    if(doDucks) {
                        // to the warehouse
                        lineTo(Vector2d(-56.0, -56.0))
                        lineToLinearHeading(Pose2d(-24.0, -55.0, Math.toRadians(0.0)))
                    }

                    /*
                    Generating repetitive trajectories for each cycle
                     */
                    repeat(cycles) {
                        // to the warehouse
                        splineToSplineHeading(Pose2d(23.0, -64.0, Math.toRadians(0.0)), 0.0)
                        UNSTABLE_addTemporalMarkerOffset(0.0) {
                            + IntakeInCmd()
                        }

                        // grab freight
                        lineTo(Vector2d(50.0, -64.0))
                        // out of the warehouse
                        lineTo(Vector2d(23.0, -64.0))
                        UNSTABLE_addTemporalMarkerOffset(0.0) {
                            + IntakeStopCmd()
                            + LiftMoveToPosCmd(LiftPosition.HIGH)
                        }

                        UNSTABLE_addTemporalMarkerOffset(2.0) {
                            + freightDropSequence()
                        }
                        // put freight in big wobble
                        splineToSplineHeading(bigWobblePose, Math.toRadians(90.0))
                        waitSeconds(0.8) // wait for the freight to fall
                    }
                }

                // to the warehouse to park
                splineToSplineHeading(Pose2d(23.0, -64.0, Math.toRadians(0.0)), 0.0)
                // park fully
                lineTo(Vector2d(40.0, -64.0))
                // in case alliance wants to park too
                strafeTo(Vector2d(40.0, -46.0))
            }.build()

    private fun freightDropSequence() = deltaSequence {
        - BoxThrowCmd().dontBlock()
        - waitForSeconds(3.0)
        - BoxSaveCmd().dontBlock()

        - LiftMoveToPosCmd(LiftPosition.ZERO).dontBlock()
    }

}

@Autonomous(name = "R-Completo Izquierda Pato", group = "Final")
class AutonomoCompletoIzquierdaPato : AutonomoCompleto(
        Pose2d(-35.0, -62.0, Math.toRadians(90.0)), // pose inicial
        cycles = 2
)
@Autonomous(name = "R-Completo Izquierda", group = "Final")
class AutonomoCompletoIzquierda : AutonomoCompleto(
        Pose2d(-35.0, -62.0, Math.toRadians(90.0)), // pose inicial
        doDucks = false
)

@Autonomous(name = "R-Completo Izquierda Pato", group = "Final")
class AutonomoCompletoDerechaPato : AutonomoCompleto(
        Pose2d(1.0, -62.0, Math.toRadians(90.0)), // pose inicial
        cycles = 2
)
@Autonomous(name = "R-Completo Derecha", group = "Final")
class AutonomoCompletoDerecha : AutonomoCompleto(
        Pose2d(1.0, -62.0, Math.toRadians(90.0)), // pose inicial
        doDucks = false
)