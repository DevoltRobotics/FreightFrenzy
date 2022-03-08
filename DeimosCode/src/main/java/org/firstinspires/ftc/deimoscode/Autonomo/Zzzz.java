package org.firstinspires.ftc.deimoscode.Autonomo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.deimoscode.Hardwareñ;

@Autonomous (name = "Zzzz", group = "Autonomus")
public class Zzzz extends LinearOpMode {

    private DcMotor FrontalD;
    private DcMotor FrontalI;
    private DcMotor TraseroD;
    private DcMotor TraseroI;
    private DcMotor Elevador;
    private DcMotor Pato;
    private DcMotor Extender;
    private Servo Garra;

    @Override
    public void runOpMode() {

        FrontalD = hardwareMap.dcMotor.get("FD");
        FrontalI = hardwareMap.dcMotor.get("FI");
        TraseroD = hardwareMap.dcMotor.get("TD");
        TraseroI = hardwareMap.dcMotor.get("TI");
        Elevador = hardwareMap.dcMotor.get("ELE");
        Extender = hardwareMap.dcMotor.get("EXT");
        Pato = hardwareMap.dcMotor.get("Pato");
        Garra = hardwareMap.servo.get("Abs");

        FrontalI.setDirection(DcMotorSimple.Direction.REVERSE);
        TraseroI.setDirection(DcMotorSimple.Direction.REVERSE);

        //FD=TD
        //TD=FD
        //FI=TI
        //TI=FI

        waitForStart();
        FrontalD.setPower(1);
        sleep(2000);
        FrontalD.setPower(0);
        sleep(300);
        FrontalI.setPower(1);
        sleep(2000);
        FrontalI.setPower(0);
        sleep(300);
        TraseroD.setPower(1);
        sleep(2000);
        TraseroD.setPower(0);
        sleep(300);
        TraseroI.setPower(1);
        sleep(3000);
        TraseroI.setPower(0);
        sleep(300);


    }
}
