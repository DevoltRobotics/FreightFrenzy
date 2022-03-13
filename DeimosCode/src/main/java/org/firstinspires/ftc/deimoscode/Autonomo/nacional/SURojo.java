package org.firstinspires.ftc.deimoscode.Autonomo.nacional;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition;
import org.firstinspires.ftc.deimoscode.Hardwareñ;
import org.firstinspires.ftc.deimoscode.rr.trajectorysequence.TrajectorySequence;

@Autonomous(name = "SURojo", group = "###Autonomus")
public class SURojo extends AutonomoBase {

    Pose2d startPóse = new Pose2d(-35, -60, Math.toRadians(0));

    int liftPos = 0;

    @Override
    public void run() {
        hardware.drive.setPoseEstimate(startPóse);

        hardware.Absorber.setPosition(0);

        TrajectorySequence sequence = hardware.drive.trajectorySequenceBuilder(startPóse)
                .UNSTABLE_addDisplacementMarkerOffset(0.0, () -> {
                    if(detector.getPosition() == TeamMarkerPosition.LEFT) {
                        liftPos = Hardwareñ.LOW_LIFT_POS;
                    } else if(detector.getPosition() == TeamMarkerPosition.MIDDLE) {
                        liftPos = Hardwareñ.MID_LIFT_POS;
                    } else if(detector.getPosition() == TeamMarkerPosition.RIGHT) {
                        liftPos = Hardwareñ.HIGH_LIFT_POS;
                    }
                })

                // ir a poner cubo
                .lineToSplineHeading(new Pose2d(-21.5, -20.0, Math.toRadians(339)))

                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    hardware.Absorber.setPosition(0.7);
                })
                .waitSeconds(1.5)

                //Ir a Pato
                .lineToSplineHeading(new Pose2d(-60.7, -60.0, Math.toRadians(340)))

                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    hardware.Absorber.setPosition(0);
                    liftPos = Hardwareñ.LOW_LIFT_POS;
                })

                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    hardware.Pato.setPower(-0.6);
                })
                .UNSTABLE_addTemporalMarkerOffset(5, () -> {
                    hardware.Pato.setPower(0);
                })
                .waitSeconds(5)

                // estacionarse
                .lineToSplineHeading(new Pose2d(-60.0, -31.0, Math.toRadians(0)))
                .build();

        while(!isStarted() && !isStopRequested()) {
            telemetry.addData("position", detector.getPosition());
            telemetry.update();
        }

        if(isStopRequested()) return;

        hardware.drive.followTrajectorySequenceAsync(sequence);

        while(opModeIsActive()) {
            hardware.drive.update();
            hardware.updateLift(liftPos);
        }
    }
}
