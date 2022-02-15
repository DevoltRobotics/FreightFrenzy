package org.firstinspires.ftc.deimoscode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.deimoscode.Hardwareñ;

@TeleOp(name = "Tsuru_Tuneado", group = "control")
public class Tsuru_Tuneado extends LinearOpMode {

    Hardwareñ hdw = new Hardwareñ();

    double Extend = 0.3;
    int liftTarget = 0;

    LiftState liftState = LiftState.IDLE;

    enum LiftState { IDLE, MOVING, WAITING, ZERO}

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        hdw.initHardware(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            hdw.drive.setWeightedDrivePower(new Pose2d(gamepad1.left_stick_y, gamepad1.left_stick_x, -gamepad1.right_stick_x));

            switch(liftState) {
                case IDLE:
                    hdw.Elevador.setPower(-gamepad2.right_stick_y * 0.8);
                    break;
                case MOVING:
                    hdw.updateLift(liftTarget);
                    break;
                case WAITING:
                    break;
                case ZERO:
                    break;
            }

            Extend = gamepad2.left_stick_y * 0.5;
            hdw.Extender.setPower(-Extend);

            if (gamepad2.right_bumper) {
                hdw.Absorber.setPosition(1);
            } else if (gamepad2.left_bumper) {
                hdw.Absorber.setPosition(0.4);
            }

            if (gamepad2.x) {
                hdw.Bloqueo.setPosition(1);
            } else if (gamepad2.a) {
                hdw.Bloqueo.setPosition(0);
            }

            if (gamepad2.y) {
                hdw.Pato.setPower(1);
            } else {
                hdw.Pato.setPower(0);
            }

            if (gamepad1.y) {
                hdw.Pato.setPower(-1);
            } else {
                hdw.Pato.setPower(0);
            }


            telemetry.addData("lift current", hdw.Elevador.getCurrentPosition());
            telemetry.addData("lift target", liftTarget);
            telemetry.update();
        }
    }
}