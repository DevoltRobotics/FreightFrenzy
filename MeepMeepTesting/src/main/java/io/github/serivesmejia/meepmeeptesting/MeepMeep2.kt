package io.github.serivesmejia.meepmeeptesting

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.noahbres.meepmeep.MeepMeep
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder
import com.noahbres.meepmeep.roadrunner.DriveShim

enum class StartPosition(
    val startPose: Pose2d,
    val startWobblePose: Pose2d
) {
    DUCKS_NEAREST(
        Pose2d(-39.0, 62.0, Math.toRadians(270.0)), // start
        Pose2d(-23.0, 37.0, Math.toRadians(150.0))
    ),
    WAREHOUSE_NEAREST(
        Pose2d(11.0, 63.0, Math.toRadians(270.0)),
        Pose2d(3.0, 38.5, Math.toRadians(75.0))
    )
}

enum class ParkPosition {
    NONE, WAREHOUSE, STORAGE_UNIT
}

val startPosition  = StartPosition.DUCKS_NEAREST
val parkPosition = ParkPosition.STORAGE_UNIT
val doDucks = true
val cycles = 4

val bigWobblePose = Pose2d(2.0, 35.0, Math.toRadians(60.0))

fun main() {
    val mm = MeepMeep(650)
        .setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_ADI_DARK) // Set theme
        .setBackgroundAlpha(1f)

    val robotPhobos = DefaultBotBuilder(mm)
        .setDimensions(12.5, 18.0)
        .setColorScheme(ColorSchemeRedDark())
        // Set constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
        .setConstraints(60.0, 60.0, Math.toRadians(180.0), Math.toRadians(180.0), 15.0)
        .setStartPose(phobosStartPosition)
        .followTrajectorySequence { drive: DriveShim ->

            drive.trajectorySequenceBuilder(startPosition.startPose).run {
                lineToSplineHeading(startPosition.startWobblePose)

                waitSeconds(2.0)

                if(doDucks) {
                    // duck spinny boi
                    lineToLinearHeading(Pose2d(-63.5, 62.0, Math.toRadians(180.0)))
                    waitSeconds(3.0)
                }

                if(cycless >= 1) {
                    if(doDucks) {
                        // to the warehouse
                        lineTo(Vector2d(-56.0, 55.5))
                        lineToLinearHeading(Pose2d(-24.0, 55.0, Math.toRadians(0.0)))
                    }

                    var currentGrabCubeX = 54.0
                    var minusBigWobblePose = Pose2d()

                    /*
                    Generating repetitive trajectories for each cycle
                     */
                    repeat(cycles) {
                        // to the warehouse
                        splineToSplineHeading(Pose2d(25.0, 63.0, Math.toRadians(0.0)), Math.toRadians(365.0))

                        // grab freight
                        splineTo(Vector2d(currentGrabCubeX, 63.2), 0.0)

                        // out of the warehouse
                        lineTo(Vector2d(18.0, 63.2))

                        // put freight in big wobble
                        splineToSplineHeading(bigWobblePose.minus(minusBigWobblePose), Math.toRadians(270.0))
                        waitSeconds(0.9) // wait for the freight to fall

                        currentGrabCubeX *= 1.06
                        minusBigWobblePose = minusBigWobblePose.plus(Pose2d(-1.0, -0.7))
                    }
                }

                when(parkPosition) {
                    ParkPosition.NONE -> this
                    ParkPosition.WAREHOUSE -> {
                        // to the warehouse to park
                        splineToSplineHeading(Pose2d(25.0, 63.0, Math.toRadians(0.0)), Math.toRadians(3.0))
                        // park fully
                        lineTo(Vector2d(50.0, 64.0))
                        // in case alliance wants to park too
                        strafeTo(Vector2d(50.0, 40.0))
                    }
                    ParkPosition.STORAGE_UNIT -> {
                        lineToSplineHeading(Pose2d(-62.0, 32.0, 0.0))
                    }
                }
            }.build()
        }

    mm.addEntity(robotPhobos).start()
}