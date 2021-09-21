/*
 * Copyright (c) 2020 FTC Delta Robotics #9351 - Sebastian Erives
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.serivesmejia.deltadrive.hardware

import com.arcrobotics.ftclib.hardware.motors.MotorEx
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import com.qualcomm.robotcore.hardware.HardwareMap

@Suppress("UNUSED")
class DeltaHardwareHolonomic(hardwareMap: HardwareMap, private val positionP: Double) : DeltaHardware(hardwareMap) {

    lateinit var wheelFrontLeft: DcMotorEx
        private set
    lateinit var wheelFrontRight: DcMotorEx
        private set
    lateinit var wheelBackLeft: DcMotorEx
        private set
    lateinit var wheelBackRight: DcMotorEx
        private set

    lateinit var ftclibFL: MotorEx
        private set
    lateinit var ftclibFR: MotorEx
        private set
    lateinit var ftclibBL: MotorEx
        private set
    lateinit var ftclibBR: MotorEx
        private set

    private var initialized = false

    init {
        this.type = Type.HOLONOMIC
    }

    /**
     * Initialize motors.
     * @param frontleft The front left motor of the chassis.
     * @param frontright The front right motor of the chassis.
     * @param backleft The back left motor of the chassis.
     * @param backright The back right motor of the chassis.
     * @param brake brake the motors when their power is 0
     */
     fun initHardware(frontleft: DcMotor, frontright: DcMotor, backleft: DcMotor, backright: DcMotor, brake: Boolean = true) {
        if(initialized) {
            initialized = false
            return
        }

        for((name, motor) in hardwareMap.dcMotor.entrySet()) {
            if(motor == frontleft)
                ftclibFL = MotorEx(hardwareMap, name)
            else if(motor == frontright)
                ftclibFR = MotorEx(hardwareMap, name)
            else if(motor == backleft)
                ftclibBL = MotorEx(hardwareMap, name)
            else if(motor == backright)
                ftclibBR = MotorEx(hardwareMap, name)
        }

        wheelFrontLeft = frontleft as DcMotorEx
        wheelFrontRight = frontright as DcMotorEx
        wheelBackLeft = backleft as DcMotorEx
        wheelBackRight = backright as DcMotorEx

        ftclibFL.positionCoefficient = positionP
        ftclibFR.positionCoefficient = positionP
        ftclibBL.positionCoefficient = positionP
        ftclibBR.positionCoefficient = positionP

        updateChassisMotorsArray()
         
        internalInit(brake)
    }

    /**
     * Initialize motors.
     * @param frontleft The name of front left motor of the chassis.
     * @param frontright The name of front right motor of the chassis.
     * @param backleft The name of back left motor of the chassis.
     * @param backright The name of back right motor of the chassis.
     * @param brake brake the motors when their power is 0
     */
    fun initHardware(frontLeft: String, frontRight: String, backLeft: String, backRight: String, brake: Boolean = true) {
        initHardware(
                hardwareMap.get(DcMotorEx::class.java, frontLeft),
                hardwareMap.get(DcMotorEx::class.java, frontRight),
                hardwareMap.get(DcMotorEx::class.java, backLeft),
                hardwareMap.get(DcMotorEx::class.java, backRight),
                brake
        )
    }

    override fun updateChassisMotorsArray() {
        chassisMotorsArray = arrayOf(wheelFrontLeft, wheelFrontRight, wheelBackLeft, wheelBackRight)
    }

    fun setMotorPowers(frontleft: Double, frontright: Double, backleft: Double, backright: Double) {
        super.setMotorPowers(
                frontleft,
                frontright,
                backleft,
                backright
        )
    }

     fun setTargetPositions(frontleft: Int, frontright: Int, backleft: Int, backright: Int) {
         super.setTargetPositions(
                 frontleft,
                 frontright,
                 backleft,
                 backright
         )
    }

}
