package org.firstinspires.ftc.teamcode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class AutoDrive {

    LinearOpMode opmode = null;
    public final int FORWARD = 1;
    public final int BACKWARD = 2;
    public final int STRAFELEFT = 3;
    public final int STRAFERIGHT = 4;
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;

    public void initHardwareMap()
    {
        leftFrontDrive = opmode.hardwareMap.get(DcMotor.class, "left_front");
        rightFrontDrive = opmode.hardwareMap.get(DcMotor.class, "right_front");
        leftBackDrive = opmode.hardwareMap.get(DcMotor.class, "left_back");
        rightBackDrive = opmode.hardwareMap.get(DcMotor.class, "right_back");

        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



    }



    public void drive(int time,double power,int direction){
        if (direction == FORWARD) {
            leftFrontDrive.setPower(power);
            leftBackDrive.setPower(power);
            rightFrontDrive.setPower(power);
            rightBackDrive.setPower(power);
            opmode.sleep(time);
        }

        else if (direction == BACKWARD) {
            leftFrontDrive.setPower(power * -1);
            leftBackDrive.setPower(power * -1);
            rightFrontDrive.setPower(power * -1);
            rightBackDrive.setPower(power * -1);
            opmode.sleep(time);
        }

        else if (direction == STRAFELEFT) {
            leftFrontDrive.setPower(power * 1);
            leftBackDrive.setPower(power * -1);
            rightFrontDrive.setPower(power * -1);
            rightBackDrive.setPower(power * 1);
            opmode.sleep(time);
        }

        else if (direction == STRAFERIGHT) {
            leftFrontDrive.setPower(power * -1);
            leftBackDrive.setPower(power * 1);
            rightFrontDrive.setPower(power * 1);
            rightBackDrive.setPower(power * -1);
            opmode.sleep(time);
        }
    }



}
