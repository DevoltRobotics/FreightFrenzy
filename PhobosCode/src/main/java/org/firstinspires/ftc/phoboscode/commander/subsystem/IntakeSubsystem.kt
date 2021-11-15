package org.firstinspires.ftc.phoboscode.commander.subsystem

import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.phoboscode.commander.command.intake.IntakeStopCmd

class IntakeSubsystem(val intakeMotor: DcMotor) : DeltaSubsystem() {

    init {
        defaultCommand = IntakeStopCmd()
    }

    override fun loop() {
    }

}