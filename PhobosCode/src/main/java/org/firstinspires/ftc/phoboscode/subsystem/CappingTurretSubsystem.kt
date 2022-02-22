package org.firstinspires.ftc.phoboscode.subsystem

import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.phoboscode.command.capturret.CapTurretTapeMotorStopCmd
import org.firstinspires.ftc.phoboscode.command.capturret.CapTurretZeroCmd

class CappingTurretSubsystem(
        val yawServo: Servo,
        val pitchServo: Servo,
) : DeltaSubsystem() {
    init {
        defaultCommand = CapTurretZeroCmd()
    }

    override fun loop() {
    }
}

class CappingTurretTapeSubsystem(
    val tapeMotor: DcMotor
) : DeltaSubsystem() {
    init {
        defaultCommand = CapTurretTapeMotorStopCmd()
    }

    override fun loop() {
    }
}