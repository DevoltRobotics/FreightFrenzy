package org.firstinspires.ftc.deimoscode.Autonomo.nacional;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition;
import org.firstinspires.ftc.deimoscode.rr.trajectorysequence.TrajectorySequence;

@Autonomous(name = "WHB", group = "###Autonomus")
public class WHB extends AutonomoBase {

    Pose2d startPóse = new Pose2d(11.5, 60.0, Math.toRadians(180.0));

    @Override
    public void run() {
        hardware.drive.setPoseEstimate(startPóse);

        TrajectorySequence sequence = hardware.drive.trajectorySequenceBuilder(startPóse)
                // ir a poner cubo
                .lineToSplineHeading(new Pose2d(0.0, 32.0, Math.toRadians(119)))
                .UNSTABLE_addDisplacementMarkerOffset(0.0, () -> {
                    hardware.Elevador.setPower(0.8);
                })
                .UNSTABLE_addDisplacementMarkerOffset(2.5, () -> {
                    hardware.Elevador.setPower(0);
                })

                .waitSeconds(2.5)

                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    hardware.Garra.setPosition(1);
                })
                .UNSTABLE_addTemporalMarkerOffset(1.0, () -> {
                    hardware.Garra.setPosition(0);
                })

                .waitSeconds(1.5)

                //Fuera de WareHouse
                .lineToSplineHeading(new Pose2d(7.0, 64.0, Math.toRadians(180.0)))
                //Entrar a Warehouse
                .lineToSplineHeading(new Pose2d(40.0, 64.0, Math.toRadians(180.0)))
                //Estacionarse
                .lineToSplineHeading(new Pose2d(60.0, 39.0, Math.toRadians(90.0)))
                .build();

        waitForStart();

        hardware.drive.followTrajectorySequence(sequence);
    }
}