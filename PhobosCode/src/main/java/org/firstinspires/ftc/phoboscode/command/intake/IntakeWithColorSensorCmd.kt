package org.firstinspires.ftc.phoboscode.command.intake

import com.github.serivesmejia.deltacommander.DeltaCommand
import com.github.serivesmejia.deltacommander.command.DeltaInstantCmd
import com.github.serivesmejia.deltacommander.dsl.deltaSequence
import com.github.serivesmejia.deltacommander.subsystem
import org.firstinspires.ftc.phoboscode.subsystem.IntakeSubsystem
import org.firstinspires.ftc.phoboscode.subsystem.Lift
import org.firstinspires.ftc.phoboscode.subsystem.LiftSubsystem
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

class IntakeWithColorSensorCmd(
    val power: Double,
    val telemetry: Telemetry? = null
) : DeltaCommand() {

    val intakeSub = require<IntakeSubsystem>()

    val liftSub = subsystem<LiftSubsystem>()

    var hasIntaked = false
        private set

    var reversed = false
        private set

    override fun run() {
        intakeSub.intakeMotor.power = if(liftSub.motorTicks.toDouble() < Lift.lowPosition.toDouble() / 2.0) {
            if (reversed) {
                -power
            } else power
        } else {
            0.0
        }

        val distance = intakeSub.colorSensor.getDistance(DistanceUnit.INCH)

        /*
        val argb = intakeSub.colorSensor.argb()

        val red = argb shl 16*2 and 0xFF
        val green = argb shr 16*1 and 0xFF
        val blue = argb shr 16*0 and 0xFF

        telemetry?.addData("r", red)
        telemetry?.addData("g", green)
        telemetry?.addData("b", blue)
         */

        if(distance <= 1.5 && !hasIntaked) {
            hasIntaked = true

            + deltaSequence {
                - waitForSeconds(0.45)
                - DeltaInstantCmd { reversed = true }
            }
        }
    }

}