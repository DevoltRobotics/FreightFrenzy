package io.github.serivesmejia.meepmeeptesting

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.noahbres.meepmeep.MeepMeep
import com.noahbres.meepmeep.MeepMeep.Background
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder
import com.noahbres.meepmeep.roadrunner.DriveShim

val deimosStartPosition = Pose2d(-35.0, -62.0, Math.toRadians(90.0))
val phobosStartPosition = Pose2d(1.0, -62.0, Math.toRadians(90.0))

fun main() {
    val mm = MeepMeep(650)
            .setBackground(Background.FIELD_FREIGHTFRENZY_ADI_DARK) // Set theme
            .setBackgroundAlpha(1f)

    val robotPhobos = DefaultBotBuilder(mm)
            .setDimensions(12.5, 18.0)
            .setColorScheme(ColorSchemeRedDark())
            // Set constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
            .setConstraints(60.0, 60.0, Math.toRadians(180.0), Math.toRadians(180.0), 15.0)
            .setStartPose(phobosStartPosition)
            .followTrajectorySequence { drive: DriveShim ->
                phobosSequence(drive)
            }

    val robotDeimos = DefaultBotBuilder(mm)
            .setDimensions(12.5, 18.0)
            .setColorScheme(ColorSchemeBlueDark())
            // Set constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
            .setConstraints(60.0, 60.0, Math.toRadians(180.0), Math.toRadians(180.0), 15.0)
            .setStartPose(deimosStartPosition)
            .followTrajectorySequence { drive: DriveShim ->
                deimosSequence(drive)
            }

    mm.addEntity(robotPhobos)/*.addEntity(robotDeimos)*/.start()
}

val bigWobblePose = Pose2d(-11.0, -43.0, Math.toRadians(-90.0))

val phobosDoDucks = true
val cycles = 4

fun phobosSequence(drive: DriveShim) = drive.trajectorySequenceBuilder(phobosStartPosition).run {
    // put X cube in big wobble
    UNSTABLE_addTemporalMarkerOffset(0.0) {
        println("lift up")
    }
    UNSTABLE_addTemporalMarkerOffset(0.5) {
        println("drop and lift down")
    }
    lineToLinearHeading(bigWobblePose)
    waitSeconds(2.0)

    if(phobosDoDucks) {
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
        if(phobosDoDucks) {
            // to the warehouse
            lineTo(Vector2d(-56.0, -56.0))
            lineToLinearHeading(Pose2d(-24.0, -55.0, Math.toRadians(0.0)))
        }

        repeat(cycles) {
            // to the warehouse
            splineToSplineHeading(Pose2d(23.0, -64.0, Math.toRadians(0.0)), 0.0)
            // grab freight
            UNSTABLE_addTemporalMarkerOffset(-0.5) {
                println("intake on")
            }
            lineTo(Vector2d(50.0, -64.0))
            UNSTABLE_addTemporalMarkerOffset(1.0) {
                println("intake off")
            }
            // out of the warehouse
            lineTo(Vector2d(23.0, -64.0))
            UNSTABLE_addTemporalMarkerOffset(0.0) {
                println("lift up high")
            }
            // put freight in big wobble
            UNSTABLE_addTemporalMarkerOffset(1.0) {
                println("drop and lift down")
            }
            splineToSplineHeading(bigWobblePose, Math.toRadians(90.0))
            waitSeconds(0.8)
        }
    }

    // to the warehouse to park
    splineToSplineHeading(Pose2d(23.0, -64.0, Math.toRadians(0.0)), 0.0)
    // park fully
    lineTo(Vector2d(40.0, -64.0))
    // in case alliance wants to park too
    strafeTo(Vector2d(40.0, -46.0))
}.build()

fun deimosSequence(drive: DriveShim) = drive.trajectorySequenceBuilder(deimosStartPosition).run {
    // duck spinny boi
    lineToLinearHeading(Pose2d(-60.0, -60.0, Math.toRadians(170.0)))
    UNSTABLE_addTemporalMarkerOffset(0.0) {
        println("carousel rotate")
    }
    waitSeconds(3.0)
    UNSTABLE_addTemporalMarkerOffset(0.0) {
        println("carousel stop")
    }

    waitSeconds(6.5)

    // put X cube in big wobble
    UNSTABLE_addTemporalMarkerOffset(0.0) {
        println("lift up")
    }
    UNSTABLE_addTemporalMarkerOffset(0.5) {
        println("drop and lift down")
    }
    lineToLinearHeading(bigWobblePose.copy(heading = Math.toRadians(90.0)))
    waitSeconds(2.0)

    // wait in start position
    lineToLinearHeading(deimosStartPosition.copy(y = deimosStartPosition.y + 2, heading = .00))
    waitSeconds(6.0)

    // to the warehouse to park
    lineTo(Vector2d(23.0, -64.0))
    // park fully
    lineTo(Vector2d(40.0, -64.0))
}.build()