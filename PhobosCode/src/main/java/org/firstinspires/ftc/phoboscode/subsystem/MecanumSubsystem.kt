package org.firstinspires.ftc.phoboscode.subsystem

import com.acmerobotics.roadrunner.geometry.Pose2d
import org.firstinspires.ftc.commoncode.subsystem.MecanumSubsystem
import org.firstinspires.ftc.phoboscode.lastKnownRobotPose
import org.firstinspires.ftc.phoboscode.rr.drive.SampleMecanumDrive

class MecanumSubsystem(override val drive: SampleMecanumDrive) : MecanumSubsystem() {

    override fun loop() {
        drive.update()
        lastKnownRobotPose = drive.poseEstimate
    }

    override fun setWeighedDrivePower(pose: Pose2d) = drive.setWeightedDrivePower(pose)

}