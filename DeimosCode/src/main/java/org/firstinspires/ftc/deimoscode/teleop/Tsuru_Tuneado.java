package org.firstinspires.ftc.deimoscode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.deimoscode.Hardwareñ;

@TeleOp(name = "Tsuru_Tuneado", group = "control")
public class Tsuru_Tuneado extends LinearOpMode {

    Hardwareñ hdw = new Hardwareñ();

    double Extend = 0.3;
    int liftTarget = 0;

    LiftState liftState = LiftState.IDLE;
    ElapsedTime liftTimer = new ElapsedTime();

    enum LiftState { IDLE, MOVING, WAITING, ZERO}

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        hdw.initHardware(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            double Turbo = 1.0 - (gamepad1.left_trigger * 0.7);

            hdw.drive.setWeightedDrivePower(new Pose2d(-gamepad1.left_stick_y * Turbo, -gamepad1.left_stick_x * Turbo, -gamepad1.right_stick_x * Turbo));

            switch(liftState) {
                case IDLE:
                    hdw.Elevador.setPower(-gamepad2.right_stick_y * 0.8);

                    if(gamepad2.b) {
                        hdw.Elevador.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        hdw.Elevador.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    }

                    if (gamepad2.right_bumper) {
                        hdw.Absorber.setPosition(0.85);
                    } else if (gamepad2.left_bumper) {
                        hdw.Absorber.setPosition(0.2);
                    }

                    if(gamepad2.dpad_up) {
                        liftTarget = Hardwareñ.HIGH_LIFT_POS;
                        liftState = LiftState.MOVING;
                    } else if(gamepad2.dpad_right) {
                        liftTarget = Hardwareñ.MID_LIFT_POS;
                        liftState = LiftState.MOVING;
                    } else if(gamepad2.dpad_down) {
                        liftTarget = Hardwareñ.LOW_LIFT_POS;
                        liftState = LiftState.MOVING;
                    }
                    break;
                case MOVING:
                    hdw.updateLift(liftTarget);

                    if(hdw.Elevador.getCurrentPosition() >= liftTarget - 30 || gamepad2.b) {
                        liftState = LiftState.WAITING;
                        liftTimer.reset();
                    }
                    break;
                case WAITING:
                    hdw.updateLift(liftTarget);
                    hdw.Absorber.setPosition(0.85);

                    if(liftTimer.seconds() > 2) {
                        liftState = LiftState.ZERO;
                    }
                    break;
                case ZERO:
                    hdw.updateLift(0);

                    if(hdw.Elevador.getCurrentPosition() <= liftTarget + 10) {
                        liftState = LiftState.IDLE;
                        hdw.Absorber.setPosition(0.2);
                    }
                    break;
            }

            Extend = gamepad2.left_stick_y * 0.6;
            hdw.Extender.setPower(-Extend);

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

            if (gamepad2.x) {
                hdw.Empuje.setPower(-0.6);
            } else {
                hdw.Empuje.setPower(0);
            }

            hdw.Cebollin.setPosition(hdw.Cebollin.getPosition() + (gamepad2.left_trigger * 0.01) - (gamepad2.right_trigger * 0.01));

            telemetry.addData("lift current", hdw.Elevador.getCurrentPosition());
            telemetry.addData("lift target", liftTarget);
            telemetry.addData("lift state", liftState);
            telemetry.update();
        }
    }
}