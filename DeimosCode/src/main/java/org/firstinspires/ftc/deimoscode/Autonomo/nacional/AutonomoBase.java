package org.firstinspires.ftc.deimoscode.Autonomo.nacional;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.commoncode.vision.TeamMarkerAprilTagDetector;
import org.firstinspires.ftc.deimoscode.Hardwareñ;
import org.firstinspires.ftc.deimoscode.rr.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCameraRotation;

public abstract class AutonomoBase extends LinearOpMode {

    TeamMarkerAprilTagDetector detector = new TeamMarkerAprilTagDetector();

    Hardwareñ hardware;

    @Override
    public void runOpMode() throws InterruptedException {
        hardware = new Hardwareñ();
        hardware.initHardware(hardwareMap);

        detector.initWebcamVision(hardwareMap, "Enchiladas", OpenCvCameraRotation.SIDEWAYS_LEFT);

        run();
    }

    public abstract void run();

}
