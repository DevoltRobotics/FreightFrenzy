package com.github.serivesmejia.deltasimple

import com.qualcomm.robotcore.hardware.HardwareMap

abstract class SimpleHardware {

    lateinit var hdwMap: HardwareMap

    fun initHardware(hardwareMap: HardwareMap) {
        hdwMap = hardwareMap
        init()
    }
    
    protected open fun init() { }

    inline fun <reified T> hardware(name: String): Lazy<T> = lazy {
        require(::hdwMap.isInitialized) { "The HardwareMap is not defined, call initHardware(hardwareMap)" }
        hdwMap.get(T::class.java, name)!!
    }


}