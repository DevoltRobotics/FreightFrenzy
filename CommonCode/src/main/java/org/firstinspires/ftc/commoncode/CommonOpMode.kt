package org.firstinspires.ftc.commoncode

import com.github.serivesmejia.deltacommander.deltaScheduler
import com.github.serivesmejia.deltacommander.reset
import com.github.serivesmejia.deltadrive.hardware.DeltaHardwareHolonomic
import com.github.serivesmejia.deltaevent.opmode.DeltaOpMode
import org.firstinspires.ftc.commoncode.commander.subsystem.MecanumSubsystem

abstract class CommonOpMode(val usingRR: Boolean = false) : DeltaOpMode() {

    abstract val hardware: CommonHardware

    val mecanum by lazy { MecanumSubsystem(hardware.deltaHardware) }

    override fun initialize() {
        deltaScheduler.reset()
        hardware.initHardware(hardwareMap)

        deltaHardware = hardware.deltaHardware

        if(!usingRR) {
            deltaScheduler.addSubsystem(mecanum)
        }
    }

}