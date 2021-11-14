package org.firstinspires.ftc.phoboscode.commander.command.intake

import org.firstinspires.ftc.commoncode.commander.command.MotorPowerCmd
import org.firstinspires.ftc.phoboscode.commander.subsystem.IntakeSubsystem

open class IntakeMoveCmd(override val power: Double) : MotorPowerCmd() {
    val inSub = require<IntakeSubsystem>()

    override val motor = inSub.intakeMotor
}

class IntakeInCmd : IntakeMoveCmd(1.0)
class IntakeOutCmd : IntakeMoveCmd(-1.0)

class IntakeStopCmd : IntakeMoveCmd(0.0)