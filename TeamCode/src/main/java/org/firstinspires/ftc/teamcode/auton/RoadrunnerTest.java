package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.auton.AutoDrive;

@Config
@Autonomous(name = "ROADRUNNER TEST")
public class RoadrunnerTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        AutoDrive robot = new AutoDrive(this);

        Trajectory TEST1 = drive.trajectoryBuilder(new Pose2d())
                .forward(38,
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL / 4, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL / 2)
                )
                .build();

        Trajectory TEST2 = drive.trajectoryBuilder(TEST1.end())
                .back(12,
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL / 4, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL / 2)
                )
                .build();

        Trajectory TEST3 = drive.trajectoryBuilder(TEST2.end())
                .strafeLeft(24,
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL / 4, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL / 2)
                )
                .build();

        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectory(TEST1);
        drive.followTrajectory(TEST2);
        drive.followTrajectory(TEST3);
    }
}
