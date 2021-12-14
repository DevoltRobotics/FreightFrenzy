package org.firstinspires.ftc.phoboscode.commander.subsystem

import com.acmerobotics.roadrunner.drive.MecanumDrive
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.github.serivesmejia.deltadrive.hardware.DeltaHardwareHolonomic
import org.firstinspires.ftc.commoncode.commander.subsystem.MecanumSubsystem
import org.firstinspires.ftc.phoboscode.rr.drive.SampleMecanumDrive

class MecanumSubsystem(hardware: DeltaHardwareHolonomic) : MecanumSubsystem(hardware) {

    override val drive = SampleMecanumDrive(hardware.hardwareMap, hardware)

    override fun loop() {
        drive.update()
    }

    override fun setWeighedDrivePower(pose: Pose2d) = drive.setWeightedDrivePower(pose)

}