package io.github.serivesmejia.meepmeeptesting

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.noahbres.meepmeep.MeepMeep
import com.noahbres.meepmeep.MeepMeep.Background
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark
import com.noahbres.meepmeep.roadrunner.DriveShim


fun main() {

    var mm = MeepMeep(650)
            .setBackground(Background.FIELD_FREIGHT_FRENZY) // Set theme
            .setTheme(ColorSchemeRedDark()) // Background opacity from 0-1
            .setBackgroundAlpha(1f)
            // Set constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
            .setConstraints(60.0, 60.0, Math.toRadians(180.0), Math.toRadians(180.0), 15.0)
            .setStartPose(Pose2d(11.0, -55.0, Math.toRadians(90.0)))
            .followTrajectorySequence { drive: DriveShim ->
                sequence(drive)
            }
            .start()

}

val startPosition = Pose2d(-35.0, -62.0, Math.toRadians(90.0))
val bigWobblePose = Pose2d(-11.0, -43.0, Math.toRadians(-90.0))

val doDucks = false
val cycles = 2

fun sequence(drive: DriveShim) = drive.trajectorySequenceBuilder(startPosition).run {
    // put X cube in big wobble
    UNSTABLE_addTemporalMarkerOffset(0.0) {
        println("lift up")
    }
    UNSTABLE_addTemporalMarkerOffset(0.5) {
        println("drop and lift down")
    }
    lineToLinearHeading(bigWobblePose)
    waitSeconds(2.0)

    if(doDucks) {
        // duck spinny boi
        lineToLinearHeading(Pose2d(-60.0, -60.0, Math.toRadians(170.0)))
        UNSTABLE_addTemporalMarkerOffset(0.0) {
            println("carousel rotate")
        }
        waitSeconds(3.0)
        UNSTABLE_addTemporalMarkerOffset(0.0) {
            println("carousel stop")
        }
    }

    if(cycles >= 1) {
        if(doDucks) {
            // to the warehouse
            lineTo(Vector2d(-56.0, -56.0))
            lineToLinearHeading(Pose2d(-24.0, -55.0, Math.toRadians(0.0)))
        }

        repeat(cycles) {
            // to the warehouse
            splineToLinearHeading(Pose2d(23.0, -64.0, Math.toRadians(0.0)), 0.0)
            // grab freight
            UNSTABLE_addTemporalMarkerOffset(-0.5) {
                println("intake on")
            }
            lineTo(Vector2d(50.0, -64.0))
            UNSTABLE_addTemporalMarkerOffset(1.0) {
                println("intake off")
            }
            // out of the warehouse
            lineTo(Vector2d(10.0, -64.0))
            UNSTABLE_addTemporalMarkerOffset(0.0) {
                println("lift up high")
            }
            // put freight in big wobble
            UNSTABLE_addTemporalMarkerOffset(1.0) {
                println("drop and lift down")
            }
            lineToLinearHeading(bigWobblePose)
            waitSeconds(2.0)
        }
    }

    // to the warehouse to park
    splineToLinearHeading(Pose2d(23.0, -64.0, Math.toRadians(0.0)), 0.0)
    // park fully
    lineTo(Vector2d(40.0, -64.0))
}.build()