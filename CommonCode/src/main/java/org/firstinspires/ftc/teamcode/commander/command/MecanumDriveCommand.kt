package org.firstinspires.ftc.teamcode.commander.command

import com.github.serivesmejia.deltacommander.DeltaCommand
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.commander.subsystem.MecanumSubsystem

class MecanumDriveCommand(val gamepad: Gamepad) : DeltaCommand() {

    val sub = require<MecanumSubsystem>()

    override fun run() {
        sub.drive.joystickRobotCentric(gamepad, true, 0.7)
    }

}