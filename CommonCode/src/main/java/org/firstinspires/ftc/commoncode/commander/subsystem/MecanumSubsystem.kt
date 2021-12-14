package org.firstinspires.ftc.commoncode.commander.subsystem

import com.acmerobotics.roadrunner.drive.MecanumDrive
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.github.serivesmejia.deltadrive.drivebase.DeltaMecanumDrive
import com.github.serivesmejia.deltadrive.hardware.DeltaHardwareHolonomic

abstract class MecanumSubsystem(
        hardware: DeltaHardwareHolonomic
) : DeltaSubsystem() {

    abstract val drive: MecanumDrive

    val deltaDrive by lazy { DeltaMecanumDrive(hardware) }

    override fun loop() {
    }

    abstract fun setWeighedDrivePower(pose: Pose2d)

    fun resetPose() {
        drive.poseEstimate = Pose2d()
    }

}