package org.firstinspires.ftc.deimoscode

import com.github.serivesmejia.deltacommander.deltaScheduler
import org.firstinspires.ftc.commoncode.CommonOpMode
import org.firstinspires.ftc.deimoscode.commander.subsystem.ArmClawSubystem
import org.firstinspires.ftc.deimoscode.commander.subsystem.ArmSubsystem

abstract class DeimosOpMode(usingRR: Boolean = false) : CommonOpMode(usingRR) {

    override val hardware = Hardware()

    override fun initialize() {
        super.initialize()

        deltaScheduler.addSubsystem(ArmSubsystem(hardware.motorClawVertical, hardware.motorClawRotate))
        deltaScheduler.addSubsystem(ArmClawSubystem(hardware.servoClaw))
    }

}