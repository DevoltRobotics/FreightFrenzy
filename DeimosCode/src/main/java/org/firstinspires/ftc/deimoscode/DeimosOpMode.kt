package org.firstinspires.ftc.deimoscode

import org.firstinspires.ftc.commoncode.CommonOpMode

abstract class DeimosOpMode(usingRR: Boolean = false) : CommonOpMode(usingRR) {

    override val hardware = Hardware()

}