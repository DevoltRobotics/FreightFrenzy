package org.firstinspires.ftc.deimoscode.teleop

import com.github.serivesmejia.deltacommander.deltaScheduler
import com.github.serivesmejia.deltaevent.gamepad.button.Button
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.deimoscode.DeimosOpMode
import org.firstinspires.ftc.commoncode.commander.command.MecanumDriveCommand
import org.firstinspires.ftc.deimoscode.commander.command.arm.ArmClawCloseCmd
import org.firstinspires.ftc.deimoscode.commander.command.arm.ArmClawOpenCmd
import org.firstinspires.ftc.deimoscode.commander.command.arm.ArmMoveCmd

@TeleOp(name = "TeleOp")
class DeimosTeleOp : DeimosOpMode() {

    override fun setup() {
        + MecanumDriveCommand(gamepad1) // manejar las llantas mecanum con el gamepad 1

        + ArmMoveCmd(
                { gamepad2.right_stick_x.toDouble() }, // mover el brazo de izquierda a derecha con el eje x del jostick derecho
                { -gamepad2.left_stick_y.toDouble() }, // mover el brazo de arriba a abajo con el eje y de joystick izquierdo
                0.1, // nos moveremos al 10% de velocidad
                0.2 // el minimo en los joysticks para que el brazo se mueva
        )

        superGamepad2.scheduleOn(Button.LEFT_TRIGGER,
                ArmClawOpenCmd(), // abrir la garra cuando se presiona el left trigger
                ArmClawCloseCmd() // cerrar la garra cuando deja de presionar
        )

        superGamepad2.scheduleOn(Button.RIGHT_TRIGGER,
                ArmClawOpenCmd(), // abrir la garra cuando se presiona el right trigger
                ArmClawCloseCmd() // cerrar la garra cuando se deja de presionar
        )

        deltaScheduler.update()
    }

}