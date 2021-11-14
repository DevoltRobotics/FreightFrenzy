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

@TeleOp(name = "TeleOp")
class PhobosTeleOp : PhobosOpMode() {

    override fun setup() {
        + MecanumDriveCommand(gamepad1) // contrar las mecanum con los joysticks del gamepad 1

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
        CAROUSEL
         */

        superGamepad2.scheduleOn(Button.Y,
                CarouselRotateCmd(),
                CarouselStopCmd()
        )

        /*
        LIFT
         */

        superGamepad2.scheduleOn(Button.DPAD_UP,
                liftSequence( LiftHighPosition() ),
                NoCmd
        )

        superGamepad2.scheduleOn(Button.DPAD_RIGHT,
                liftSequence( LiftMiddlePosition() ),
                NoCmd
        )

        superGamepad2.scheduleOn(Button.DPAD_DOWN,
                liftSequence( LiftLowPosition() ),
                NoCmd
        )
    }

    private fun liftSequence(liftCommand: LiftMoveToPosCmd) = deltaSequence {
        - liftCommand.dontBlock()
        - waitFor { liftCommand.controller.onSetpoint() }

        - BoxThrowCmd().dontBlock()
        - waitForSeconds(4.0)
        - BoxSaveCmd().dontBlock()

        - LiftZeroPosition().dontBlock()
    }

}