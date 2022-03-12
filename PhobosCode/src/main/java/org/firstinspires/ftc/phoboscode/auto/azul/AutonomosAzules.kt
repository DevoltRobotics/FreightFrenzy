package org.firstinspires.ftc.phoboscode.auto.azul

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "A-Completo Izquierda Pato", group = "Final")
class AutonomoCompletoAzulIzquierdaPato : AutonomoCompletoAzul(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)

@Autonomous(name = "A-Completo Izquierda", group = "Final")
class AutonomoCompletoAzulIzquierda : AutonomoCompletoAzul(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 1
)

@Autonomous(name = "A-Completo Derecha Pato", group = "Final")
class AutonomoCompletoAzulDerechaPato : AutonomoCompletoAzul(
    startPosition = StartPosition.DUCKS_NEAREST,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)
@Autonomous(name = "A-Completo Derecha", group = "Final")
class AutonomoCompletoAzulDerecha : AutonomoCompletoAzul(
    startPosition = StartPosition.DUCKS_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 1
)