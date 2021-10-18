package org.firstinspires.ftc.deimoscode.commander.command.arm

import com.github.serivesmejia.deltacommander.DeltaCommand
import org.firstinspires.ftc.deimoscode.commander.subsystem.ArmSubsystem

class ArmMoveCmd(
        val verticalPowerSupplier: () -> Double,
        val rotatePowerSupplier: () -> Double,
        val turbo: Double = 0.5,
        val minThreshold: Double = 0.0
) : DeltaCommand() {

    val armSub = require<ArmSubsystem>()

    override fun run() {
        val vertical = verticalPowerSupplier()

        if(vertical >= minThreshold) {
            armSub.verticalMotor.power = vertical * turbo
        } else {
            armSub.verticalMotor.power = 0.01
        }

        val rotate = rotatePowerSupplier()

        if(rotate >= minThreshold) {
            armSub.rotateMotor.power = rotate * turbo
        }
    }

    override fun end(interrupted: Boolean) {
        armSub.verticalMotor.power = 0.0
        armSub.rotateMotor.power = 0.0
    }


}