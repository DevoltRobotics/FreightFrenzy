package org.firstinspires.ftc.deimoscode.Autonomo.nacional;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition;
import org.firstinspires.ftc.deimoscode.Hardwareñ;
import org.firstinspires.ftc.deimoscode.rr.trajectorysequence.TrajectorySequence;

@Autonomous(name = "WHB", group = "###Autonomus")
public class WHB extends AutonomoBase {

    Pose2d startPóse = new Pose2d(11.5, 60, Math.toRadians(180));

    int liftPos = 0;

    @Override
    public void run() {
        hardware.drive.setPoseEstimate(startPóse);

        hardware.Garra.setPosition(0);

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
                .lineToSplineHeading(new Pose2d(2.2, 9.0, Math.toRadians(90)))
                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    hardware.Garra.setPosition(0.5);
                })


                .waitSeconds(1.5)

                //Fuera de WareHouse
                .lineToSplineHeading(new Pose2d(16.0, 64.0, Math.toRadians(180.0)))
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    hardware.Garra.setPosition(0);
                    liftPos = Hardwareñ.LOW_LIFT_POS;
                })
                //Entrar a Warehouse
                .lineToSplineHeading(new Pose2d(40.0, 64.0, Math.toRadians(180.0)))
                //Estacionarse
                .lineToSplineHeading(new Pose2d(70.0, 30.0, Math.toRadians(90.0)))
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