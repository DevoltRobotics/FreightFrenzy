package org.firstinspires.ftc.commoncode

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.github.serivesmejia.deltacommander.deltaScheduler
import com.github.serivesmejia.deltacommander.reset
import com.github.serivesmejia.deltaevent.opmode.DeltaOpMode
import com.github.serivesmejia.deltasimple.SimpleHardware
import org.firstinspires.ftc.commoncode.subsystem.MecanumSubsystem
import org.firstinspires.ftc.robotcore.external.Telemetry

abstract class CommonOpMode() : DeltaOpMode() {

    abstract val hardware: SimpleHardware

    abstract val mecanumSub: MecanumSubsystem

    lateinit var originalTelemetry: Telemetry
        private set

    override fun initialize() {
        originalTelemetry = telemetry
        telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)

        deltaScheduler.reset()
        hardware.initHardware(hardwareMap)
    }

}