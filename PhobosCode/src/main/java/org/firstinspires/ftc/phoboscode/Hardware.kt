package org.firstinspires.ftc.phoboscode

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.commoncode.CommonHardware

class   Hardware : CommonHardware() {

    val intakeMotor by hardware<DcMotor>("in")
    val carouselMotor by hardware<DcMotor>("ca")

    val sliderMotor by hardware<DcMotor>("sl")
    val boxServo by hardware<Servo>("bx")

}