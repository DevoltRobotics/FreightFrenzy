package org.firstinspires.ftc.deimoscode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Tsuru_Tuneado", group = "control")
public class Tsuru_Tuneado extends LinearOpMode {
    private DcMotor FrontalD;
    private DcMotor FrontalI;
    private DcMotor TraseroD;
    private DcMotor TraseroI;
    private DcMotor Pato;
    private DcMotor Extender;
    private DcMotor Elevador;
    private Servo Absorber;

    double Extend = 0.3;
    double Elepo = 0.5;

    @Override
    public void runOpMode() {
        FrontalD = hardwareMap.dcMotor.get("FD");
        FrontalI = hardwareMap.dcMotor.get("FI");
        TraseroD = hardwareMap.dcMotor.get("TD");
        TraseroI = hardwareMap.dcMotor.get("TI");
        Pato = hardwareMap.dcMotor.get("Pato");
        Extender = hardwareMap.dcMotor.get("EXT");
        Elevador = hardwareMap.dcMotor.get("ELE");
        Absorber = hardwareMap.servo.get("Abs");


        FrontalD.setDirection(DcMotorSimple.Direction.REVERSE);
        TraseroD.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            double horizontal = 0.7 * gamepad1.left_stick_x;
            double vertical = -0.7 * gamepad1.left_stick_y;
            double turn = -0.7 * gamepad1.right_stick_x;

            TraseroI.setPower(vertical + turn - horizontal);
            FrontalI.setPower(vertical + turn + horizontal);
            TraseroD.setPower(vertical - turn + horizontal);
            FrontalD.setPower(vertical - turn - horizontal);


            Elepo = gamepad2.left_stick_y * 0.6;
            Elevador.setPower(Elepo);


            Extend = gamepad2.right_stick_y;
            Extender.setPower(-Extend);

            if (gamepad2.right_bumper) {
                Absorber.setPosition(1);
            }

            if (gamepad2.left_bumper) {
                Absorber.setPosition(0.4);
            }

            if (gamepad2.y) {
                Pato.setPower(1);
            } else {
                Pato.setPower(0);
            }

        }
    }
}

