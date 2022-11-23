package org.firstinspires.ftc.teamcode.auton;

// Work by Kallen 22092

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

// This is 22092 cyber eagles jade work
// You may only use this code over cyber eagles jade consent

@Autonomous
public class AutoDrive {

    LinearOpMode opMode = null;
    public ElapsedTime  runtime = new ElapsedTime();
    public AutoDrive(LinearOpMode opMode){
        this.opMode= opMode;
    }
    public final int FORWARD = 1;
    public final int BACKWARD = 2;
    public final int STRAFELEFT = 3;
    public final int STRAFERIGHT = 4;
    DcMotor rightFrontDrive,rightBackDrive, leftFrontDrive, leftBackDrive;

    public void initHardwareMap()
    {
        leftFrontDrive = opMode.hardwareMap.get(DcMotor.class, "left_front");
        rightFrontDrive = opMode.hardwareMap.get(DcMotor.class, "right_front");
        leftBackDrive = opMode.hardwareMap.get(DcMotor.class, "left_back");
        rightBackDrive = opMode.hardwareMap.get(DcMotor.class, "right_back");

        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);
    }



    public void drive(int time,double power,int direction){
        if (direction == FORWARD) {
            leftFrontDrive.setPower(power);
            leftBackDrive.setPower(power);
            rightFrontDrive.setPower(power);
            rightBackDrive.setPower(power);
            opMode.sleep(time);
            leftFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
            rightBackDrive.setPower(0);
        }

        else if (direction == BACKWARD) {
            leftFrontDrive.setPower(power * -1);
            leftBackDrive.setPower(power * -1);
            rightFrontDrive.setPower(power * -1);
            rightBackDrive.setPower(power * -1);
            opMode.sleep(time);
            leftFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
            rightBackDrive.setPower(0);
        }

        else if (direction == STRAFELEFT) {
            leftFrontDrive.setPower(power * -1);
            leftBackDrive.setPower(power * 1);
            rightFrontDrive.setPower(power * 1);
            rightBackDrive.setPower(power * -1);
            opMode.sleep(time);
            leftFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
            rightBackDrive.setPower(0);
        }

        else if (direction == STRAFERIGHT) {

            leftFrontDrive.setPower(power * 1);
            leftBackDrive.setPower(power * -1);
            rightFrontDrive.setPower(power * -1);
            rightBackDrive.setPower(power * 1);
            opMode.sleep(time);
            leftFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
            rightBackDrive.setPower(0);
        }
    }

}
