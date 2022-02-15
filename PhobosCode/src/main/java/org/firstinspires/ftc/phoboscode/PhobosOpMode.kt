package org.firstinspires.ftc.phoboscode

import com.github.serivesmejia.deltacommander.deltaScheduler
import org.firstinspires.ftc.commoncode.CommonOpMode
import org.firstinspires.ftc.phoboscode.commander.subsystem.*

abstract class PhobosOpMode : CommonOpMode() {

    override val hardware = Hardware()

    override val mecanumSub by lazy { MecanumSubsystem(hardware.deltaHardware) }

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

        mecanumSub.init()
        intakeSub = IntakeSubsystem(hardware.intakeMotor)
        carouselSub = CarouselSubsystem(hardware.carouselMotor, telemetry)
        liftSub = LiftSubsystem(hardware.sliderMotor)
        boxSub = BoxSubsystem(hardware.boxServo)

        setup()
    }

    abstract fun setup()

}