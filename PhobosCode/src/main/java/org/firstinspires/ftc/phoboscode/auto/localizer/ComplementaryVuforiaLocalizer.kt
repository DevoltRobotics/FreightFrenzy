package org.firstinspires.ftc.phoboscode.auto.localizer

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.localization.Localizer
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaFieldNavigationWebcam
import org.firstinspires.ftc.robotcore.external.ClassFactory
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix
import org.firstinspires.ftc.robotcore.external.navigation.*
import org.openftc.easyopencv.OpenCvCameraFactory
import java.util.*


class ComplementaryVuforiaLocalizer(
    val complementaryLocalizer: Localizer,
    val hardwareMap: HardwareMap,
    webcamName: String,
    viewport: Int
) : Localizer {

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private val VUFORIA_KEY = "Aepe6CP/////AAABmRk6EJb+x0+zur2hPUnceDdSDsYFaBpBVT5T/J8YW13b2w4fGcU887Wzc158ndgWH4HMAOl2Htb9kLrNW7MGQynHfJ9X/fQWamy7My1WRnL8gsdf6RLl5zUduyKIWQQBc5m1qkSdRwMim+rei5HigjDMKaeSO3eE33Tsjmob3NiNCdmo6Xl2E+lGTHAImsP7rnMYdpw9hiTv+CLEnTQQ5G/v6or1NDr9y/vlbssk0i4tt4ZS78uxPTGRdf5gJVTvOpACYDjYgQNcEH3ZUL+PKaIOix56i0fpCiigENTxPqSKH46uQciXg591zlYCDkSQSyZq5b8MC5GfTJ5GkzbPG2zLxegPi+Ax7NAOWRQ1pVgX"

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private val mmPerInch = 25.4f
    private val mmTargetHeight = 6 * mmPerInch // the height of the center of the target image above the floor

    private val halfField = 72 * mmPerInch
    private val halfTile = 12 * mmPerInch
    private val oneAndHalfTile = 36 * mmPerInch

    // Class Members
    private lateinit var lastLocation: OpenGLMatrix
    var vuforia: VuforiaLocalizer
        private set

    var targets: VuforiaTrackables
        private set

    private val webcamHardware = hardwareMap.get(WebcamName::class.java, webcamName)

    val parameters = VuforiaLocalizer.Parameters(viewport)

    val allTrackables = ArrayList<VuforiaTrackable>()
    private var targetVisible = false

    override var poseEstimate: Pose2d
        get() {
            return if(!targetVisible) {
                complementaryLocalizer.poseEstimate
            } else {
                // express position (translation) of robot in inches.
                val translation = lastLocation.translation

                Pose2d(
                    (translation[0] / mmPerInch).toDouble(),
                    (translation[1] / mmPerInch).toDouble(),
                    complementaryLocalizer.poseEstimate.heading
                )
            }
        }
        set(value) {
            complementaryLocalizer.poseEstimate = value
        }

    override val poseVelocity: Pose2d? = null

    init {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC screen);
         * If no camera-preview is desired, use the parameter-less constructor instead (commented out below).
         * Note: A preview window is required if you want to view the camera stream on the Driver Station Phone.
         */
        parameters.vuforiaLicenseKey = VUFORIA_KEY

        // We also indicate which camera we wish to use.
        parameters.cameraName = webcamHardware

        // Turn off Extended tracking.  Set this true if you want Vuforia to track beyond the target.
        parameters.useExtendedTracking = false

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters)

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        targets = vuforia.loadTrackablesFromAsset("FreightFrenzy")

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        allTrackables.addAll(targets)

        // Name and locate each trackable object
        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of *transformation matrices.*
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See [Transformation Matrix](https://en.wikipedia.org/wiki/Transformation_matrix)
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the [OpenGLMatrix] class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         * - The X axis runs from your left to the right. (positive from the center to the right)
         * - The Y axis runs from the Red Alliance Station towards the other side of the field
         * where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         * - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         * coordinate system (the center of the field), facing up.
         */

        // Name and locate each trackable object
        identifyTarget(
            0,
            "Blue Storage",
            -halfField,
            oneAndHalfTile,
            mmTargetHeight,
            90f,
            0f,
            90f
        )
        identifyTarget(
            1,
            "Blue Alliance Wall",
            halfTile,
            halfField,
            mmTargetHeight,
            90f,
            0f,
            0f
        )
        identifyTarget(
            2,
            "Red Storage",
            -halfField,
            -oneAndHalfTile,
            mmTargetHeight,
            90f,
            0f,
            90f
        )
        identifyTarget(
            3,
            "Red Alliance Wall",
            halfTile,
            -halfField,
            mmTargetHeight,
            90f,
            0f,
            180f
        )


        /*
         * Create a transformation matrix describing where the camera is on the robot.
         *
         * Info:  The coordinate frame for the robot looks the same as the field.
         * The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
         * Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
         *
         * For a WebCam, the default starting orientation of the camera is looking UP (pointing in the Z direction),
         * with the wide (horizontal) axis of the camera aligned with the X axis, and
         * the Narrow (vertical) axis of the camera aligned with the Y axis
         *
         * But, this example assumes that the camera is actually facing forward out the front of the robot.
         * So, the "default" camera position requires two rotations to get it oriented correctly.
         * 1) First it must be rotated +90 degrees around the X axis to get it horizontal (its now facing out the right side of the robot)
         * 2) Next it must be be rotated +90 degrees (counter-clockwise) around the Z axis to face forward.
         *
         * Finally the camera can be translated to its actual mounting position on the robot.
         *      In this example, it is centered on the robot (left-to-right and front-to-back), and 6 inches above ground level.
         */
        val CAMERA_FORWARD_DISPLACEMENT =
            0.0f * mmPerInch // eg: Enter the forward distance from the center of the robot to the camera lens

        val CAMERA_VERTICAL_DISPLACEMENT =
            8.5f * mmPerInch // eg: Camera is 6 Inches above ground

        val CAMERA_LEFT_DISPLACEMENT =
            9f * mmPerInch // eg: Enter the left distance from the center of the robot to the camera lens

        val cameraLocationOnRobot = OpenGLMatrix
            .translation(
                CAMERA_FORWARD_DISPLACEMENT,
                CAMERA_LEFT_DISPLACEMENT,
                CAMERA_VERTICAL_DISPLACEMENT
            )
            .multiplied(
                Orientation.getRotationMatrix(
                    AxesReference.EXTRINSIC,
                    AxesOrder.XZY,
                    AngleUnit.DEGREES,
                    90f,
                    90f,
                    0f
                )
            )

        /**  Let all the trackable listeners know where the camera is.  */
        for (trackable in allTrackables) {
            (trackable.listener as VuforiaTrackableDefaultListener).setCameraLocationOnRobot(
                parameters.cameraName!!, cameraLocationOnRobot
            )
        }

        targets.activate()
    }

    override fun update() {
        complementaryLocalizer.update()

        // check all the trackable targets to see which one (if any) is visible.
        targetVisible = false

        for (trackable in allTrackables) {
            if ((trackable.listener as VuforiaTrackableDefaultListener).isVisible) {
                targetVisible = true

                // getUpdatedRobotLocation() will return null if no new information is available since
                // the last time that call was made, or if the trackable is not currently visible.
                val robotLocationTransform =
                    (trackable.listener as VuforiaTrackableDefaultListener).updatedRobotLocation
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform
                }
                break
            }
        }

        if(targetVisible) {
            complementaryLocalizer.poseEstimate = complementaryLocalizer.poseEstimate.copy(x = poseEstimate.x)
        }
    }


    /***
     * Identify a target by naming it, and setting its position and orientation on the field
     * @param targetIndex
     * @param targetName
     * @param dx, dy, dz  Target offsets in x,y,z axes
     * @param rx, ry, rz  Target rotations in x,y,z axes
     */
    private fun identifyTarget(
        targetIndex: Int,
        targetName: String?,
        dx: Float,
        dy: Float,
        dz: Float,
        rx: Float,
        ry: Float,
        rz: Float
    ) {
        val aTarget = targets[targetIndex]
        aTarget.name = targetName
        aTarget.location = OpenGLMatrix.translation(dx, dy, dz)
            .multiplied(
                Orientation.getRotationMatrix(
                    AxesReference.EXTRINSIC,
                    AxesOrder.XYZ,
                    AngleUnit.RADIANS,
                    rx,
                    ry,
                    rz
                )
            )
    }

}