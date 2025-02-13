package org.firstinspires.ftc.teamcode.auton;

// Work by k754a 22092

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.SpinbotTeleOp;

// This is 22092 cyber eagles jade work
// You may only use this code over cyber eagles jade consent

@Autonomous
public class AutoDrive {

    LinearOpMode opMode = null;
    public ElapsedTime runtime = new ElapsedTime();

    public AutoDrive(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    public AutoDrive(SpinbotTeleOp spinbotTeleOp) {
    }

    public final int FORWARD = 1;
    public final int BACKWARD = 2;
    public final int STRAFELEFT = 3;
    public final int STRAFERIGHT = 4;
    public final int RIGHTTURN = 5;
    public final int LEFTTURN = 6;
    public final int ARMUP2 = 7;
    public final int ARMDOWN = 8;
    public final int CLAWCLOSE = 9;
    public final int CLAWOPEN = 10;
    public final int GROUND = 11;
    public final int ARMUP3 = 12;
    public final int TurretrightHigh = 13;
    public final int Turretreset = 14;
    public final int TurretrightMedium = 15;
    public final int TurretleftHigh = 16;
    public final int TurretleftMedium = 17;
    DcMotor lift = null;
    DcMotor turret = null;
    public Servo Claw;
    DcMotor rightFrontDrive;
    DcMotor rightBackDrive;
    DcMotor leftFrontDrive;
    DcMotor leftBackDrive;

    public void initDrive() {
        leftFrontDrive = opMode.hardwareMap.get(DcMotor.class, "left_front");
        rightFrontDrive = opMode.hardwareMap.get(DcMotor.class, "right_front");
        leftBackDrive = opMode.hardwareMap.get(DcMotor.class, "left_back");
        rightBackDrive = opMode.hardwareMap.get(DcMotor.class, "right_back");

        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void initHardwareMap() {
        lift = opMode.hardwareMap.get(DcMotor.class, "Lift");
        turret = opMode.hardwareMap.get(DcMotor.class, "turret");
        Claw = opMode.hardwareMap.get(Servo.class, "Claw");


        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        turret.setDirection(DcMotorSimple.Direction.FORWARD);
//hi

    }

    public void claw(int direction) {
        if (direction == CLAWCLOSE) {
            Claw.setPosition(0.6);

        }

        if (direction == CLAWOPEN) {
            Claw.setPosition(0.0);
        }
    }

    public void lift(int direction) {
        if (direction == ARMUP2) {
            lift.setTargetPosition(625);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.13);

        }
        if (direction == ARMUP3) {
            lift.setTargetPosition(900);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.13);

        }
        if (direction == ARMDOWN) {
            lift.setTargetPosition(0);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.13);
        }
    }

    public void turret(int direction) {
        if (direction ==TurretrightHigh)  {
            turret.setTargetPosition(1205);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(1);
        }
        if (direction ==Turretreset) {
            turret.setTargetPosition(0);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(1);
        }
        if (direction ==TurretrightMedium) {
            turret.setTargetPosition(946);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(1);
        }
        if (direction ==TurretleftHigh)  {
            turret.setTargetPosition(-1500);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(1);
        }
        if (direction ==TurretleftMedium) {
            turret.setTargetPosition(-946);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(1);
        }
    }

        public void drive ( int time, double power, int direction){
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

            if (direction == BACKWARD) {
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

            if (direction == STRAFELEFT) {
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

            if (direction == STRAFERIGHT) {

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
            if (direction == RIGHTTURN) {
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
            if (direction == LEFTTURN) {
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

//        if (direction == ARMUP2)   {
//            lift.setTargetPosition(685);
//            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            lift.setPower(0.13);
//
//        }
//        if (direction == ARMUP3)   {
//            lift.setTargetPosition(900);
//            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            lift.setPower(0.13);
//
//        }
//        if (direction == ARMDOWN)   {
//            lift.setTargetPosition(0);
//            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            lift.setPower(0.13);
//
//        }
//        if (direction ==CLAWCLOSE) {
//            Claw.setPosition(0.6);
//
//        }
//
//        if (direction ==CLAWOPEN) {
//            Claw.setPosition(0.0);
//        }
//        if (direction == GROUND)   {
//            lift.setTargetPosition(100);
//            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            lift.setPower(0.13);
//
//        }
//        if (direction ==Turretright)  {
//            turret.setTargetPosition(1105);
//            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            turret.setPower(1);
//        }
//        if (direction ==Turretreset) {
//            turret.setTargetPosition(0);
//            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            turret.setPower(1);
//        }

        }

    }
