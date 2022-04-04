package org.firstinspires.ftc.phoboscode

import com.github.serivesmejia.deltasimple.SimpleHardware
import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.phoboscode.rr.drive.SampleMecanumDrive

class Hardware : SimpleHardware() {

    val drive by lazy { SampleMecanumDrive(hdwMap)}

    val intakeMotor by hardware<DcMotor>("in")
    val carouselMotor by hardware<DcMotor>("ca")

    val sliderMotor by hardware<DcMotor>("sl")
    val boxServo by hardware<Servo>("bx")

    val intakePushServo by hardware<Servo>("ip")
    val intakeColorSensor by hardware<RevColorSensorV3>("ic")

    val capArmMotor by hardware<DcMotor>("cp")

}