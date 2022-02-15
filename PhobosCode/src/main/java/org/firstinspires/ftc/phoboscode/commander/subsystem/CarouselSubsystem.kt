package org.firstinspires.ftc.phoboscode.commander.subsystem

import com.acmerobotics.dashboard.config.Config
import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.phoboscode.commander.command.carousel.CarouselStopCmd
import org.firstinspires.ftc.robotcore.external.Telemetry

class CarouselSubsystem(
        val carouselMotor: DcMotor,
        val telemetry: Telemetry,
) : DeltaSubsystem() {

    init {
        defaultCommand = CarouselStopCmd()
    }

    override fun loop() {
    }

}

@Config
object Carousel {

    @JvmField var acceleration = 0.08

}