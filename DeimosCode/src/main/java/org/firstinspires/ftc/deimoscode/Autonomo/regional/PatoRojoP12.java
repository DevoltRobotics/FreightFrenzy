package org.firstinspires.ftc.deimoscode.Autonomo.regional;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.deimoscode.Hardwareñ;

@Autonomous (name = "PatoRojo12", group = "Autonomus")
public class PatoRojoP12 extends LinearOpMode {

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

        FrontalI.setDirection(DcMotorSimple.Direction.REVERSE);
        TraseroI.setDirection(DcMotorSimple.Direction.REVERSE);

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
        sleep(1300);
        FrontalD.setPower(0);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(0);
        sleep(500);
        FrontalD.setPower(-.5);
        TraseroD.setPower(-.5);
        FrontalI.setPower(.5);
        TraseroI.setPower(.5);
        sleep(10);
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
        sleep(250);
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
        sleep(2400);
        FrontalD.setPower(0);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(0);
        sleep(400);
        Elevador.setPower(-.6);
        sleep(750);
        Elevador.setPower(0);
        sleep(800);
        Garra.setPosition(0);
        FrontalD.setPower(-.4);
        TraseroD.setPower(-.4);
        FrontalI.setPower(-.4);
        TraseroI.setPower(-.4);
        sleep(1800);
        FrontalD.setPower(0);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(0);
        sleep(200);
        FrontalD.setPower(-.3);
        TraseroD.setPower(-.3);
        FrontalI.setPower(.3);
        TraseroI.setPower(.3);
        sleep(200);
        FrontalD.setPower(-.3);  //movimiento a la Derecha
        TraseroD.setPower(.3);
        FrontalI.setPower(.3);
        TraseroI.setPower(-.3);
        sleep(2500);
        FrontalD.setPower(0);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(0);
        sleep(400);
        Pato.setPower(.8);
        sleep(3500);
        Pato.setPower(0);
        sleep(200);
        FrontalD.setPower(.3);  //movimiento a la Izquierda
        TraseroD.setPower(-.3);
        FrontalI.setPower(-.3);
        TraseroI.setPower(.3);
        sleep(1200);
        FrontalD.setPower(0);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(0);
        sleep(300);
        FrontalD.setPower(.3);
        TraseroD.setPower(.3);
        FrontalI.setPower(-.3);
        TraseroI.setPower(-.3);
        sleep(250);
        FrontalD.setPower(0);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(0);
        sleep(300);
        FrontalD.setPower(.3);  //movimiento a la Izquierda
        TraseroD.setPower(-.3);
        FrontalI.setPower(-.3);
        TraseroI.setPower(.3);
        sleep(1800);
        FrontalD.setPower(0);
        TraseroD.setPower(0);
        FrontalI.setPower(0);
        TraseroI.setPower(0);
        sleep(300);
        FrontalD.setPower(-.3);
        TraseroD.setPower(-.3);
        FrontalI.setPower(-.3);
        TraseroI.setPower(-.3);
        sleep(900);



    }
}


