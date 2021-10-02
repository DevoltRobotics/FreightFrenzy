package org.firstinspires.ftc.phoboscode.commander.command

import com.github.serivesmejia.deltacommander.DeltaCommand
import org.firstinspires.ftc.phoboscode.commander.subsystem.TestSubsystem

class MoveTestServoCmd(val position: Double) : DeltaCommand() {

    val testSub = require<TestSubsystem>()

    override fun run() {
        testSub.testServo.position = position
    }


}