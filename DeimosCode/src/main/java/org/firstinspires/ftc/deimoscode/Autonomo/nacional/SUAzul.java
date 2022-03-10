package org.firstinspires.ftc.deimoscode.Autonomo.nacional;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition;
import org.firstinspires.ftc.deimoscode.rr.trajectorysequence.TrajectorySequence;

public class SUAzul extends AutonomoBase {

    Pose2d startPóse = new Pose2d(-35, 60, Math.toRadians(180));

    @Override
    public void run() {
        hardware.drive.setPoseEstimate(startPóse);

        TrajectorySequence sequence = hardware.drive.trajectorySequenceBuilder(startPóse)
                // ir a poner cubo
                .lineToSplineHeading(new Pose2d(-23.5, 31.0, Math.toRadians(249.0)))

                .UNSTABLE_addDisplacementMarkerOffset(0.0, () -> {
                    hardware.Elevador.setPower(0.8);
                })
                .UNSTABLE_addDisplacementMarkerOffset(2.5, () -> {
                    hardware.Elevador.setPower(0);
                })

                .waitSeconds(2.5)

                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    hardware.Absorber.setPosition(1);
                })
                .UNSTABLE_addTemporalMarkerOffset(1.0, () -> {
                    hardware.Absorber.setPosition(0);
                })

                .waitSeconds(1.5)

                // ir a hacer patos
                .lineToSplineHeading(new Pose2d(-60.0, 60.0, Math.toRadians(210.0)))

                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    hardware.Pato.setPower(0.9);
                })
                .UNSTABLE_addTemporalMarkerOffset(3.5, () -> {
                    hardware.Pato.setPower(0);
                })
                .waitSeconds(3.5)

                // estacionarse
                .lineToSplineHeading(new Pose2d(-58.0, 35.0, Math.toRadians(180.0)))
                .build();

        waitForStart();

        hardware.drive.followTrajectorySequenceAsync(sequence);
    }

}
