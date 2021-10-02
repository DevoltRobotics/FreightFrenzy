package org.firstinspires.ftc.deimoscode.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.deimoscode.DeimosOpMode
import org.firstinspires.ftc.commoncode.commander.command.MecanumDriveCommand

@TeleOp(name = "TeleOp")
class DeimosTeleOp : DeimosOpMode() {

    override fun initialize() {
        super.initialize()

        + MecanumDriveCommand(gamepad1)
    }

}