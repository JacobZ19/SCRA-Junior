package org.firstinspires.ftc.robotcontroller.external.samples;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.HardwareMap;




public class TestClass extends OpMode {
    //CreateMoters

    public DcMotor frontRightMoter = null;
    public DcMotor frontLeftMoter = null;
    public DcMotor backRightMoter = null;
    public DcMotor backLeftMoter = null;


    //Using servos 3-5




    //Servos


    //vars
    HardwareMap hardwareMap = null;



    public void init()
    {
        frontRightMoter = hardwareMap.get(DcMotor.class,  "FrontRightMoter");
        frontLeftMoter = hardwareMap.get(DcMotor.class,  "LeftRightMoter");
        backRightMoter = hardwareMap.get(DcMotor.class,  "BackRightMoter");
        backLeftMoter = hardwareMap.get(DcMotor.class,  "BackRightMoter");


        //moterDirection

        frontRightMoter.setDirection(DcMotor.Direction.REVERSE);

        backRightMoter.setDirection(DcMotor.Direction.REVERSE);
    }






    public void loop()
        {
            frontRightMoter.setPower(-gamepad1.right_stick_y);
            frontLeftMoter.setPower(-gamepad1.left_stick_y);
            backRightMoter.setPower(-gamepad1.right_stick_y);
            backLeftMoter.setPower(-gamepad1.left_stick_y);

        }


    }




