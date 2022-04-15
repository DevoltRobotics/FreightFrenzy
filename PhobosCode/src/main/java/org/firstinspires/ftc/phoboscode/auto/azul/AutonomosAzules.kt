package org.firstinspires.ftc.phoboscode.auto.azul

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "A-Izquierda Pato", group = "Final", preselectTeleOp = "TeleOp Azul")
class AutonomoCompletoAzulIzquierdaPato : AutonomoCompletoAzul(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)

@Autonomous(name = "A-Completo Izquierda", group = "Final", preselectTeleOp = "TeleOp Azul")
class AutonomoCompletoAzulIzquierda : AutonomoCompletoAzul(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 2
)

@Autonomous(name = "A-Cubo Izquierda", group = "Final", preselectTeleOp = "TeleOp Azul")
class AutonomoCuboAzulIzquierda : AutonomoCompletoAzul(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 0
)

@Autonomous(name = "A-Derecha Pato", group = "Final", preselectTeleOp = "TeleOp Azul")
class AutonomoCompletoAzulDerechaPato : AutonomoCompletoAzul(
    startPosition = StartPosition.DUCKS_NEAREST,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)
@Autonomous(name = "A-Completo Derecha", group = "Final", preselectTeleOp = "TeleOp Azul")
class AutonomoCompletoAzulDerecha : AutonomoCompletoAzul(
    startPosition = StartPosition.DUCKS_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 1
)