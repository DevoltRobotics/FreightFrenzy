package org.firstinspires.ftc.deimoscode.Autonomo.nacional;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition;
import org.firstinspires.ftc.deimoscode.Hardwareñ;
import org.firstinspires.ftc.deimoscode.rr.trajectorysequence.TrajectorySequence;

@Autonomous(name = "SUAzul", group = "###Autonomus")
public class SUAzul extends AutonomoBase {

    Pose2d startPóse = new Pose2d(-35, 60, Math.toRadians(180));

    int liftPos = 0;

    @Override
    public void run() {
        hardware.drive.setPoseEstimate(startPóse);

        TrajectorySequence sequence = hardware.drive.trajectorySequenceBuilder(startPóse)
                .UNSTABLE_addDisplacementMarkerOffset(0.0, () -> {
                    liftPos = Hardwareñ.HIGH_LIFT_POS;
                })

                // ir a poner cubo
                .lineToSplineHeading(new Pose2d(-23.5, 31.0, Math.toRadians(249.0)))

                .waitSeconds(1.0)

                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    hardware.Absorber.setPosition(1);
                })
                .UNSTABLE_addTemporalMarkerOffset(1.0, () -> {
                    hardware.Absorber.setPosition(0);
                    liftPos = Hardwareñ.LOW_LIFT_POS;
                })

                .waitSeconds(1.5)

                // ir a hacer patos
                .lineToSplineHeading(new Pose2d(-60.0, 60.0, Math.toRadians(210.0)))

                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    hardware.Pato.setPower(-0.7);
                })
                .UNSTABLE_addTemporalMarkerOffset(5, () -> {
                    hardware.Pato.setPower(0);
                })
                .waitSeconds(5)

                // estacionarse
                .lineToSplineHeading(new Pose2d(-58.0, 35.0, Math.toRadians(0.0)))
                .build();

        waitForStart();

        hardware.drive.followTrajectorySequenceAsync(sequence);

        while(opModeIsActive()) {
            hardware.drive.update();
            hardware.updateLift(liftPos);
        }
    }

}
