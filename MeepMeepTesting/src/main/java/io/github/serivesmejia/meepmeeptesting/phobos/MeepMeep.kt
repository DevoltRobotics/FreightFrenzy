package io.github.serivesmejia.meepmeeptesting.phobos

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
        .setDimensions(12.25, 18.0)
        .setColorScheme(ColorSchemeRedDark())
        // Set constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
        .setConstraints(60.0, 60.0, Math.toRadians(180.0), Math.toRadians(180.0), 15.0)
        .setStartPose(Pose2d(13.0, 65.0, Math.toRadians(0.0)))
        .followTrajectorySequence { drive: DriveShim ->
            drive.trajectorySequenceBuilder(Pose2d(13.0, 65.0, Math.toRadians(0.0))).run {
                lineToLinearHeading(
                    Pose2d(1.4, 33.6, Math.toRadians(40.0)),
                )

                lineToSplineHeading(
                    Pose2d(12.0, 56.0, Math.toRadians(0.0))//,
                    //Math.toRadians(0.0)
                )

                splineToSplineHeading(Pose2d(51.0, 64.0, Math.toRadians(0.0)), Math.toRadians(0.0))
            }.build()
        }

    mm.addEntity(robotPhobos).start()
}