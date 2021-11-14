package org.firstinspires.ftc.phoboscode.teleop

import com.github.serivesmejia.deltacommander.command.NoCmd
import com.github.serivesmejia.deltacommander.dsl.deltaSequence
import com.github.serivesmejia.deltaevent.gamepad.button.Button
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.phoboscode.PhobosOpMode
import org.firstinspires.ftc.commoncode.commander.command.MecanumDriveCommand
import org.firstinspires.ftc.phoboscode.commander.command.box.BoxSaveCmd
import org.firstinspires.ftc.phoboscode.commander.command.box.BoxThrowCmd
import org.firstinspires.ftc.phoboscode.commander.command.carousel.CarouselRotateCmd
import org.firstinspires.ftc.phoboscode.commander.command.carousel.CarouselStopCmd
import org.firstinspires.ftc.phoboscode.commander.command.intake.IntakeInCmd
import org.firstinspires.ftc.phoboscode.commander.command.intake.IntakeOutCmd
import org.firstinspires.ftc.phoboscode.commander.command.intake.IntakeStopCmd
import org.firstinspires.ftc.phoboscode.commander.command.lift.*
import org.firstinspires.ftc.phoboscode.commander.subsystem.LiftPosition

@TeleOp(name = "TeleOp")
class PhobosTeleOp : PhobosOpMode() {

    override fun setup() {
        + MecanumDriveCommand(gamepad1) // contrar las mecanum con los joysticks del gamepad 1

        /*
        CAROUSEL
         */
        superGamepad1.scheduleOn(Button.A,
                CarouselRotateCmd(),
                CarouselStopCmd()
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
        )

        superGamepad2.scheduleOn(Button.DPAD_RIGHT,
                liftSequence( LiftPosition.MID )
        )

        superGamepad2.scheduleOn(Button.DPAD_DOWN,
                liftSequence( LiftPosition.LOW )
        )

        // controlling the lift with either of the joysticks when it isn't executing the "lift sequence"
        liftSub.defaultCommand = LiftMoveCmd {
            -eitherStick(gamepad2.left_stick_y, gamepad2.right_stick_y)
        }

        /*
        BOX
         */
        superGamepad2.scheduleOnPress(Button.RIGHT_TRIGGER,
                BoxSaveCmd()
        )

        superGamepad2.scheduleOnPress(Button.LEFT_TRIGGER,
                BoxThrowCmd()
        )
    }

    fun eitherStick(stickValue1: Float, stickValue2: Float) =
            if(stickValue1 != 0f) {
                stickValue1
            } else {
                stickValue2
            }.toDouble()

    private fun liftSequence(liftPosition: LiftPosition) = deltaSequence {
        val liftCommand = LiftMoveToPosCmd(liftPosition)

        - liftCommand.dontBlock()
        - waitFor { liftCommand.controller.onSetpoint() }

        - BoxThrowCmd().dontBlock()
        - waitForSeconds(4.0)
        - BoxSaveCmd().dontBlock()

        - LiftZeroPosition().dontBlock()
    }

}