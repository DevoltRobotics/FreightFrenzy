package org.firstinspires.ftc.commoncode.commander.command

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.github.serivesmejia.deltacommander.DeltaCommand
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.commoncode.commander.subsystem.MecanumSubsystem
import org.firstinspires.ftc.robotcore.external.Telemetry

class MecanumFieldCentricDriveCommand(val gamepad: Gamepad, val telemetry: Telemetry? = null) : DeltaCommand() {

    val sub = require<MecanumSubsystem>("MecanumSubsystem")

    override fun run() {
        val pose = sub.drive.poseEstimate

        val input = Vector2d(
                (-gamepad.left_stick_y).toDouble(),
                (-gamepad.left_stick_x).toDouble()
        ).rotated(-pose.heading)

        sub.setWeighedDrivePower(
                Pose2d(
                        input.x, input.y,
                        (-gamepad.right_stick_x).toDouble()
                )
        )

        telemetry?.run {
            addData("heading", pose.heading)
        }
    }


}