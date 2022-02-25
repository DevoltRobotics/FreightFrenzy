package org.firstinspires.ftc.phoboscode.command.capturret

import org.firstinspires.ftc.commoncode.command.MotorPowerCmd
import org.firstinspires.ftc.phoboscode.subsystem.CappingTurretTapeSubsystem

open class CapTurretTapeMotorMoveCmd(override val power: Double) : MotorPowerCmd() {
    val capSub = require<CappingTurretTapeSubsystem>()
    override val motor = capSub.tapeMotor
}

class CapTurretTapeMotorExtendCmd : CapTurretTapeMotorMoveCmd(0.7)
class CapTurretTapeMotorSaveCmd : CapTurretTapeMotorMoveCmd(-0.7)
class CapTurretTapeMotorStopCmd : CapTurretTapeMotorMoveCmd(0.0)