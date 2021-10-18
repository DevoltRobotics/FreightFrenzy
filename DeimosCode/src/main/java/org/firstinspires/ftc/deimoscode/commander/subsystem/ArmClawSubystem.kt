package org.firstinspires.ftc.deimoscode.commander.subsystem

import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.deimoscode.commander.command.arm.ArmClawCloseCmd

class ArmClawSubystem(val servoClaw: Servo) : DeltaSubsystem() {

    override fun init() {
        defaultCommand = ArmClawCloseCmd()
    }

    override fun loop() {
    }

}