package org.firstinspires.ftc.phoboscode.teleop

import com.github.serivesmejia.deltacommander.command.DeltaInstantCmd
import com.github.serivesmejia.deltacommander.command.DeltaRunCmd
import com.github.serivesmejia.deltacommander.dsl.deltaSequence
import com.github.serivesmejia.deltaevent.gamepad.button.Button
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.phoboscode.PhobosOpMode
import org.firstinspires.ftc.commoncode.command.MecanumFieldCentricDriveCommand
import org.firstinspires.ftc.phoboscode.command.box.BoxSaveCmd
import org.firstinspires.ftc.phoboscode.command.box.BoxThrowCmd
import org.firstinspires.ftc.phoboscode.command.capturret.CapTurretIncrementalMoveCmd
import org.firstinspires.ftc.phoboscode.command.capturret.CapTurretTapeMotorExtendCmd
import org.firstinspires.ftc.phoboscode.command.capturret.CapTurretTapeMotorSaveCmd
import org.firstinspires.ftc.phoboscode.command.capturret.CapTurretTapeMotorStopCmd
import org.firstinspires.ftc.phoboscode.command.carousel.*
import org.firstinspires.ftc.phoboscode.command.intake.IntakeInCmd
import org.firstinspires.ftc.phoboscode.command.intake.IntakeOutCmd
import org.firstinspires.ftc.phoboscode.command.intake.IntakeStopCmd
import org.firstinspires.ftc.phoboscode.command.lift.*
import org.firstinspires.ftc.phoboscode.lastKnownRobotPose
import org.firstinspires.ftc.phoboscode.subsystem.LiftPosition
import kotlin.math.abs

@TeleOp(name = "TeleOp")
class PhobosTeleOp : PhobosOpMode() {

    override fun setup() {
        hardware.drive.poseEstimate = lastKnownRobotPose

        + MecanumFieldCentricDriveCommand(gamepad1, telemetry) // contrar las mecanum con los joysticks del gamepad 1

        superGamepad1.scheduleOnPress(Button.DPAD_LEFT,
            DeltaInstantCmd {
                mecanumSub.resetPose()
            }
        )

        /*
        CAPPING TURRET
         */

        CapTurretIncrementalMoveCmd(
            { gamepad2.left_stick_x.toDouble() * 0.02 }, // yaw servo
            { -gamepad2.left_stick_y.toDouble() * 0.02 }, // pitch servo
        ).schedule(false)

        superGamepad2.scheduleOn(Button.X,
            CapTurretTapeMotorExtendCmd(),
            CapTurretTapeMotorStopCmd()
        )

        superGamepad2.scheduleOn(Button.Y,
            CapTurretTapeMotorSaveCmd(),
            CapTurretTapeMotorStopCmd()
        )

        /*
        CAROUSEL
         */
        superGamepad1.scheduleOn(Button.X,
                ACCarouselRotateBackwardsCmd(),
                ACCarouselStopCmd()
        )

        superGamepad1.scheduleOn(Button.Y,
                ACCarouselRotateForwardCmd(),
                ACCarouselStopCmd()
        )

        /*
        INTAKE
         */

        superGamepad2.scheduleOn(Button.A,
                IntakeInCmd(), // encender el intake cuando se presiona A
                IntakeStopCmd() // apagar el intake cuando se deja de presionar A
        )
        superGamepad2.scheduleOn(Button.B,
                IntakeOutCmd(), // encender el intake en reversa cuando se presiona B
                IntakeStopCmd() // apagar el intake cuando se deja de presionar B
        )

        /*
        LIFT
         */

        superGamepad2.scheduleOnPress(Button.DPAD_UP,
                liftSequence( LiftPosition.HIGH )
        ) { !liftSub.isBusy } // only schedule command if lift is currently not busy from going to another position

        superGamepad2.scheduleOnPress(Button.DPAD_RIGHT,
                liftSequence( LiftPosition.MID )
        ) { !liftSub.isBusy }

        superGamepad2.scheduleOnPress(Button.DPAD_DOWN,
                liftSequence( LiftPosition.LOW ),
        ) { !liftSub.isBusy }

        // controlling the lift with either of the joysticks when it isn't executing the "lift sequence"
        liftSub.defaultCommand = LiftMoveCmd {
            -gamepad2.right_stick_y.toDouble()
        }

        // Sobas le gana
        // no

        /*
        BOX
         */
        superGamepad2.scheduleOnPress(Button.RIGHT_TRIGGER,
                BoxSaveCmd()
        )

        superGamepad2.scheduleOnPress(Button.LEFT_TRIGGER,
                BoxThrowCmd()
        )

        /*
        TELEMETRY LOGGING
         */
        + DeltaRunCmd {
            telemetry.addData("lift pos", hardware.sliderMotor.currentPosition)
            telemetry.addData("carousel power", hardware.carouselMotor.power)
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
        - waitFor { abs(liftCmd.controller.lastError) < 10 }

        - BoxThrowCmd().dontBlock()
        - waitForSeconds(3.0)
        - BoxSaveCmd().dontBlock()

        - LiftZeroPosition(telemetry).stopOn { abs(controller.lastError) < 5 }.dontBlock()
    }

}