package org.firstinspires.ftc.phoboscode.subsystem

import com.acmerobotics.dashboard.config.Config
import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.PIDFCoefficients
import org.firstinspires.ftc.phoboscode.command.lift.LiftMoveToPosCmd

class LiftSubsystem(val liftMotor: DcMotor) : DeltaSubsystem() {

    init {
        defaultCommand = LiftMoveToPosCmd(LiftPosition.ZERO)
    }

    val positionPid = Lift.pid

    private var offset = 0
    val motorTicks get() = liftMotor.currentPosition - offset

    override fun init() {
        liftMotor.direction = DcMotorSimple.Direction.REVERSE
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
    @JvmField var pid = PIDFCoefficients(0.004, 0.0, 0.0, 0.0)

    @JvmField var power = 0.7

    @JvmField var zeroPosition = 0
    @JvmField var lowPosition = 210
    @JvmField var middlePosition = 572
    @JvmField var highPosition = 892
}