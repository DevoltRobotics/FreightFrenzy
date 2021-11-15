@file:Suppress("UNUSED")
package org.firstinspires.ftc.phoboscode.auto

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition

abstract class AutonomoCompleto(val startPosition: Pose2d): AutoBase() {

    val bigWobblePose = Pose2d(-11.0, -43.0, Math.toRadians(-90.0))

    override fun setup() {
        super.setup()

        drive.poseEstimate = startPosition
    }

    override fun sequence(teamMarkerPosition: TeamMarkerPosition) =
            drive.trajectorySequenceBuilder(startPosition)
                    // put X cube in big wobble
                    .lineToLinearHeading(bigWobblePose)
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
                    .lineToLinearHeading(bigWobblePose)

                    // to the warehouse again
                    .splineToLinearHeading(Pose2d(23.0, -64.0, Math.toRadians(0.0)), 0.0)
                    // grab freight
                    .lineTo(Vector2d(50.0, -64.0))
                    // out of the warehouse
                    .lineTo(Vector2d(10.0, -64.0))
                    // put freight in big wobble
                    .lineToLinearHeading(bigWobblePose)

                    // to the warehouse to park
                    .splineToLinearHeading(Pose2d(23.0, -64.0, Math.toRadians(0.0)), 0.0)
                    // park fully
                    .lineTo(Vector2d(40.0, -64.0))

                    .build()

}

@Autonomous(name = "Completo Izquierda", group = "Final")
class AutonomoCompletoIzquierda : AutonomoCompleto(
        Pose2d(-35.0, -55.0, Math.toRadians(90.0))
)

@Autonomous(name = "Completo Derecha", group = "Final")
class AutonomoCompletoDerecha : AutonomoCompleto(
        Pose2d(1.0, -55.0, Math.toRadians(90.0))
)