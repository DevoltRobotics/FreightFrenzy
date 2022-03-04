package org.firstinspires.ftc.commoncode.command

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.github.serivesmejia.deltacommander.DeltaCommand
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.commoncode.subsystem.MecanumSubsystem
import org.firstinspires.ftc.robotcore.external.Telemetry

class MecanumFieldCentricDriveCommand(
    val gamepad: Gamepad,
    val telemetry: Telemetry? = null,
    val applyTurboWithTriggers: Boolean = true
) : DeltaCommand() {

    val sub = require<MecanumSubsystem>("MecanumSubsystem")

    override fun run() {
        val triggerValue = if(gamepad.left_trigger > 0.2) {
            gamepad.left_trigger
        } else gamepad.right_trigger

        val turbo = 0.7 * if(applyTurboWithTriggers) {
            1.0 - (triggerValue * 0.8)
        } else 1.0

        val pose = sub.drive.poseEstimate

        val input = Vector2d(
                (-gamepad.left_stick_y).toDouble() * turbo,
                (-gamepad.left_stick_x).toDouble() * turbo
        ).rotated(-pose.heading)

        sub.setWeighedDrivePower(
                Pose2d(
                        input.x, input.y,
                        (-gamepad.right_stick_x).toDouble() * turbo
                )
        )

        telemetry?.run {
            addData("heading", pose.heading)
        }
    }


}