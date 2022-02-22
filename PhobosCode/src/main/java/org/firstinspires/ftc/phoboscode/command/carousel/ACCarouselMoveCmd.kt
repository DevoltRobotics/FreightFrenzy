package org.firstinspires.ftc.phoboscode.command.carousel

import com.acmerobotics.roadrunner.util.NanoClock
import com.github.serivesmejia.deltacommander.DeltaCommand
import com.github.serivesmejia.deltacommander.dsl.deltaSequence
import com.github.serivesmejia.deltacommander.subsystem
import com.qualcomm.robotcore.util.Range
import org.firstinspires.ftc.phoboscode.subsystem.Carousel
import org.firstinspires.ftc.phoboscode.subsystem.CarouselSubsystem
import kotlin.math.abs
import kotlin.math.sign

open class ACCarouselMoveCmd(val targetPower: Double) : DeltaCommand() {

    val carSub = require<CarouselSubsystem>()
    val timer = NanoClock.system()

    private var startTime = timer.seconds()

    override fun init() {
        startTime = timer.seconds()
    }

    override fun run() {
        carSub.power = Range.clip(Carousel.acceleration * (timer.seconds() - startTime), 0.0, abs(targetPower)) * sign(targetPower)
    }

    override fun end(interrupted: Boolean) {
        carSub.carouselMotor.power = 0.0
    }

}

class ACCarouselRotateForwardCmd  : ACCarouselMoveCmd(1.0)
class ACCarouselRotateBackwardsCmd : ACCarouselMoveCmd(-1.0)

fun ACCarouselStopCmd() = deltaSequence {
    val carSub = subsystem<CarouselSubsystem>()

    - CarouselMoveCmd(-carSub.lastMovingPower)
    - waitForSeconds(1.5)
    - CarouselStopCmd()
}