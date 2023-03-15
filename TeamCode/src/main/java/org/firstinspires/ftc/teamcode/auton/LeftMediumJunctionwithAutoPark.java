package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Config
@Autonomous(name = "Left Medium-Junction with AutoPark")
public class LeftMediumJunctionwithAutoPark extends LinearOpMode {
    private DcMotor lift = null;
    AutoDrive robot = new AutoDrive(this);

    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;
    public float liftpos;
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;
    int tag_number;
    double tagsize = 0.166;

    int LEFT = 1;
    int MIDDLE = 2;
    int RIGHT = 3;


    AprilTagDetection tagOfInterest = null;

    @Override
    public void runOpMode() {
        lift = hardwareMap.get(DcMotor.class,"Lift");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.initHardwareMap();
        robot.initDrive();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {}
        });

        telemetry.setMsTransmissionInterval(50);

//        lift = hardwareMap.get(DcMotor.class,"Lift");
//        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//        lift.setDirection(DcMotorSimple.Direction.REVERSE);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d StartPose = new Pose2d(0,0, Math.toRadians(0));
        drive.setPoseEstimate(StartPose);

        TrajectorySequence Coning = drive.trajectorySequenceBuilder(StartPose)
                .addTemporalMarker(() -> {
                    robot.claw(robot.CLAWCLOSE);
                })
                .forward(45,
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL / 2, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL / 2)
                )
                .back(18,
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL / 2, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL / 2))
                .addTemporalMarker(() -> {
                    robot.lift(robot.ARMUP2);
                })
                .waitSeconds(4)
                .addTemporalMarker(() -> {
                    robot.turret(robot.TurretrightMedium);
                })
                .waitSeconds(3)
                .addTemporalMarker(() -> {
                    robot.claw(robot.CLAWOPEN);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    robot.turret(robot.Turretreset);
                })
                .waitSeconds(3)
//                .back(8)
////                .lineToConstantHeading(new Vector2d(49, 0),
////                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL / 2, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
////                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL / 2))
                .addTemporalMarker(() -> {
                    robot.lift(robot.ARMDOWN);
                })
                .waitSeconds(4)
                .forward(24)
                .turn(Math.toRadians(90))
                .build();

        TrajectorySequence Left = drive.trajectorySequenceBuilder(Coning.end())
                .forward(23,
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL / 2, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL / 2))
                .build();

        TrajectorySequence Right = drive.trajectorySequenceBuilder(Coning.end())
                .back(24,
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL / 2, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL / 2))
                .build();


        while (!isStarted() && !isStopRequested())
        {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if(currentDetections.size() != 0)
            {
                boolean tagFound = false;

                for(AprilTagDetection tag : currentDetections)
                {
                    if(tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT)
                    {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if(tagFound)
                {
                    telemetry.addLine("My eyes work\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                }
                else
                {
                    telemetry.addLine("Don't see tag of interest :(");

                    if(tagOfInterest == null)
                    {
                        telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }
            }
            else
            {
                telemetry.addLine("Cant see it");

                if(tagOfInterest == null)
                {
                    telemetry.addLine("Neva seeeeeen");
                }
                else
                {
                    telemetry.addLine("\nTag Seen Before");
                    tagToTelemetry(tagOfInterest);
                }

            }

            telemetry.update();

        }

        if(tagOfInterest != null)
        {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        }
        else
        {
            telemetry.addLine("ERROR IN SEEING");
            telemetry.update();
        }

        telemetry.addLine("Start happened");
        telemetry.update();

        if(tagOfInterest == null || tagOfInterest.id == LEFT){
            drive.followTrajectorySequence(Coning);
            drive.followTrajectorySequence(Left);
        }
        else if(tagOfInterest.id == MIDDLE) {
            telemetry.addLine("Before the movement");
            telemetry.update();
            drive.followTrajectorySequence(Coning);
            telemetry.addLine("After the movement");
            telemetry.update();
        }

        else if(tagOfInterest.id == RIGHT){
            drive.followTrajectorySequence(Coning);
            drive.followTrajectorySequence(Right);
        }

        else{
        }
        while (opModeIsActive()) {;}
    }

    void tagToTelemetry(AprilTagDetection detection)
    {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
    public int gettag_number(){
        return tag_number;
    }

}