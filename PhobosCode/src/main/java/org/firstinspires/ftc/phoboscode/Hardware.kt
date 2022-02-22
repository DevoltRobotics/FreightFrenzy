package org.firstinspires.ftc.phoboscode

import com.github.serivesmejia.deltasimple.SimpleHardware
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.phoboscode.rr.drive.SampleMecanumDrive

class Hardware : SimpleHardware() {

    val drive by lazy { SampleMecanumDrive(hdwMap)}

    val intakeMotor by hardware<DcMotor>("in")
    val carouselMotor by hardware<DcMotor>("ca")

    val sliderMotor by hardware<DcMotor>("sl")
    val boxServo by hardware<Servo>("bx")

    val turretYawServo by hardware<Servo>("ty")
    val turretPitchServo by hardware<Servo>("tp")

    val turretTapeMotor by hardware<DcMotor>("tt")

}