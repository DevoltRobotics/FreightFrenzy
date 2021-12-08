package org.firstinspires.ftc.phoboscode.auto

import org.firstinspires.ftc.commoncode.vision.TeamMarkerAprilTagDetector
import org.firstinspires.ftc.commoncode.vision.TeamMarkerAprilTagPipeline
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition
import org.firstinspires.ftc.phoboscode.PhobosOpMode
import org.firstinspires.ftc.phoboscode.rr.drive.SampleMecanumDrive
import org.firstinspires.ftc.phoboscode.rr.trajectorysequence.TrajectorySequence
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvInternalCamera2

abstract class AutonomoBase(
        val needsRR: Boolean = true,
        val needsVision: Boolean = true,
) : PhobosOpMode() {

    lateinit var drive: SampleMecanumDrive
        private set

    val vision = TeamMarkerAprilTagDetector()

    private var openFailed = false

    override fun setup() {
        if(needsRR) {
            drive = SampleMecanumDrive(hardwareMap, hardware.deltaHardware)
        }

        if(needsVision) {
            vision.initInternalCameraVision(hardwareMap)
        }
    }

    override fun begin() {
        if(needsRR) {
            drive.followTrajectorySequenceAsync(
                    sequence(
                            if (openFailed) TeamMarkerPosition.UNKNOWN else vision.position
                    )
            )
        }

        vision.close()
    }

    override fun update() {
        if(needsRR) {
            drive.update()
        }
    }

    abstract fun sequence(teamMarkerPosition: TeamMarkerPosition): TrajectorySequence?

}