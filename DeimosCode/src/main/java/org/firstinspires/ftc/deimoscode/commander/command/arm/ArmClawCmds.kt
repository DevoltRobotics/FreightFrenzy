package org.firstinspires.ftc.deimoscode.commander.command.arm

import com.github.serivesmejia.deltacommander.DeltaCommand
import org.firstinspires.ftc.deimoscode.commander.subsystem.ArmClawSubystem

open class ArmClawPositionCmd(val position: Double) : DeltaCommand() {

    val armClawSub = require<ArmClawSubystem>()

    override fun run() {
        armClawSub.servoClaw.position = position
    }

}

class ArmClawOpenCmd : ArmClawPositionCmd(1.0)
class ArmClawCloseCmd : ArmClawPositionCmd(0.0)