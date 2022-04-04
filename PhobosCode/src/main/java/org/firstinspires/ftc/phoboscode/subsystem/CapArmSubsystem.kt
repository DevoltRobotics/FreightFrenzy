package org.firstinspires.ftc.phoboscode.subsystem

import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.phoboscode.command.caparm.CapArmStopCmd

class CapArmSubsystem(val armMotor: DcMotor) : DeltaSubsystem() {

    init {
        armMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        defaultCommand = CapArmStopCmd()
    }
    
    override fun loop() {
    }

}