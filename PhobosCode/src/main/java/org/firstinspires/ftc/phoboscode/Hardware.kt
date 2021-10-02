package org.firstinspires.ftc.phoboscode

import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.commoncode.CommonHardware

class Hardware : CommonHardware() {

    val testServo by hardware<Servo>("tst")

}