package org.firstinspires.ftc.phoboscode.commander.command.carousel

import org.firstinspires.ftc.commoncode.commander.command.MotorPowerCmd
import org.firstinspires.ftc.phoboscode.commander.subsystem.CarouselSubsystem

open class CarouselMoveCmd(override val power: Double) : MotorPowerCmd() {
    val carSub = require<CarouselSubsystem>()

    override val motor = carSub.carouselMotor
}

class CarouselRotateForwardCmd : CarouselMoveCmd(0.7)
class CarouselRotateBackwardsCmd : CarouselMoveCmd(0.7)
class CarouselStopCmd : CarouselMoveCmd(0.0)