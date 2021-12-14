package org.firstinspires.ftc.deimoscode

import com.github.serivesmejia.deltacommander.deltaScheduler
import org.firstinspires.ftc.commoncode.CommonOpMode
import org.firstinspires.ftc.deimoscode.commander.subsystem.ArmClawSubystem
import org.firstinspires.ftc.deimoscode.commander.subsystem.ArmSubsystem
import org.firstinspires.ftc.deimoscode.commander.subsystem.MecanumSubsystem

abstract class DeimosOpMode : CommonOpMode() {

    override val hardware = Hardware()

    override val mecanumSub by lazy { MecanumSubsystem(hardware.deltaHardware) }

    lateinit var armSub: ArmSubsystem
        private set
    lateinit var armClawSub: ArmClawSubystem
        private set

    override fun initialize() {
        super.initialize()

        mecanumSub.init()
        armSub = ArmSubsystem(hardware.motorClawVertical, hardware.motorClawRotate)
        armClawSub = ArmClawSubystem(hardware.servoClaw)

        setup()
    }

    abstract fun setup()

}