package org.firstinspires.ftc.deimoscode

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.commoncode.CommonHardware

class Hardware : CommonHardware() {

    val motorClawVertical by hardware<DcMotor>("CV")
    val motorClawRotate by hardware<DcMotor>("CR")

    val servoClaw by hardware<Servo>("SC")

}