package org.firstinspires.ftc.teamcode.auton;

// Work by k754a 22092

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
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
    public final int RIGHTTURN = 5;
    public final int LEFTTURN =6;
    public final int ARMUP2 = 7;
    public final int ARMDOWN = 8;
    public final int CLAWCLOSE = 9;
    public final int CLAWOPEN = 10;
    public final int GROUND = 11;
    DcMotor lift = null;
    public Servo Claw;
    DcMotor rightFrontDrive;
    DcMotor rightBackDrive;
    DcMotor leftFrontDrive;
    DcMotor leftBackDrive;

    public void initHardwareMap()
    {
        leftFrontDrive = opMode.hardwareMap.get(DcMotor.class, "left_front");
        rightFrontDrive = opMode.hardwareMap.get(DcMotor.class, "right_front");
        leftBackDrive = opMode.hardwareMap.get(DcMotor.class, "left_back");
        rightBackDrive = opMode.hardwareMap.get(DcMotor.class, "right_back");
        lift = opMode.hardwareMap.get(DcMotor.class,"Lift");
        Claw = opMode.hardwareMap.get(Servo.class, "Claw");

        Claw.setPosition(0.2);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
//hi

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
        else if (direction == RIGHTTURN) {
            leftFrontDrive.setPower(power * 1);
            leftBackDrive.setPower(power * 1);
            rightFrontDrive.setPower(power * -1);
            rightBackDrive.setPower(power * -1);
            opMode.sleep(time);
            leftFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
            rightBackDrive.setPower(0);
        }
        else if (direction == LEFTTURN)   {
            leftFrontDrive.setPower(power * -1);
            leftBackDrive.setPower(power * -1);
            rightFrontDrive.setPower(power * 1);
            rightBackDrive.setPower(power * 1);
            opMode.sleep(time);
            leftFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
            rightBackDrive.setPower(0);
        }

        else if (direction == ARMUP2)   {
            lift.setTargetPosition(685);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.13);

        }
        else if (direction == ARMDOWN)   {
            lift.setTargetPosition(0);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.13);

        }
        else if (direction ==CLAWCLOSE) {
            Claw.setPosition(1);

        }

        else if (direction ==CLAWOPEN) {
            Claw.setPosition(0.2);

        }
        else if (direction == GROUND)   {
            lift.setTargetPosition(100);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.13);

        }

    }

}

