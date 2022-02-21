package org.firstinspires.ftc.commoncode.subsystem

import com.acmerobotics.roadrunner.drive.MecanumDrive
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.github.serivesmejia.deltacommander.DeltaSubsystem

abstract class MecanumSubsystem: DeltaSubsystem() {

    abstract val drive: MecanumDrive

    override fun loop() {
    }

    abstract fun setWeighedDrivePower(pose: Pose2d)

    fun resetPose() {
        drive.poseEstimate = Pose2d()
    }

}