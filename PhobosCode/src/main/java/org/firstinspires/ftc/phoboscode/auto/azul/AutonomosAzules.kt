package org.firstinspires.ftc.phoboscode.auto.azul

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled

@Autonomous(name = "A-Izquierda Pato", group = "FinalA", preselectTeleOp = "TeleOp Azul")
@Disabled
class AutonomoCompletoAzulIzquierdaPato : AutonomoCompletoAzul(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)

@Autonomous(name = "A-Completo Izquierda", group = "FinalA", preselectTeleOp = "TeleOp Azul")
class AutonomoCompletoAzulIzquierda : AutonomoCompletoAzul(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 1
)

@Autonomous(name = "A-Cubo Izquierda", group = "FinalA", preselectTeleOp = "TeleOp Azul")
class AutonomoCuboAzulIzquierda : AutonomoCompletoAzul(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 0
)

@Autonomous(name = "A-Derecha Pato", group = "FinalA", preselectTeleOp = "TeleOp Azul")
class AutonomoCompletoAzulDerechaPato : AutonomoCompletoAzul(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)
@Autonomous(name = "A-Completo Derecha", group = "FinalA", preselectTeleOp = "TeleOp Azul")
@Disabled
class AutonomoCompletoAzulDerecha : AutonomoCompletoAzul(
    startPosition = StartPosition.DUCKS_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 1
)