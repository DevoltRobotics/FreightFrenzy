package org.firstinspires.ftc.phoboscode.auto

import com.acmerobotics.dashboard.FtcDashboard
import org.firstinspires.ftc.commoncode.vision.TeamMarkerAprilTagDetector
import org.firstinspires.ftc.commoncode.vision.TeamMarkerAprilTagPipeline
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition
import org.firstinspires.ftc.phoboscode.PhobosOpMode
import org.firstinspires.ftc.phoboscode.rr.drive.SampleMecanumDrive
import org.firstinspires.ftc.phoboscode.rr.trajectorysequence.TrajectorySequence
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation
import org.openftc.easyopencv.OpenCvInternalCamera2

abstract class AutonomoBase(
        val needsVision: Boolean = true,
        val useOneDivider: Boolean
) : PhobosOpMode() {

    val drive get() = hardware.drive

    val vision = TeamMarkerAprilTagDetector(useOneDivider)

    private var openFailed = false

    override fun setup() {
        if(needsVision) {
            vision.initWebcamVision(hardwareMap, "Webcam 1", OpenCvCameraRotation.UPRIGHT)
            TeamMarkerAprilTagPipeline.LEFT_LINE_PERC = 0.385;

            FtcDashboard.getInstance().startCameraStream(vision.camera, 0.0)
        }
    }

    override fun initializeUpdate() {
        telemetry.addData("position", vision.position)
        telemetry.update()
    }

// El chocomilk es la bebida universal
// estoy de acuerdo
// yo tambien
// yo no
// chtm
    override fun begin() {
        drive.followTrajectorySequenceAsync(
                sequence(
                        if (openFailed) TeamMarkerPosition.UNKNOWN else vision.position
                )
        )

        if(needsVision) {
            vision.close()
        }
    }

    abstract fun sequence(teamMarkerPosition: TeamMarkerPosition): TrajectorySequence?

}