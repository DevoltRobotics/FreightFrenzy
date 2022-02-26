package org.firstinspires.ftc.commoncode.util

fun angleAdd(i: Double, i1: Double): Double {
    var i = i
    i += i1

    while (i >= 360.0) {
        i -= 360
    }
    while (i < 0.0) {
        i += 360.0
    }

    return i
}