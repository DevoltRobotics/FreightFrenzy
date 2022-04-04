package org.firstinspires.ftc.phoboscode.command.caparm

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.commoncode.command.MotorPowerCmd
import org.firstinspires.ftc.phoboscode.subsystem.CapArmSubsystem

open class CapArmMoveCmd(override val power: Double) : MotorPowerCmd() {
    val capSub = require<CapArmSubsystem>()

    override val motor = capSub.armMotor
}

class CapArmOutCmd : CapArmMoveCmd(0.8)
class CapArmSaveCmd : CapArmMoveCmd(-0.8)

class CapArmStopCmd : CapArmMoveCmd(0.0)