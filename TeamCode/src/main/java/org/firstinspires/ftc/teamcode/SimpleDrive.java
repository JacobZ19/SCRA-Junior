package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.util.Range;

// This is 22092 cyber eagles work

@TeleOp
public class SimpleDrive extends OpMode {

    double clawPos = ClawHome;
    final double clawSpeed = 0.01;
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor lift = null;
    public Servo Claw = null;
    public float OpenClose = 0f;
    public final static double ClawHome = 0.0;
    public float liftpos;
    public float r = 1;
    public float rot;
    boolean changed = false; //Outside of loop()

    public final static double ClawMinRange = 0.0;
    public final static double ClawMaxRange = 1.0;

    //private double shortpole = null;
    //private double midpole = null;
    @Override
    public void init()
    {
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back");
        Claw = hardwareMap.get(Servo.class, "Claw");
        lift = hardwareMap.get(DcMotor.class,"Lift");
        Claw.setPosition(ClawHome);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        lift.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    @Override
    public void start(){

    }


    @Override
    public void loop() {
        double leftFrontPower;
        double leftBackPower;
        double rightFrontPower;
        double rightBackPower;
        double liftPower;



        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;


        leftFrontPower = Range.clip(drive+ turn + strafe, -0.5, 0.5);
        rightFrontPower = Range.clip(drive - turn - strafe, -0.5, 0.5);
        leftBackPower = Range.clip(drive + turn - strafe, -0.5, 0.5);
        rightBackPower = Range.clip(drive - turn + strafe, -0.5, 0.5);


        telemetry.addData("lift motor pos", lift.getCurrentPosition());
        telemetry.addData("left stick X position", gamepad1.left_stick_x);
        telemetry.addData("left front power", leftFrontPower);
        telemetry.addData("This is before the move loop", "It sets x+y by the movement of the joysticks");


        telemetry.update();
        if(gamepad1.left_stick_y >= 0.1 && gamepad1.right_stick_x <= -0.1){
            rightFrontPower = 0.8;
            rightBackPower = 0.8;
            leftFrontPower = -0.8;
            leftBackPower = -0.8;
        }

        else if(gamepad1.left_stick_y <= -0.1 && gamepad1.right_stick_x <= -0.1){
            leftFrontPower = 0.8;
            leftBackPower = 0.8;
            rightFrontPower = -0.8;
            rightBackPower = -0.8;
        }

        //using for button
        if (gamepad1.right_bumper)
        {
            leftFrontPower /= 3;
            leftBackPower /= 3;
            rightFrontPower /= 3;
            rightBackPower /= 3;
        }

//        if (gamepad2.a)
//        {
//            liftPower = 0.5;
//        }
//
        if (gamepad2.a)
        {
            lift.setTargetPosition(285);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.1);
        }
//
        if (gamepad2.b)
        {
            lift.setTargetPosition(450);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.1);
        }
//
        if (gamepad2.dpad_down)
        {
            lift.setTargetPosition(0);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.2);
        }

        if (gamepad2.dpad_right && liftpos <=450) {
            liftpos = lift.getCurrentPosition();
            liftpos += 5;
            lift.setTargetPosition((int) liftpos);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.1);
        }
        else if (gamepad2.dpad_left && liftpos >=-2) {

            liftpos = lift.getCurrentPosition();
            liftpos -= 5;
            lift.setTargetPosition((int) liftpos);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.1);

        }

        //this is what i changed
        if(gamepad2.left_bumper){
            Claw.setPosition(0);
        }
        else if(gamepad2.right_bumper){
            Claw.setPosition(0.5);
        }
        //else if(!gamepad1.a) changed = false;











        leftFrontDrive.setPower((leftFrontPower));
        rightFrontDrive.setPower((rightFrontPower));
        leftBackDrive.setPower((leftBackPower));
        rightBackDrive.setPower((rightBackPower));

    }

    @Override
    public void stop()
    {
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
        lift.setPower(0);
    }

}