package org.firstinspires.ftc.commoncode.commander.subsystem

import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.github.serivesmejia.deltadrive.drivebase.DeltaMecanumDrive
import com.github.serivesmejia.deltadrive.hardware.DeltaHardwareHolonomic

class MecanumSubsystem(
        hardware: DeltaHardwareHolonomic
) : DeltaSubsystem() {

    val drive = DeltaMecanumDrive(hardware)

    override fun loop() { }

}