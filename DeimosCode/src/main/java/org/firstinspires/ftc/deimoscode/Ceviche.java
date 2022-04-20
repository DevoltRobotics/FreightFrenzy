package org.firstinspires.ftc.deimoscode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp( name = "teleop", group = "Chido")
public class Ceviche extends LinearOpMode {

    private DcMotor motorleft;
    private DcMotor motorright;


    @Override
    public void runOpMode() throws InterruptedException {
        motorleft = hardwareMap.dcMotor.get("motorleft");
        motorright = hardwareMap.dcMotor.get("motorright");

        motorleft.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()) {
            motorleft.setPower(-gamepad1.left_stick_y);
            motorright.setPower(-gamepad1.right_stick_y);
        }
    }
}