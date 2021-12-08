package org.firstinspires.ftc.phoboscode.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.commoncode.vision.TeamMarkerAprilTagDetector

@Autonomous(name = "Webcam Test", group = "test")
class AutonomoWebcamTest : LinearOpMode() {

    override fun runOpMode() {
        val vision = TeamMarkerAprilTagDetector()
        vision.initInternalCameraVision(hardwareMap)

        while(!isStopRequested) {
            telemetry.addData("Position", vision.position)
            telemetry.update()
        }
    }

}