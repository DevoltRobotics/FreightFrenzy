package org.firstinspires.ftc.commoncode.vision

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.openftc.easyopencv.*


class   TeamMarkerAprilTagDetector {

    val pipeline = TeamMarkerAprilTagPipeline()

    val position get() = if(openFailed)
            TeamMarkerPosition.UNKNOWN
        else pipeline.lastPosition

    lateinit var camera: OpenCvCamera
        private set

    private var openFailed = false

    private fun init(cameraOrientation: OpenCvCameraRotation) {
        camera.openCameraDeviceAsync(object : OpenCvCamera.AsyncCameraOpenListener {
            override fun onOpened() {
                camera.setPipeline(pipeline)
                camera.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
                camera.startStreaming(640, 480, cameraOrientation)
            }

            override fun onError(errorCode: Int) {
                openFailed = true
            }
        })
    }

    fun initInternalCameraVision(
            hardwareMap: HardwareMap,
            cameraDirection: OpenCvInternalCamera2.CameraDirection = OpenCvInternalCamera2.CameraDirection.BACK,
            cameraOrientation: OpenCvCameraRotation = OpenCvCameraRotation.SIDEWAYS_LEFT
    ) {
        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)
        camera = OpenCvCameraFactory.getInstance().createInternalCamera2(cameraDirection, cameraMonitorViewId)

        init(cameraOrientation)
    }


    fun initWebcamVision(
        hardwareMap: HardwareMap,
        name: String,
        cameraOrientation: OpenCvCameraRotation = OpenCvCameraRotation.SIDEWAYS_LEFT
    ) {
        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName::class.java, name), cameraMonitorViewId)

        init(cameraOrientation)
    }

    fun close() {
        camera.stopStreaming()
    }

}