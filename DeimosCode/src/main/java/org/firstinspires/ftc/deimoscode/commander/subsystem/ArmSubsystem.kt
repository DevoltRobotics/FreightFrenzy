package org.firstinspires.ftc.deimoscode.commander.subsystem

import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.deimoscode.commander.command.arm.ArmClawCloseCmd

class ArmSubsystem(val verticalMotor: DcMotor, val rotateMotor: DcMotor) : DeltaSubsystem() {

    init {
        verticalMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        rotateMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }

    override fun loop() {

    }

}