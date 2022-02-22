package org.firstinspires.ftc.commoncode.vision

import com.qualcomm.robotcore.hardware.HardwareMap
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation
import org.openftc.easyopencv.OpenCvInternalCamera2


class TeamMarkerAprilTagDetector {

    val pipeline = TeamMarkerAprilTagPipeline()

    val position get() = if(openFailed)
            TeamMarkerPosition.UNKNOWN
        else pipeline.lastPosition

    lateinit var camera: OpenCvCamera
        private set

    private var openFailed = false

    private fun init() {
        camera.openCameraDeviceAsync(object : OpenCvCamera.AsyncCameraOpenListener {
            override fun onOpened() {
                camera.setPipeline(pipeline)
                camera.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
                camera.startStreaming(640, 480, OpenCvCameraRotation.SIDEWAYS_LEFT)
            }

            override fun onError(errorCode: Int) {
                openFailed = true
            }
        })
    }

    fun initInternalCameraVision(
            hardwareMap: HardwareMap,
            cameraDirection: OpenCvInternalCamera2.CameraDirection = OpenCvInternalCamera2.CameraDirection.BACK
    ) {
        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)
        camera = OpenCvCameraFactory.getInstance().createInternalCamera2(cameraDirection, cameraMonitorViewId)

        init()
    }

    fun close() {
        camera.stopStreaming()
    }

}