package org.firstinspires.ftc.phoboscode.command.intake

import org.firstinspires.ftc.commoncode.command.MotorPowerCmd
import org.firstinspires.ftc.phoboscode.subsystem.IntakeSubsystem

open class IntakeMoveCmd(override val power: Double) : MotorPowerCmd() {
    val inSub = require<IntakeSubsystem>()

    override val motor = inSub.intakeMotor
}

class IntakeInCmd : IntakeMoveCmd(1.0)
class IntakeOutCmd : IntakeMoveCmd(-1.0)

class IntakeStopCmd : IntakeMoveCmd(0.0)