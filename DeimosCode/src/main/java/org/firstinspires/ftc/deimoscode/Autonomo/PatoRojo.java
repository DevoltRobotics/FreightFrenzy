package org.firstinspires.ftc.deimoscode.Autonomo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.deimoscode.Hardwareñ;

@Autonomous (name = "PatoAzul", group = "Autonomus")
public class PatoAzul extends LinearOpMode {

    private DcMotor FrontalD;
    private DcMotor FrontalI;
    private DcMotor TraseroD;
    private DcMotor TraseroI;
    private DcMotor Elevador;
    private DcMotor Pato;
    private DcMotor Extender;
    private Servo Garra;

    @Override
    public void runOpMode(){


        FrontalD = hardwareMap.dcMotor.get("FD");
        FrontalI = hardwareMap.dcMotor.get("FI");
        TraseroD = hardwareMap.dcMotor.get("TD");
        TraseroI = hardwareMap.dcMotor.get("TI");
        Elevador = hardwareMap.dcMotor.get("ELE");
        Extender = hardwareMap.dcMotor.get("EXT");
        Pato = hardwareMap.dcMotor.get("Pato");
        Garra = hardwareMap.servo.get("Abs");

        FrontalD.setDirection(DcMotorSimple.Direction.REVERSE);
        TraseroD.setDirection(DcMotorSimple.Direction.REVERSE);

        //FD=TD
        //TD=FD
        //FI=TI
        //TI=FI

        waitForStart();

        Garra.setPosition(0);
        sleep(500);
        FrontalD.setPower(.5);  //movimiento a la Izquierda
        TraseroD.setPower(-.5);
        FrontalI.setPower(-.5);
        TraseroI.setPower(.5);
        sleep(1200);
        FrontalD.setPower(0);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(0);
        sleep(500);
        FrontalD.setPower(.5);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(-.5);
        sleep(350);
        FrontalD.setPower(0);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(0);
        sleep(300);
        Elevador.setPower(.6);
        sleep(2400);
        FrontalD.setPower(.5);  //movimiento a la Izquierda
        TraseroD.setPower(-.5);
        FrontalI.setPower(-.5);
        TraseroI.setPower(.5);
        sleep(150);
        FrontalD.setPower(0);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(0);
        sleep(300);
        Garra.setPosition(0);
        sleep(500);
        Garra.setPosition(1);
        sleep(500);
        FrontalD.setPower(-.3);
        TraseroD.setPower(.3);
        FrontalI.setPower(.3);
        TraseroI.setPower(-.3);
        sleep(2000);
        FrontalD.setPower(0);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(0);
        sleep(400);
        Elevador.setPower(-.6);
        sleep(800);
        Elevador.setPower(0);
        sleep(800);
        Garra.setPosition(0);
        FrontalD.setPower(-.6);
        TraseroD.setPower(-.6);
        FrontalI.setPower(-.6);
        TraseroI.setPower(-.6);
        sleep(400);
        FrontalD.setPower(0);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(0);
        sleep(400);
        FrontalD.setPower(-.3);  //movimiento a la Izquierda
        TraseroD.setPower(.3);
        FrontalI.setPower(.3);
        TraseroI.setPower(-.3);
        sleep(1700);
        FrontalD.setPower(0);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(0);
        sleep(400);
        Pato.setPower(1);
        sleep(3000);
        Pato.setPower(0);
        sleep(200);
        FrontalD.setPower(.3);  //movimiento a la Izquierda
        TraseroD.setPower(-.3);
        FrontalI.setPower(-.3);
        TraseroI.setPower(.3);
        sleep(1500);
        FrontalD.setPower(0);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(0);
        sleep(400);
        FrontalD.setPower(-.3);
        TraseroD.setPower(-.3);
        FrontalI.setPower(-.3);
        TraseroI.setPower(-.3);
        sleep(700);
        FrontalD.setPower(0);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(0);
        sleep(400);


    }
}

