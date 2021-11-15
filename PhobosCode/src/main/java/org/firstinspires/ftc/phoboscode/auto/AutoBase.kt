package org.firstinspires.ftc.phoboscode.auto

import org.firstinspires.ftc.commoncode.vision.TeamMarkerAprilTagPipeline
import org.firstinspires.ftc.commoncode.vision.TeamMarkerPosition
import org.firstinspires.ftc.phoboscode.PhobosOpMode
import org.firstinspires.ftc.phoboscode.rr.drive.SampleMecanumDrive
import org.firstinspires.ftc.phoboscode.rr.trajectorysequence.TrajectorySequence
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvInternalCamera2

abstract class AutoBase(val needsVision: Boolean = true) : PhobosOpMode() {

    lateinit var drive: SampleMecanumDrive
        private set

    lateinit var camera: OpenCvCamera
        private set

    open val pipeline = TeamMarkerAprilTagPipeline()

    private var openFailed = false

    override fun setup() {
        drive = SampleMecanumDrive(hardwareMap, hardware.deltaHardware)

        if(needsVision) {
            camera = OpenCvCameraFactory.getInstance().createInternalCamera2(OpenCvInternalCamera2.CameraDirection.BACK)

            camera.openCameraDeviceAsync(object: OpenCvCamera.AsyncCameraOpenListener {
                override fun onOpened() {
                    camera.setPipeline(pipeline)
                    camera.startStreaming(640, 480)
                }

                override fun onError(errorCode: Int) {
                    openFailed = true
                }
            })
        }
    }

    override fun begin() {
        drive.followTrajectorySequenceAsync(
                sequence(
                        if(openFailed) TeamMarkerPosition.RIGHT else pipeline.position
                )
        )
    }

    override fun update() {
        drive.update()
    }

    abstract fun sequence(teamMarkerPosition: TeamMarkerPosition): TrajectorySequence

}