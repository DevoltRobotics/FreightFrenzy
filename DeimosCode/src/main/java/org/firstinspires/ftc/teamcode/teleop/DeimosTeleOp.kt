package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.DeimosOpMode
import org.firstinspires.ftc.teamcode.commander.command.MecanumDriveCommand

@TeleOp(name = "TeleOp")
class DeimosTeleOp : DeimosOpMode() {

    override fun initialize() {
        super.initialize()

        + MecanumDriveCommand(gamepad1)
    }

}