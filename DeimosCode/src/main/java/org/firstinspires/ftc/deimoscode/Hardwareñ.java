package org.firstinspires.ftc.deimoscode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.deimoscode.rr.drive.SampleMecanumDrive;

@Config
public class Hardwareñ {

    public static PIDCoefficients LIFT_PID = new PIDCoefficients(0.005, 0, 0);

    public static int HIGH_LIFT_POS = 2360;
    public static int MID_LIFT_POS = 1600;
    public static int LOW_LIFT_POS = 1100;

    private PIDFController liftController = new PIDFController(LIFT_PID);

    public SampleMecanumDrive drive;

    public DcMotor Pato;
    public DcMotor Extender;
    public DcMotor Elevador;
    public DcMotor Empuje;
    public Servo Absorber;
    public Servo Cebollin;


    public void initHardware(HardwareMap hardwareMap) {
        drive = new SampleMecanumDrive(hardwareMap);

        Pato = hardwareMap.dcMotor.get("Pato");
        Extender = hardwareMap.dcMotor.get("EXT");
        Elevador = hardwareMap.dcMotor.get("ELE");
        Absorber = hardwareMap.servo.get("Abs");
        Empuje = hardwareMap.dcMotor.get("Emp");
        Cebollin = hardwareMap.servo.get("Cebollin");
    }

    public void updateLift(int targetPos) {
        liftController.setTargetPosition(targetPos);
        Elevador.setPower(liftController.update(Elevador.getCurrentPosition()));
    }

}
