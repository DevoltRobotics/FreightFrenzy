package org.firstinspires.ftc.phoboscode.subsystem

import com.acmerobotics.dashboard.config.Config
import com.github.serivesmejia.deltacommander.DeltaSubsystem
import com.github.serivesmejia.deltacommander.command.DeltaInstantCmd
import com.github.serivesmejia.deltacommander.dsl.deltaSequence
import com.github.serivesmejia.deltacommander.subsystem
import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.phoboscode.command.intake.IntakeStopCmd
import org.firstinspires.ftc.phoboscode.subsystem.Intake.intakedDistance
import org.firstinspires.ftc.phoboscode.subsystem.Intake.pushPosition
import org.firstinspires.ftc.phoboscode.subsystem.Intake.savePosition
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

class IntakeSubsystem(
    val intakeMotor: DcMotor,
    val pushServo: Servo,
    val colorSensor: RevColorSensorV3
) : DeltaSubsystem() {

    lateinit var liftSub: LiftSubsystem

    private val lowDistanceTimer = ElapsedTime()
    var pushing = false
        private set
    private var pushingAutomatically = false

    var lastDistance = 0.0
        private set

    init {
        defaultCommand = IntakeStopCmd()
        intakeMotor.direction = DcMotorSimple.Direction.REVERSE
    }

    override fun init() {
        pushServo.position = savePosition
        liftSub = subsystem()
    }

    override fun loop() {
        lastDistance = colorSensor.getDistance(DistanceUnit.INCH)

        if(lastDistance >= intakedDistance) {
            lowDistanceTimer.reset()
        } else if(lowDistanceTimer.seconds() > 0.5 && !pushing && intakeMotor.power >= 0.0 && liftSub.motorTicks <= Lift.lowPosition / 2.0) {
            + pushServoSequence()
            pushingAutomatically = true
        } else if((liftSub.motorTicks > Lift.lowPosition / 2.0 || intakeMotor.power < 0.0) && pushingAutomatically) {
            pushServo.position = savePosition
        }
    }

    fun pushServoSequence() = deltaSequence {
        - DeltaInstantCmd {
            pushServo.position = pushPosition
            pushing = true
        }

        - waitForSeconds(0.8)

        - DeltaInstantCmd {
            pushServo.position = savePosition

            pushing = false
            pushingAutomatically = false
            lowDistanceTimer.reset()
        }
    }

}

@Config
object Intake {
    @JvmField var intakedDistance = 2.0

    @JvmField var pushPosition = 0.7
    @JvmField var savePosition = 1.0
}