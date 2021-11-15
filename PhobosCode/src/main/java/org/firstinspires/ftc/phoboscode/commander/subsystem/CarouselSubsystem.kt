package org.firstinspires.ftc.phoboscode.commander.subsystem

import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.phoboscode.commander.command.carousel.CarouselStopCmd

class CarouselSubsystem(val carouselMotor: DcMotor) : DeltaSubsystem() {

    init {
        defaultCommand = CarouselStopCmd()
    }

    override fun loop() {
    }

}