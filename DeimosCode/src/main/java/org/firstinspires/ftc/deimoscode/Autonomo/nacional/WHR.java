package org.firstinspires.ftc.deimoscode.Autonomo.nacional;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition;
import org.firstinspires.ftc.deimoscode.rr.trajectorysequence.TrajectorySequence;

@Autonomous(name = "WHR", group = "###Autonomus")
public class WHR extends AutonomoBase {

    Pose2d startPóse = new Pose2d(11.5, -60.0, Math.toRadians(0.0));

    @Override
    public void run() {
        hardware.drive.setPoseEstimate(startPóse);

        hardware.Absorber.setPosition(0);

        TrajectorySequence sequence = hardware.drive.trajectorySequenceBuilder(startPóse)
                // ir a poner cubo
                .lineToSplineHeading(new Pose2d(0.0, -20.0, Math.toRadians(45)))
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

                //Fuera de WareHouse
                .lineToSplineHeading(new Pose2d(7.0, -64.0, Math.toRadians(0.0)))
                //Entrar a Warehouse
                .lineToSplineHeading(new Pose2d(40.0, -64.0, Math.toRadians(0.0)))
                //Estacionarse
                .lineToSplineHeading(new Pose2d(60.0, -39.0, Math.toRadians(270.0)))
                .build();

        waitForStart();

        hardware.drive.followTrajectorySequence(sequence);
    }
}
