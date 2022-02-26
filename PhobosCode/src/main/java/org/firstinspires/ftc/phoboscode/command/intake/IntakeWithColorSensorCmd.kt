package org.firstinspires.ftc.phoboscode.command.intake

import com.github.serivesmejia.deltacommander.DeltaCommand
import com.github.serivesmejia.deltacommander.command.DeltaInstantCmd
import com.github.serivesmejia.deltacommander.dsl.deltaSequence
import org.firstinspires.ftc.phoboscode.subsystem.IntakeSubsystem
import org.firstinspires.ftc.robotcore.external.Telemetry

class IntakeWithColorSensorCmd(
    val power: Double,
    val telemetry: Telemetry? = null
) : DeltaCommand() {

    val intakeSub = require<IntakeSubsystem>()

    var hasIntaked = false
        private set

    var reversed = false
        private set

    override fun run() {
        intakeSub.intakeMotor.power = if(reversed) {
            -power
        } else power

        val argb = intakeSub.colorSensor.argb()

        val red = argb shl 16*2 and 0xFF
        val green = argb shr 16*1 and 0xFF
        val blue = argb shr 16*0 and 0xFF

        telemetry?.addData("r", red)
        telemetry?.addData("g", green)
        telemetry?.addData("b", blue)

        if(green >= 1.0 && !hasIntaked) {
            hasIntaked = true

            + deltaSequence {
                - waitForSeconds(0.5)
                - DeltaInstantCmd { reversed = true }
            }
        }
    }

}