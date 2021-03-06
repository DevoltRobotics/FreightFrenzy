package org.firstinspires.ftc.phoboscode.teleop

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.github.serivesmejia.deltacommander.command.DeltaInstantCmd
import com.github.serivesmejia.deltacommander.command.DeltaRunCmd
import com.github.serivesmejia.deltacommander.dsl.deltaSequence
import com.github.serivesmejia.deltaevent.gamepad.button.Button
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.commoncode.command.MecanumFieldCentricDriveCommand
import org.firstinspires.ftc.phoboscode.PhobosOpMode
import org.firstinspires.ftc.phoboscode.command.box.BoxSaveCmd
import org.firstinspires.ftc.phoboscode.command.box.BoxThrowCmd
import org.firstinspires.ftc.phoboscode.command.caparm.CapArmMoveCmd
import org.firstinspires.ftc.phoboscode.command.carousel.*
import org.firstinspires.ftc.phoboscode.command.intake.IntakeInCmd
import org.firstinspires.ftc.phoboscode.command.intake.IntakeOutCmd
import org.firstinspires.ftc.phoboscode.command.intake.IntakeStopCmd
import org.firstinspires.ftc.phoboscode.command.lift.*
import org.firstinspires.ftc.phoboscode.lastKnownRobotPose
import org.firstinspires.ftc.phoboscode.subsystem.LiftPosition
import kotlin.math.abs

abstract class PhobosTeleOp(val plusDriverAngle: Double) : PhobosOpMode() {

    override fun setup() {
        hardware.drive.poseEstimate = lastKnownRobotPose.plus(Pose2d(0.0, 0.0, plusDriverAngle))

        + MecanumFieldCentricDriveCommand(gamepad1, telemetry) // contrar las mecanum con los joysticks del gamepad 1

        superGamepad1.scheduleOnPress(Button.DPAD_LEFT,
            DeltaInstantCmd {
                mecanumSub.resetPose()
            }
        )

        /*
        CAROUSEL
         */
        superGamepad2.scheduleOn(
            Button.RIGHT_BUMPER,
            ACCarouselRotateBackwardsCmd(),
            ACCarouselStopCmd()
        )

        superGamepad2.scheduleOn(
            Button.LEFT_BUMPER,
            ACCarouselRotateForwardCmd(),
            ACCarouselStopCmd()
        )

        /*
        INTAKE
         */

        superGamepad2.scheduleOn(

            Button.A,
            IntakeInCmd(), // encender el intake cuando se presiona A
            IntakeStopCmd() // apagar el intake cuando se deja de presionar A
        )
        superGamepad2.scheduleOn(
            Button.B,
            IntakeOutCmd(), // encender el intake en reversa cuando se presiona B
            IntakeStopCmd() // apagar el intake cuando se deja de presionar B
        )

        superGamepad2.scheduleOn(Button.X, intakeSub.pushServoSequence())

        /*
        LIFT
         */

        superGamepad2.scheduleOnPress(
            Button.DPAD_UP,
            liftSequence(LiftPosition.HIGH)
        ) { !liftSub.isBusy } // only schedule command if lift is currently not busy from going to another position

        superGamepad2.scheduleOnPress(
            Button.DPAD_RIGHT,
            liftSequence(LiftPosition.MID)
        ) { !liftSub.isBusy }

        superGamepad2.scheduleOnPress(
            Button.DPAD_DOWN,
            liftSequence(LiftPosition.LOW),
        ) { !liftSub.isBusy }

        // controlling the lift with either of the joysticks when it isn't executing the "lift sequence"
        liftSub.defaultCommand = LiftMoveCmd {
            -gamepad2.right_stick_y.toDouble()
        }


        /*
        BOX
         */
        superGamepad2.scheduleOnPress(Button.LEFT_TRIGGER,
            BoxThrowCmd()
        )

        superGamepad2.scheduleOnPress(Button.RIGHT_TRIGGER,
            BoxSaveCmd()
        )

        /*
        CAP ARM
         */

        + CapArmMoveCmd { -gamepad2.left_stick_y.toDouble() * 0.2 }

        /*
        TELEMETRY LOGGING
         */
        + DeltaRunCmd {
            val liftEncoderPos = hardware.sliderMotor.currentPosition
            
            try {
                val liftPositions = LiftPosition.values()
                var liftHeight = LiftPosition.HIGH
            
                for(val (i, position) in liftPositions.withIndex()) {
                    if(i == 0) {
                        if(position.position() <= liftEncoderPos) {
                            liftHeight = position
                            break
                        }
                    } else {
                        val beforePosition = liftPositions[i]
                    
                        if(beforePosition.position() > liftEncoderPos && position.position() <= liftEncoderPos) {
                            liftHeight = position
                            break
                        }
                    }
                }
            
                telemetry.addData("lift height", liftHeight)
            } catch(e: Exception) {}
            
            telemetry.addData("lift pos", liftEncoderPos)
            
            telemetry.addData("carousel power", hardware.carouselMotor.power)
            telemetry.addData("intake distance", intakeSub.lastDistance)
            telemetry.addData("intake pushing", intakeSub.pushing)

            telemetry.addData("fl", hardware.drive.leftFront.currentPosition)
            telemetry.addData("fr", hardware.drive.rightFront.currentPosition)
            telemetry.addData("bl", hardware.drive.leftRear.currentPosition)
            telemetry.addData("br", hardware.drive.rightRear.currentPosition)

            telemetry.update()
        }
    }

    fun eitherStick(stickValue1: Float, stickValue2: Float) =
            if(stickValue1 != 0f) {
                stickValue1
            } else {
                stickValue2
            }.toDouble()

    private fun liftSequence(liftPosition: LiftPosition) = deltaSequence {
        val liftCmd = LiftMoveToPosCmd(liftPosition, telemetry)

        - liftCmd.dontBlock()
        - waitFor { gamepad2.right_trigger > 0.2f }

        - BoxThrowCmd().dontBlock()

        - waitFor { gamepad2.left_trigger > 0.5f }

        - BoxSaveCmd().dontBlock()

        - LiftZeroPosition(telemetry).stopOn { abs(controller.lastError) < 5 }.dontBlock()
    }

}

@TeleOp(name = "TeleOp Rojo")
class TeleOpRojo : PhobosTeleOp(Math.toRadians(-90.0))


@TeleOp(name = "TeleOp Azul")
class TeleOpAzul : PhobosTeleOp(Math.toRadians(90.0))
