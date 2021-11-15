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
                drive.trajectorySequenceBuilder(Pose2d(-35.0, -55.0, Math.toRadians(90.0)))
                        // big wobble goal
                        .lineToLinearHeading(Pose2d(-11.0, -43.0, Math.toRadians(-90.0)))
                        // duck spinny boi
                        .lineToLinearHeading(Pose2d(-60.0, -60.0, Math.toRadians(170.0)))

                        // to the warehouse
                        .lineTo(Vector2d(-56.0, -56.0))
                        .lineToLinearHeading(Pose2d(-24.0, -55.0, Math.toRadians(0.0)))
                        .splineToLinearHeading(Pose2d(23.0, -64.0, Math.toRadians(0.0)), 0.0)
                        // grab freight
                        .lineTo(Vector2d(50.0, -64.0))
                        // out of the warehouse
                        .lineTo(Vector2d(10.0, -64.0))
                        // put freight in big wobble
                        .lineToLinearHeading(Pose2d(-11.0, -43.0, Math.toRadians(-90.0)))

                        // to the warehouse again
                        .splineToLinearHeading(Pose2d(23.0, -64.0, Math.toRadians(0.0)), 0.0)
                        // grab freight
                        .lineTo(Vector2d(50.0, -64.0))
                        // out of the warehouse
                        .lineTo(Vector2d(10.0, -64.0))
                        // put freight in big wobble
                        .lineToLinearHeading(Pose2d(-11.0, -43.0, Math.toRadians(-90.0)))

                        // to the warehouse to park
                        .splineToLinearHeading(Pose2d(23.0, -64.0, Math.toRadians(0.0)), 0.0)
                        // park fully
                        .lineTo(Vector2d(40.0, -64.0))

                        .build()
            }
            .start()

}