package com.github.serivesmejia.deltasimple

import com.qualcomm.robotcore.hardware.HardwareMap

abstract class SimpleHardware {

    lateinit var hdwMap: HardwareMap

    open fun initHardware(hardwareMap: HardwareMap) {
        hdwMap = hardwareMap
    }

    inline fun <reified T> hardware(name: String): Lazy<T> = lazy { hdwMap.get(T::class.java, name)!! }


}