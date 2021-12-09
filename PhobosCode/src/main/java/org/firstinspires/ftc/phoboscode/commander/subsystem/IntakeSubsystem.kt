package org.firstinspires.ftc.phoboscode.commander.subsystem

import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.phoboscode.commander.command.intake.IntakeStopCmd

class IntakeSubsystem(val intakeMotor: DcMotor) : DeltaSubsystem() {

    init {
        defaultCommand = IntakeStopCmd()
        intakeMotor.direction = DcMotorSimple.Direction.REVERSE
    }

    override fun loop() {
    }

}