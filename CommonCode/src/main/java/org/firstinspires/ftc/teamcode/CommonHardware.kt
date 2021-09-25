package org.firstinspires.ftc.teamcode

import com.github.serivesmejia.deltadrive.hardware.DeltaHardwareHolonomic
import com.github.serivesmejia.deltasimple.SimpleHardware
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

open class CommonHardware : SimpleHardware() {

    val fl by hardware<DcMotor>("fl")
    val fr by hardware<DcMotor>("fr")
    val bl by hardware<DcMotor>("bl")
    val br by hardware<DcMotor>("br")

    val deltaHardware by lazy {
        val hdw = DeltaHardwareHolonomic(hdwMap)
        hdw.initHardware(fl, fr, bl, br, true)

        fl.direction = DcMotorSimple.Direction.REVERSE
        bl.direction = DcMotorSimple.Direction.REVERSE

        hdw
    }

}