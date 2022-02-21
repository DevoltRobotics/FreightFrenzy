package org.firstinspires.ftc.phoboscode.command.capturret

import com.github.serivesmejia.deltacommander.DeltaCommand
import org.firstinspires.ftc.phoboscode.subsystem.CappingTurretSubsystem

open class CapTurretMoveCmd(
        val yawPositionProvider: () -> Double,
        val pitchPositionProvider: () -> Double
) : DeltaCommand() {

    val turretSub = require<CappingTurretSubsystem>()

    constructor(yawPosition: Double, pitchPosition: Double) : this(
            { yawPosition }, { pitchPosition }
    )

    override fun run() {
        turretSub.yawServo.position = yawPositionProvider()
        turretSub.pitchServo.position = pitchPositionProvider()
    }

}

class CapTurretIncrementalMoveCmd(
        val incrementYawProvider: () -> Double,
        val incrementPitchProvider: () -> Double
) : DeltaCommand() {

    val turretSub = require<CappingTurretSubsystem>()

    override fun run() {
        turretSub.yawServo.position += incrementYawProvider()
        turretSub.pitchServo.position += incrementPitchProvider()
    }

}

class CapTurretZeroCmd : CapTurretMoveCmd(0.0, 0.0)