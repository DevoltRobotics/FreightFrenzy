package org.firstinspires.ftc.phoboscode

import org.firstinspires.ftc.commoncode.CommonOpMode
import org.firstinspires.ftc.phoboscode.subsystem.*

abstract class PhobosOpMode : CommonOpMode() {

    override val hardware = Hardware()

    override val mecanumSub by lazy { MecanumSubsystem(hardware.drive) }

    lateinit var intakeSub: IntakeSubsystem
        private set
    lateinit var carouselSub: CarouselSubsystem
        private set
    lateinit var liftSub: LiftSubsystem
        private set
    lateinit var boxSub: BoxSubsystem
        private set
    lateinit var capArmSub: CapArmSubsystem
        private set

    override fun initialize() {
        super.initialize()

        mecanumSub.init()
        intakeSub = IntakeSubsystem(hardware.intakeMotor, hardware.intakePushServo, hardware.intakeColorSensor)
        carouselSub = CarouselSubsystem(hardware.carouselMotor, telemetry)
        liftSub = LiftSubsystem(hardware.sliderMotor)
        boxSub = BoxSubsystem(hardware.boxServo)
        capArmSub = CapArmSubsystem(hardware.capArmMotor)

        setup()
    }

    abstract fun setup()

}