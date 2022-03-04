package org.firstinspires.ftc.phoboscode.auto

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "A-Completo Izquierda Pato", group = "Final")
class AutonomoCompletoAzulIzquierdaPato : AutonomoCompleto(
    alliance = Alliance.BLUE,
    startPosition = StartPosition.LEFT,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)

@Autonomous(name = "A-Completo Izquierda", group = "Final")
class AutonomoCompletoAzulIzquierda : AutonomoCompleto(
    alliance = Alliance.BLUE,
    startPosition = StartPosition.LEFT,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 1
)

@Autonomous(name = "A-Completo Derecha Pato", group = "Final")
class AutonomoCompletoAzulDerechaPato : AutonomoCompleto(
    alliance = Alliance.BLUE,
    startPosition = StartPosition.RIGHT,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)
@Autonomous(name = "A-Completo Derecha", group = "Final")
class AutonomoCompletoAzulDerecha : AutonomoCompleto(
    alliance = Alliance.BLUE,
    startPosition = StartPosition.RIGHT,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = true, cycles = 1
)