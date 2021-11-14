package org.firstinspires.ftc.phoboscode.commander.subsystem

import com.acmerobotics.dashboard.config.Config
import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.PIDFCoefficients

class LiftSubsystem(val liftMotor: DcMotor) : DeltaSubsystem() {

    val positionPid = Lift.pid

    private var offset = 0
    val motorTicks get() = liftMotor.currentPosition - offset

    override fun init() {
        liftMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        liftMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER
    }

    override fun loop() {
    }

    fun resetMotorPosition() {
        offset = liftMotor.currentPosition
    }

}

enum class LiftPosition(val position: () -> Int) {
    ZERO({ Lift.zeroPosition }),
    LOW({ Lift.lowPosition }),
    MID({ Lift.middlePosition }),
    HIGH({ Lift.highPosition }),
}

@Config
object Lift {
    @JvmStatic val pid = PIDFCoefficients()

    @JvmStatic val power = 0.7

    @JvmStatic val zeroPosition = 0
    @JvmStatic val lowPosition = 100
    @JvmStatic val middlePosition = 500
    @JvmStatic val highPosition = 1000
}