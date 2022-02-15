package org.firstinspires.ftc.phoboscode.commander.subsystem

import com.acmerobotics.dashboard.config.Config
import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.phoboscode.commander.command.box.BoxSaveCmd

class BoxSubsystem(val boxServo: Servo) : DeltaSubsystem() {

    init {
        defaultCommand = BoxSaveCmd()
    }

    override fun loop() {
    }

}

@Config
object Box {
    @JvmField var throwPosition = 0.76
    @JvmField var savePosition = 0.28
}