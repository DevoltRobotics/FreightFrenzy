package org.firstinspires.ftc.commoncode.commander.command

import com.github.serivesmejia.deltacommander.DeltaCommand
import com.qualcomm.robotcore.hardware.DcMotor

abstract class MotorPowerCmd : DeltaCommand() {

    abstract val power: Double
    abstract val motor: DcMotor

    override fun run() {
        motor.power = power
    }

}