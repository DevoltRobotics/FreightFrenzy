package org.firstinspires.ftc.commoncode

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.github.serivesmejia.deltacommander.deltaScheduler
import com.github.serivesmejia.deltacommander.reset
import com.github.serivesmejia.deltadrive.hardware.DeltaHardwareHolonomic
import com.github.serivesmejia.deltaevent.opmode.DeltaOpMode
import org.firstinspires.ftc.commoncode.commander.subsystem.MecanumSubsystem
import org.firstinspires.ftc.robotcore.external.Telemetry

abstract class CommonOpMode() : DeltaOpMode() {

    abstract val hardware: CommonHardware

    abstract val mecanumSub: MecanumSubsystem

    lateinit var originalTelemetry: Telemetry
        private set

    override fun initialize() {
        originalTelemetry = telemetry
        telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)

        deltaScheduler.reset()
        hardware.initHardware(hardwareMap)

        deltaHardware = hardware.deltaHardware
    }

}