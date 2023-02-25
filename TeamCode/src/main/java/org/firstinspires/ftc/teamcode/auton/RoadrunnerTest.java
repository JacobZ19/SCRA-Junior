package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.auton.AutoDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Config
@Autonomous(name = "ROADRUNNER TEST")
public class RoadrunnerTest extends LinearOpMode {



    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        AutoDrive robot = new AutoDrive(this);

        robot.initHardwareMap();

        TrajectorySequence TEST1 = drive.trajectorySequenceBuilder(new Pose2d())
                .addDisplacementMarker(() -> {
                        robot.drive(0, 0, robot.CLAWCLOSE);
                })
                .forward(56,
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL / 4, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL / 2)
                )
//                .addDisplacementMarker(() -> {
//                    robot.drive(0, 0, robot.ARMUP3);
//                })
//                .waitSeconds(5)
//                .addDisplacementMarker(() -> {
//                    robot.drive(0, 0, robot.Turretright);
//                })
//                .waitSeconds(4)
//                .addDisplacementMarker(() -> {
//                    robot.drive(0, 0, robot.CLAWOPEN);
//                })
//                .waitSeconds(1)
//                .addDisplacementMarker(() -> {
//                    robot.drive(0,0, robot.Turretreset);
//                })
//                .waitSeconds(4)
//                .addDisplacementMarker(() -> {
//                    robot.drive(0, 0, robot.ARMDOWN);
//                })
//                .waitSeconds(5)
//                .splineToConstantHeading(new Vector2d(26, 0), 90)
                .build();

        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectorySequence(TEST1);
    }
}
//dsjklds