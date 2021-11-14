package org.firstinspires.ftc.deimoscode

import com.github.serivesmejia.deltacommander.deltaScheduler
import org.firstinspires.ftc.commoncode.CommonOpMode
import org.firstinspires.ftc.deimoscode.commander.subsystem.ArmClawSubystem
import org.firstinspires.ftc.deimoscode.commander.subsystem.ArmSubsystem

abstract class DeimosOpMode(usingRR: Boolean = false) : CommonOpMode(usingRR) {

    override val hardware = Hardware()

    lateinit var armSub: ArmSubsystem
        private set
    lateinit var armClawSub: ArmClawSubystem
        private set

    override fun initialize() {
        super.initialize()

        armSub = ArmSubsystem(hardware.motorClawVertical, hardware.motorClawRotate)
        armClawSub = ArmClawSubystem(hardware.servoClaw)

        setup()
    }

    abstract fun setup()

}