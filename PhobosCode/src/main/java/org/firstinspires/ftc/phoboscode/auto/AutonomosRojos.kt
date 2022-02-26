package org.firstinspires.ftc.phoboscode.auto

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous

val izquierdaStartPose = Pose2d(-35.0, -62.0, Math.toRadians(90.0))
val izquierdaStartWobblePose = Pose2d(-36.0, -35.5, Math.toRadians(210.0))

val derechaStartPose = Pose2d(1.0, -62.0, Math.toRadians(90.0))

@Autonomous(name = "R-Completo Izquierda Pato", group = "Final")
class AutonomoCompletoRojoIzquierdaPato : AutonomoCompleto(
    izquierdaStartPose, // pose inicial
    izquierdaStartWobblePose,
    cycles = 0,
    parkPosition = ParkPosition.STORAGE_UNIT
)

@Autonomous(name = "R-Completo Izquierda", group = "Final")
class AutonomoCompletoRojoIzquierda : AutonomoCompleto(
    izquierdaStartPose, // pose inicial
    izquierdaStartWobblePose,
    cycles = 2, doDucks = false
)

@Autonomous(name = "R-Completo Derecha Pato", group = "Final")
class AutonomoCompletoRojoDerechaPato : AutonomoCompleto(
    derechaStartPose, // pose inicial
    cycles = 0,
    parkPosition = ParkPosition.STORAGE_UNIT
)
@Autonomous(name = "R-Completo Derecha", group = "Final")
class AutonomoCompletoRojoDerecha : AutonomoCompleto(
    derechaStartPose, // pose inicial
    cycles = 2,
    doDucks = false
)