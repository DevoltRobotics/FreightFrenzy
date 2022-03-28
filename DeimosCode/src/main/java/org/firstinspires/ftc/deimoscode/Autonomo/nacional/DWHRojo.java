package org.firstinspires.ftc.deimoscode.Autonomo.nacional;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition;
import org.firstinspires.ftc.deimoscode.Hardwareñ;
import org.firstinspires.ftc.deimoscode.rr.trajectorysequence.TrajectorySequence;

@Autonomous(name = "DWHRojo", group = "###Autonomus")
public class DWHRojo extends AutonomoBase {

    Pose2d startPóse = new Pose2d(-35, -60, Math.toRadians(0));

    int liftPos = 0;

    @Override
    public void run() {
        hardware.drive.setPoseEstimate(startPóse);

        hardware.Garra.setPosition(0);

        TrajectorySequence sequence = hardware.drive.trajectorySequenceBuilder(startPóse)
                .UNSTABLE_addDisplacementMarkerOffset(0.0, () -> {
                    if(detector.getPosition() == TeamMarkerPosition.RIGHT) {
                        liftPos = Hardwareñ.HIGH_LIFT_POS;
                    } else if(detector.getPosition() == TeamMarkerPosition.MIDDLE) {
                        liftPos = Hardwareñ.MID_LIFT_POS;
                    } else {
                        liftPos = Hardwareñ.LOW_LIFT_POS;
                    }
                })

                // ir a poner cubo
                .lineToSplineHeading(new Pose2d(-29, -6.8, Math.toRadians(270)))

                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    hardware.Garra.setPosition(0.7);
                })
                .waitSeconds(1.5)

                //Ir a Pato
                .lineToSplineHeading(new Pose2d(-66.5, -60.0, Math.toRadians(330)))

                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    hardware.Garra.setPosition(0);
                    liftPos = Hardwareñ.LOW_LIFT_POS;
                })

                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    hardware.Pato.setPower(-0.6);
                })
                .UNSTABLE_addTemporalMarkerOffset(5, () -> {
                    hardware.Pato.setPower(0);
                })
                .waitSeconds(12)

                // estacionarse
                .lineToSplineHeading(new Pose2d(7.0, -60.0, Math.toRadians(0)))
                .lineToSplineHeading(new Pose2d(46.0, -64.0, Math.toRadians(0)))

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