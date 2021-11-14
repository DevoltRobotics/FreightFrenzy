package org.firstinspires.ftc.phoboscode.commander.command.lift

import com.github.serivesmejia.deltacommander.DeltaCommand
import com.github.serivesmejia.deltacontrol.MotorPIDFController
import org.firstinspires.ftc.phoboscode.commander.subsystem.Lift
import org.firstinspires.ftc.phoboscode.commander.subsystem.LiftPosition
import org.firstinspires.ftc.phoboscode.commander.subsystem.LiftSubsystem

open class LiftMoveToPosCmd(val positionSupplier: () -> Int) : DeltaCommand() {

    val liftSub = require<LiftSubsystem>()

    val controller = MotorPIDFController(Lift.pid)
            .setInitialPower(Lift.power)
            .setDeadzone(0.08)
            .setErrorTolerance(5.0)

    constructor(position: Int) : this({ position })

    constructor(pos: LiftPosition) : this(pos.position)

    override fun init() {
        controller.reset()
    }

    override fun run() {
        controller.setSetpoint(positionSupplier().toDouble()) // set the target position

        liftSub.liftMotor.power = controller.calculate( // calculate output from pidcontroller
                liftSub.liftMotor.currentPosition.toDouble() // based from the current position
        )
    }

}

class LiftZeroPosition: LiftMoveToPosCmd(Lift.zeroPosition)
class LiftLowPosition : LiftMoveToPosCmd(Lift.lowPosition)
class LiftMiddlePosition : LiftMoveToPosCmd(Lift.middlePosition)
class LiftHighPosition : LiftMoveToPosCmd(Lift.highPosition)