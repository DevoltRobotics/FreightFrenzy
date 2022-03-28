package org.firstinspires.ftc.commoncode.vision;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.List;

@Config
public class TeamMarkerAprilTagPipeline extends AprilTagDetectionPipeline {

    public static double LEFT_LINE_PERC = 0.32;
    public static double RIGHT_LINE_PERC = 0.68;
    public static int APRILTAG_ID = 8;

    final float DECIMATION_HIGH = 3;
    final float DECIMATION_LOW = 1;
    final float THRESHOLD_HIGH_DECIMATION_RANGE_METERS = 1.0f;
    final int THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION = 4;

    public static Scalar LINE_COLOR = new Scalar(100, 0, 255);
    public static Scalar TEXT_COLOR = new Scalar(0, 100, 255);
    public static Scalar TEXT_SHADE_COLOR = new Scalar(0, 0, 255);

    private final Rect leftRectangle = new Rect();
    private final Rect rightRectangle = new Rect();

    private TeamMarkerPosition position = TeamMarkerPosition.UNKNOWN;
    private final Object positionLock = new Object();

    int numFramesWithoutDetection = 0;

    boolean hasDetected = false;

    Telemetry telemetry;
    boolean useOneDivider = false;

    boolean defaultToRight = false;

    public TeamMarkerAprilTagPipeline(Telemetry telemetry, boolean useOneDivider) {
        this.telemetry = telemetry;
        this.useOneDivider = useOneDivider;
    }

    public TeamMarkerAprilTagPipeline(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public TeamMarkerAprilTagPipeline(boolean useOneDivider) {
        this(null, useOneDivider);
    }

    public TeamMarkerAprilTagPipeline(boolean useOneDivider, boolean defaultToRight) {
        this(null, useOneDivider);
        this.defaultToRight = defaultToRight;
    }

    public TeamMarkerAprilTagPipeline() {
        this(null);
    }

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
        if(!useOneDivider) {
            Imgproc.rectangle(output, rightRectangle, LINE_COLOR);
        }

        synchronized (positionLock) {
            List<AprilTagDetection> detections = getDetectionsUpdate();
            if(detections != null) {
                // If we don't see any tags
                if(detections.size() == 0)
                {
                    numFramesWithoutDetection++;

                    // If we haven't seen a tag for a few frames, lower the decimation
                    // so we can hopefully pick one up if we're e.g. far back
                    if(numFramesWithoutDetection >= THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION) {
                        setDecimation(DECIMATION_LOW);
                    }

                    if(!hasDetected && position == TeamMarkerPosition.UNKNOWN) {
                        position = TeamMarkerPosition.RIGHT;
                    }
                } else {
                    numFramesWithoutDetection = 0;

                    for (AprilTagDetection detection : detections) {
                        // If the target is within 1 meter, turn on high decimation to
                        // increase the frame rate
                        if(detection.pose.z < THRESHOLD_HIGH_DECIMATION_RANGE_METERS) {
                            setDecimation(DECIMATION_HIGH);
                        }

                        Point corner = detection.corners[0];
                        Point textPos = new Point(corner.x, corner.y + 25);

                        String text = "id=" + detection.id;
                        if (detection.id != APRILTAG_ID) {
                            text += " (expecting " + APRILTAG_ID + ")";
                        }

                        Imgproc.putText(output, text,
                                textPos,
                                Imgproc.FONT_HERSHEY_PLAIN, 1.8, TEXT_SHADE_COLOR, 5);
                        Imgproc.putText(output, text,
                                textPos,
                                Imgproc.FONT_HERSHEY_PLAIN, 1.8, TEXT_COLOR, 2);

                        if (detection.id == APRILTAG_ID) {
                            Point p = detection.center;

                            if(!useOneDivider) {
                                if (p.x < leftLineX && p.x < rightLineX) {
                                    position = TeamMarkerPosition.LEFT;
                                } else if (p.x > leftLineX && p.x < rightLineX) {
                                    position = TeamMarkerPosition.MIDDLE;
                                } else {
                                    position = TeamMarkerPosition.RIGHT;
                                }
                            } else {
                                if (p.x < leftLineX) {
                                    position = TeamMarkerPosition.MIDDLE;
                                } else if (p.x > leftLineX) {
                                    position = TeamMarkerPosition.RIGHT;
                                } else {
                                    position = TeamMarkerPosition.LEFT;
                                }
                            }

                            corner = detection.corners[3];
                            textPos.x = corner.x;
                            textPos.y = corner.y - 25;

                            Imgproc.putText(output, position.name(),
                                    textPos,
                                    Imgproc.FONT_HERSHEY_PLAIN, 1.8, TEXT_SHADE_COLOR, 5);
                            Imgproc.putText(output, position.name(),
                                    textPos,
                                    Imgproc.FONT_HERSHEY_PLAIN, 1.8, TEXT_COLOR, 2);

                            break;
                        }
                    }
                }
            }
        }

        if(telemetry != null) {
            telemetry.addData("Position", position);
            telemetry.update();
        }

        if(position != TeamMarkerPosition.UNKNOWN) {
            hasDetected = true;
        }

        return output;
    }

    public TeamMarkerPosition getLastPosition() {
        synchronized(positionLock) {
            return position;
        }
    }

}
