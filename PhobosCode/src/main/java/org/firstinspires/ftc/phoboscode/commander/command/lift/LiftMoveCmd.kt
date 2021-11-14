package org.firstinspires.ftc.phoboscode.commander.command.lift

import org.firstinspires.ftc.commoncode.commander.command.MotorPowerCmd
import org.firstinspires.ftc.phoboscode.commander.subsystem.Lift
import org.firstinspires.ftc.phoboscode.commander.subsystem.LiftSubsystem

open class LiftMoveCmd(override val power: Double) : MotorPowerCmd() {
    val liftSub = require<LiftSubsystem>()

    override val motor = liftSub.liftMotor
}

class LiftUpCmd : LiftMoveCmd(Lift.power)
class LiftDownCmd : LiftMoveCmd(-Lift.power)