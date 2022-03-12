package org.firstinspires.ftc.phoboscode.auto.rojo

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "R-Completo Izquierda Pato", group = "Final")
class AutonomoCompletoRojoIzquierdaPato : AutonomoCompletoRojo(
    startPosition = StartPosition.DUCKS_NEAREST,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)

@Autonomous(name = "R-Completo Izquierda", group = "Final")
class AutonomoCompletoRojoIzquierda : AutonomoCompletoRojo(
    startPosition = StartPosition.DUCKS_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 1
)

@Autonomous(name = "R-Completo Derecha Pato", group = "Final")
class AutonomoCompletoRojoDerechaPato : AutonomoCompletoRojo(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.STORAGE_UNIT,
    doDucks = true, cycles = 0
)
@Autonomous(name = "R-Completo Derecha", group = "Final")
class AutonomoCompletoRojoDerecha : AutonomoCompletoRojo(
    startPosition = StartPosition.WAREHOUSE_NEAREST,
    parkPosition = ParkPosition.WAREHOUSE,
    doDucks = false, cycles = 1
)