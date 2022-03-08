package org.firstinspires.ftc.phoboscode.subsystem

import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.phoboscode.command.intake.IntakeStopCmd

class IntakeSubsystem(
    val intakeMotor: DcMotor,
    val colorSensor: RevColorSensorV3
) : DeltaSubsystem() {

    init {
        defaultCommand = IntakeStopCmd()
        intakeMotor.direction = DcMotorSimple.Direction.REVERSE
    }

    override fun loop() {
    }

}