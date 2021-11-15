package org.firstinspires.ftc.commoncode.vision;

//import com.acmerobotics.dashboard.config.Config;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

//@Config
public class TeamMarkerAprilTagPipeline extends AprilTagDetectionPipeline {

    public static double LEFT_LINE_PERC = 0.32;
    public static double RIGHT_LINE_PERC = 0.68;

    public static Scalar LINE_COLOR = new Scalar(100, 0, 255);

    private final Rect leftRectangle = new Rect();
    private final Rect rightRectangle = new Rect();

    private TeamMarkerPosition position = TeamMarkerPosition.UNKNOWN;
    private final Object positionLock = new Object();

    @Override
    public Mat processFrame(Mat input) {
        Mat output = super.processFrame(input);

        double leftLineX = output.cols() * LEFT_LINE_PERC;
        double rightLineX = output.cols() * RIGHT_LINE_PERC;

        leftRectangle.x = (int) Math.round(leftLineX);
        leftRectangle.width = 2;
        leftRectangle.height = output.rows();

        rightRectangle.x = (int) Math.round(rightLineX);
        rightRectangle.width = 2;
        rightRectangle.height = output.rows();

        Imgproc.rectangle(output, leftRectangle, LINE_COLOR);
        Imgproc.rectangle(output, rightRectangle, LINE_COLOR);

        synchronized (positionLock) {

        }

        return output;
    }

    public TeamMarkerPosition getLastPosition() {
        synchronized(positionLock) {
            return position;
        }
    }

}
