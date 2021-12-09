package org.firstinspires.ftc.phoboscode.commander.command.lift

import com.acmerobotics.roadrunner.control.PIDCoefficients
import com.acmerobotics.roadrunner.control.PIDFController
import com.github.serivesmejia.deltacommander.DeltaCommand
import com.github.serivesmejia.deltacontrol.MotorPIDFController
import org.firstinspires.ftc.phoboscode.commander.subsystem.Lift
import org.firstinspires.ftc.phoboscode.commander.subsystem.LiftPosition
import org.firstinspires.ftc.phoboscode.commander.subsystem.LiftSubsystem
import org.firstinspires.ftc.robotcore.external.Telemetry

open class LiftMoveToPosCmd(val positionSupplier: () -> Int, private val telemetry: Telemetry? = null) : DeltaCommand() {

    val liftSub = require<LiftSubsystem>()

    val controller = PIDFController(PIDCoefficients(Lift.pid.p, Lift.pid.i, Lift.pid.d))

    constructor(position: Int, telemetry: Telemetry? = null) : this({ position }, telemetry)

    constructor(pos: LiftPosition, telemetry: Telemetry? = null) : this(pos.position, telemetry)

    override fun init() {
        controller.reset()
    }

    override fun run() {
        controller.targetPosition = positionSupplier().toDouble() // set the target position

        liftSub.liftMotor.power = controller.update( // calculate output from pidcontroller
                liftSub.liftMotor.currentPosition.toDouble() // based from the current position
        )

        telemetry?.run {
            addData("lift target", controller.targetPosition)
            addData("lift error", controller.lastError)
            addData("lift power", liftSub.liftMotor.power)
        }
    }

}

class LiftZeroPosition(telemetry: Telemetry? = null): LiftMoveToPosCmd(Lift.zeroPosition, telemetry)
class LiftLowPosition : LiftMoveToPosCmd(Lift.lowPosition)
class LiftMiddlePosition : LiftMoveToPosCmd(Lift.middlePosition)
class LiftHighPosition : LiftMoveToPosCmd(Lift.highPosition)