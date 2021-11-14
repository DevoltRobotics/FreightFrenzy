package org.firstinspires.ftc.phoboscode

import com.github.serivesmejia.deltacommander.deltaScheduler
import org.firstinspires.ftc.commoncode.CommonOpMode
import org.firstinspires.ftc.phoboscode.commander.subsystem.BoxSubsystem
import org.firstinspires.ftc.phoboscode.commander.subsystem.CarouselSubsystem
import org.firstinspires.ftc.phoboscode.commander.subsystem.IntakeSubsystem
import org.firstinspires.ftc.phoboscode.commander.subsystem.LiftSubsystem

abstract class PhobosOpMode(usingRR: Boolean = false) : CommonOpMode(usingRR) {

    override val hardware = Hardware()

    lateinit var intakeSub: IntakeSubsystem
        private set
    lateinit var carouselSub: CarouselSubsystem
        private set
    lateinit var liftSub: LiftSubsystem
        private set
    lateinit var boxSub: BoxSubsystem
        private set

    override fun initialize() {
        super.initialize()

        intakeSub = IntakeSubsystem(hardware.intakeMotor)
        carouselSub = CarouselSubsystem(hardware.carouselMotor)
        liftSub = LiftSubsystem(hardware.sliderMotor)
        boxSub = BoxSubsystem(hardware.boxServo)

        setup()
    }

    abstract fun setup()

}