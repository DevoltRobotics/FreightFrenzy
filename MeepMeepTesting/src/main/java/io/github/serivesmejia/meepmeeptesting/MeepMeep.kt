package io.github.serivesmejia.meepmeeptesting

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.noahbres.meepmeep.MeepMeep
import com.noahbres.meepmeep.MeepMeep.Background
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder
import com.noahbres.meepmeep.roadrunner.DriveShim

var startPóse = Pose2d(-35.0, 60.0, Math.toRadians(180.0))

fun main() {
    val mm = MeepMeep(650)
            .setBackground(Background.FIELD_FREIGHTFRENZY_ADI_DARK) // Set theme
            .setBackgroundAlpha(1f)

    val robot = DefaultBotBuilder(mm)
            .setDimensions(12.5, 18.0)
            .setColorScheme(ColorSchemeRedDark())
            // Set constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
            .setConstraints(60.0, 60.0, Math.toRadians(180.0), Math.toRadians(180.0), 15.0)
            .setStartPose(startPóse)
            .followTrajectorySequence { drive: DriveShim ->
                drive.trajectorySequenceBuilder(startPóse)
                    .lineToSplineHeading(Pose2d(-23.5, 31.0, Math.toRadians(249.0)))
                    .UNSTABLE_addDisplacementMarkerOffset(0.0) {
                        println("poner cubo")
                    }
                    .lineToSplineHeading(Pose2d(-59.5, 59.5, Math.toRadians(210.0)))
                    .lineToSplineHeading(Pose2d(-58.0, 35.0, Math.toRadians(0.0)))
                    .build();
            }

    mm.addEntity(robot).start()
}