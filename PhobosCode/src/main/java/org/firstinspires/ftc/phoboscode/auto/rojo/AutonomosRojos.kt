package org.firstinspires.ftc.phoboscode.auto.rojo

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "R-Izquierda Pato", group = "Final", preselectTeleOp = "TeleOp Rojo")
class AutonomoCompletoRojoIzquierdaPato : AutonomoCompletoRojo(
    startPosition = StartPosition.DUCKS_NEAREST,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)

@Autonomous(name = "R-Completo Izquierda", group = "Final", preselectTeleOp = "TeleOp Rojo")
class AutonomoCompletoRojoIzquierda : AutonomoCompletoRojo(
    startPosition = StartPosition.DUCKS_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 2
)

@Autonomous(name = "R-Derecha Pato", group = "Final", preselectTeleOp = "TeleOp Rojo")
class AutonomoCompletoRojoDerechaPato : AutonomoCompletoRojo(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)
@Autonomous(name = "R-Completo Derecha", group = "Final", preselectTeleOp = "TeleOp Rojo")
class AutonomoCompletoRojoDerecha : AutonomoCompletoRojo(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 2
)

@Autonomous(name = "R-Cubo Derecha", group = "Final", preselectTeleOp = "TeleOp Rojo")
class AutonomoCuboRojoDerecha : AutonomoCompletoRojo(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 0
)