package org.firstinspires.ftc.phoboscode

import com.github.serivesmejia.deltacommander.deltaScheduler
import org.firstinspires.ftc.commoncode.CommonOpMode
import org.firstinspires.ftc.phoboscode.commander.subsystem.TestSubsystem

abstract class PhobosOpMode(usingRR: Boolean = false) : CommonOpMode(usingRR) {

    override val hardware = Hardware()

    override fun initialize() {
        super.initialize()

        deltaScheduler.addSubsystem(TestSubsystem(hardware.testServo))
    }

}