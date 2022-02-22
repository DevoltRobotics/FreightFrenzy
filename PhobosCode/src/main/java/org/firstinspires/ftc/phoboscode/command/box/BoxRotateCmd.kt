package org.firstinspires.ftc.phoboscode.command.box

import com.github.serivesmejia.deltacommander.DeltaCommand
import org.firstinspires.ftc.phoboscode.subsystem.Box
import org.firstinspires.ftc.phoboscode.subsystem.BoxSubsystem

open class BoxRotateCmd(val position: Double) : DeltaCommand() {

    val boxSub = require<BoxSubsystem>()

    override fun run() {
        boxSub.boxServo.position = position
    }

}

class BoxThrowCmd : BoxRotateCmd(Box.throwPosition)
class BoxSaveCmd  : BoxRotateCmd(Box.savePosition)