package org.firstinspires.ftc.phoboscode.auto

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "R-Completo Izquierda Pato", group = "Final")
class AutonomoCompletoRojoIzquierdaPato : AutonomoCompleto(
    alliance = Alliance.RED,
    startPosition = StartPosition.LEFT,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)

@Autonomous(name = "R-Completo Izquierda", group = "Final")
class AutonomoCompletoRojoIzquierda : AutonomoCompleto(
    alliance = Alliance.RED,
    startPosition = StartPosition.LEFT,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 1
)

@Autonomous(name = "R-Completo Derecha Pato", group = "Final")
class AutonomoCompletoRojoDerechaPato : AutonomoCompleto(
    alliance = Alliance.RED,
    startPosition = StartPosition.RIGHT,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)
@Autonomous(name = "R-Completo Derecha", group = "Final")
class AutonomoCompletoRojoDerecha : AutonomoCompleto(
    alliance = Alliance.RED,
    startPosition = StartPosition.RIGHT,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 1
)