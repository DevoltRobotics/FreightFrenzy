package org.firstinspires.ftc.deimoscode.Autonomo.nacional;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

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

        detector.initWebcamVision(hardwareMap, "Enchiladas", OpenCvCameraRotation.UPRIGHT);
        FtcDashboard.getInstance().startCameraStream(detector.getCamera(), 0);

        hardware.Elevador.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.Elevador.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        run();
    }

    public abstract void run();

}
