package org.firstinspires.ftc.phoboscode.command.lift

import org.firstinspires.ftc.commoncode.commander.command.MotorPowerCmd
import org.firstinspires.ftc.phoboscode.subsystem.Lift
import org.firstinspires.ftc.phoboscode.subsystem.LiftSubsystem

open class LiftMoveCmd(val powerSupplier: () -> Double) : MotorPowerCmd() {

    constructor(power: Double): this({ power })

    override val power get() = powerSupplier()

    val liftSub = require<LiftSubsystem>()

    override val motor = liftSub.liftMotor
}

class LiftUpCmd : LiftMoveCmd(Lift.power)
class LiftDownCmd : LiftMoveCmd(-Lift.power)