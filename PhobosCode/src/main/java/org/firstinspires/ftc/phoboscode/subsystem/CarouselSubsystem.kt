package org.firstinspires.ftc.phoboscode.subsystem

import com.acmerobotics.dashboard.config.Config
import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.phoboscode.command.carousel.CarouselStopCmd
import org.firstinspires.ftc.robotcore.external.Telemetry
import kotlin.math.abs

class CarouselSubsystem(
        val carouselMotor: DcMotor,
        val telemetry: Telemetry,
) : DeltaSubsystem() {

    var lastMovingPower = 0.0
        private set

    var power: Double
        get() = carouselMotor.power
        set(value) {
            if(abs(value) != 0.0) {
                lastMovingPower = value
            }
            carouselMotor.power = value
        }

    init {
        defaultCommand = CarouselStopCmd()
    }

    override fun loop() {
    }

}

@Config
object Carousel {
    @JvmField var acceleration = 0.6
}