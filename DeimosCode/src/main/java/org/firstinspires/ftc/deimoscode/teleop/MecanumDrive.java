package org.firstinspires.ftc.deimoscode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "MecanumDrive", group = "control")
public class MecanumDrive extends LinearOpMode{
    private DcMotor FrontalD;
    private DcMotor FrontalI;
    private DcMotor TraseroD;
    private DcMotor TraseroI;

    @Override
    public void runOpMode(){
        FrontalD = hardwareMap.dcMotor.get("FrontalD");
        FrontalI = hardwareMap.dcMotor.get("FrontalI");
        TraseroD = hardwareMap.dcMotor.get("TraseroD");
        TraseroI = hardwareMap.dcMotor.get("TraseroI");

        waitForStart();
        while (opModeIsActive()){
            double horizontal = 0.5 * gamepad1.left_stick_x;
            double vertical = -0.5 * gamepad1.left_stick_y;
            double girar = -0.5 * gamepad1.right_stick_x;

            FrontalD.setPower(vertical - girar - horizontal);
            TraseroD.setPower(vertical - girar + horizontal);
            FrontalI.setPower(vertical + girar + horizontal);
            TraseroI.setPower(vertical + girar - horizontal);

        }
    }
}