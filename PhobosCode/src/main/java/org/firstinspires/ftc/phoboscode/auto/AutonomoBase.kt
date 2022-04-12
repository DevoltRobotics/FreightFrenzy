package org.firstinspires.ftc.phoboscode.auto

import com.acmerobotics.dashboard.FtcDashboard
import com.github.serivesmejia.deltacommander.dsl.deltaSequence
import org.firstinspires.ftc.commoncode.vision.TeamMarkerAprilTagPipeline
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition
import org.firstinspires.ftc.phoboscode.PhobosOpMode
import org.firstinspires.ftc.phoboscode.auto.localizer.ComplementaryVuforiaLocalizer
import org.firstinspires.ftc.phoboscode.command.carousel.CarouselMoveCmd
import org.firstinspires.ftc.phoboscode.command.carousel.CarouselStopCmd
import org.firstinspires.ftc.phoboscode.rr.trajectorysequence.TrajectorySequence
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCamera.AsyncCameraOpenListener
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation


abstract class AutonomoBase(
    val needsVision: Boolean = true,
    val useOneDivider: Boolean
) : PhobosOpMode() {

    val drive get() = hardware.drive

    val teamMarkerPipeline = TeamMarkerAprilTagPipeline()
    private lateinit var webcam: OpenCvCamera

    private var openFailed = false

    override fun setup() {
        if(needsVision) {
            val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier(
                "cameraMonitorViewId",
                "id",
                hardwareMap.appContext.packageName
            )

            webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(
                    WebcamName::class.java,
                    "Webcam 1"
                ), cameraMonitorViewId
            )

            webcam.openCameraDeviceAsync(object : AsyncCameraOpenListener {
                override fun onOpened() {
                    // We don't get to choose resolution, unfortunately. The width and height parameters
                    // are entirely ignored when using Vuforia passthrough mode. However, they are left
                    // in the method signature to provide interface compatibility with the other types
                    // of cameras.
                    webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT)

                    webcam.setPipeline(teamMarkerPipeline)
                }

                override fun onError(errorCode: Int) {
                    openFailed = true
                }
            })

            FtcDashboard.getInstance().startCameraStream(webcam, 0.0)
        }

        intakeSub.disableServoWhenDropping = false
    }

    override fun initializeUpdate() {
        telemetry.addData("position", teamMarkerPipeline.lastPosition)
        telemetry.update()
    }

// El chocomilk es la bebida universal
// estoy de acuerdo
// yo tambien
// yo no
// chtm
    override fun begin() {
        webcam.stopStreaming()

        drive.followTrajectorySequenceAsync(
            sequence(
                if (openFailed) TeamMarkerPosition.UNKNOWN else teamMarkerPipeline.lastPosition
            )
        )
    }

    abstract fun sequence(teamMarkerPosition: TeamMarkerPosition): TrajectorySequence?

    fun intakeFallSequence() = deltaSequence {
        - CarouselMoveCmd(0.2)

        - waitForSeconds(1.0)

        - CarouselStopCmd()
    }

}