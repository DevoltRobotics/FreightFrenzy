package org.firstinspires.ftc.phoboscode.teleop

import com.github.serivesmejia.deltaevent.gamepad.button.Button
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.phoboscode.PhobosOpMode
import org.firstinspires.ftc.commoncode.commander.command.MecanumDriveCommand
import org.firstinspires.ftc.phoboscode.commander.command.MoveTestServoCmd

@TeleOp(name = "TeleOp")
class PhobosTeleOp : PhobosOpMode() {

    override fun initialize() {
        super.initialize()

        + MecanumDriveCommand(gamepad1)

        superGamepad1.toggleScheduleOn(Button.A,
                MoveTestServoCmd(1.0),
                MoveTestServoCmd(0.0)
        )
    }

}