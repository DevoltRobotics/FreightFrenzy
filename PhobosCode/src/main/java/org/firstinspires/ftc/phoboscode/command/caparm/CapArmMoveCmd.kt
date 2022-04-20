package org.firstinspires.ftc.phoboscode.command.caparm

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.commoncode.command.MotorPowerCmd
import org.firstinspires.ftc.phoboscode.subsystem.CapArmSubsystem

open class CapArmMoveCmd(private val powerSupplier: () -> Double) : MotorPowerCmd() {

    val capSub = require<CapArmSubsystem>()

    override val power get() = powerSupplier()
    override val motor = capSub.armMotor

    constructor(power: Double) : this({ power })

}

class CapArmOutCmd : CapArmMoveCmd(0.8)
class CapArmSaveCmd : CapArmMoveCmd(-0.8)

class CapArmStopCmd : CapArmMoveCmd(0.0)