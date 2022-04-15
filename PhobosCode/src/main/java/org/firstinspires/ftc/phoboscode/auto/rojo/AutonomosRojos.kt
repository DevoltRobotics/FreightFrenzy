package org.firstinspires.ftc.phoboscode.auto.rojo

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled

@Autonomous(name = "R-Izquierda Pato", group = "FinalR", preselectTeleOp = "TeleOp Rojo")
class AutonomoCompletoRojoIzquierdaPato : AutonomoCompletoRojo(
    startPosition = StartPosition.DUCKS_NEAREST,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)

@Autonomous(name = "R-Completo Izquierda", group = "FinalR", preselectTeleOp = "TeleOp Rojo")
@Disabled
class AutonomoCompletoRojoIzquierda : AutonomoCompletoRojo(
    startPosition = StartPosition.DUCKS_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 1
)

@Autonomous(name = "R-Derecha Pato", group = "FinalR", preselectTeleOp = "TeleOp Rojo")
@Disabled
class AutonomoCompletoRojoDerechaPato : AutonomoCompletoRojo(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)
@Autonomous(name = "R-Completo Derecha", group = "FinalR", preselectTeleOp = "TeleOp Rojo")
class AutonomoCompletoRojoDerecha : AutonomoCompletoRojo(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 1
)

@Autonomous(name = "R-Cubo Derecha", group = "FinalR", preselectTeleOp = "TeleOp Rojo")
class AutonomoCuboRojoDerecha : AutonomoCompletoRojo(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 0
)