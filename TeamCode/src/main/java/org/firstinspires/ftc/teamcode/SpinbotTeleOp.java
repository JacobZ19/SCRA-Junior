package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.auton.AutoDrive;

@TeleOp

public class SpinbotTeleOp extends OpMode {

    Gamepad.RumbleEffect Endgame;
    Gamepad.RumbleEffect Park;

    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor turret = null;
    private DcMotor lift = null;
    public Servo Claw = null;
    public float liftpos;
    public float turretpos;
    public float time_elapsed = 0;
    public boolean Endgametrue = false;
    public boolean Parktrue = false;
    final double Endgamefloat = 80.0;
    final double EndOfGamefloat = 115.0;

    ElapsedTime runtime = new ElapsedTime();

    static void sleep(int LongMilliseconds) {
        try {
            Thread.sleep(LongMilliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void init()
    {
        Endgame = new Gamepad.RumbleEffect.Builder()
                .addStep(1.0, 1.0, 1000)  //  Rumble right motor 100% for 500 mSec
                .addStep(0.0, 0.0, 500)  //  Rumble right motor 100% for 500 mSec
                .addStep(1.0, 1.0, 1000)  //  Rumble right motor 100% for 500 mSec
                .addStep(0.0, 0.0, 500)  //  Rumble right motor 100% for 500 mSec
                .addStep(1.0, 1.0, 1000)  //  Rumble right motor 100% for 500 mSec
                .build();
        Park = new Gamepad.RumbleEffect.Builder()
                .addStep(0.5, 0.5, 500)  //  Rumble right motor 100% for 500 mSec
                .addStep(0.5, 0.5, 200)  //  Rumble right motor 100% for 500 mSec
                .addStep(0.5, 0.5, 500)  //  Rumble right motor 100% for 500 mSec
                .build();

        //tells you to press start
        telemetry.addLine(">>>>>> PRESS START BUTTON");
        telemetry.update();

        //finds the DCMOTORS
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back");
        turret = hardwareMap.get(DcMotor.class, "turret");
        Claw = hardwareMap.get(Servo.class, "Claw");
        lift = hardwareMap.get(DcMotor.class,"Lift");

        //resets and sets lift and drive motions
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        lift.setDirection(DcMotorSimple.Direction.REVERSE);

        turret.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void start() {
        time_elapsed = 0;
        telemetry.clearAll();
        Endgametrue = false;
        Parktrue = false;
        runtime.reset();
    }



    @Override
    public void loop() {
        Rumble();
        //sets most drive things
        double leftFrontPower;
        double leftBackPower;
        double rightFrontPower;
        double rightBackPower;


        //set drive controls
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        //set moter power
        leftFrontPower = Range.clip(drive+ turn + strafe, -0.65, 0.65);
        rightFrontPower = Range.clip(drive - turn - strafe, -0.65, 0.65);
        leftBackPower = Range.clip(drive + turn - strafe, -0.65, 0.65);
        rightBackPower = Range.clip(drive - turn + strafe, -0.65, 0.65);


        //telemetry define
        telemetry.addData("Lift Encoder Ticks: ", lift.getCurrentPosition());
        telemetry.addData("Turret Encoder Ticks", turret.getCurrentPosition());
        telemetry.addData("Speed: ", (leftFrontPower + leftBackPower + rightBackPower + rightFrontPower) / 4);
        telemetry.addData("Left Stick X: ", gamepad1.left_stick_x);
        telemetry.addData("Left Stick Y: ", -gamepad1.left_stick_y);
        telemetry.addData("Right Stick X: ", gamepad1.right_stick_x);
        telemetry.addData("Right Stick Y: ", -gamepad1.right_stick_y);



        //telemetry update
        telemetry.update();
        //endgame timer
        turretpos = turret.getCurrentPosition();
        liftpos = lift.getCurrentPosition();



        if(gamepad1.b){
            Claw.setPosition(0.35);
            lift.setTargetPosition(625);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.1);
            leftFrontPower = -0.16;
            leftBackPower = -0.16;
            rightFrontPower = -0.16;
            rightBackPower = -0.16;
        }
        if(gamepad1.y){
            Claw.setPosition(0.35);
            lift.setTargetPosition(880);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.1);
            leftFrontPower = -0.16;
            leftBackPower = -0.16;
            rightFrontPower = -0.16;
            rightBackPower = -0.16;
        }
        if(gamepad1.x){
            Claw.setPosition(0.35);
            lift.setTargetPosition(400);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.1);
            leftFrontPower = -0.16;
            leftBackPower = -0.16;
            rightFrontPower = -0.16;
            rightBackPower = -0.16;
        }

        if(gamepad2.left_stick_button && liftpos >= 150 && turretpos >= -4000){

            turretpos = turret.getCurrentPosition();
            turretpos -= 100;
            turret.setTargetPosition((int) turretpos);
            turret.setPower(1);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        else if(gamepad2.right_stick_button && liftpos >= 150 && turretpos <= 4000){
            turretpos = turret.getCurrentPosition();
            turretpos += 100;
            turret.setTargetPosition((int) turretpos);
            turret.setPower(1);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        //incraments
        if (gamepad2.dpad_right && liftpos <= 1070) {
            liftpos = lift.getCurrentPosition();
            liftpos += 45;
            lift.setTargetPosition((int) liftpos);

            lift.setPower(0.1);

            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sleep(400);
        }

        //custom down arm
        else if (gamepad2.dpad_left && liftpos > 0) {

            liftpos = lift.getCurrentPosition();
            liftpos -= 45;
            lift.setTargetPosition((int) liftpos);

            lift.setPower(0.1);

            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sleep(400);
        }

        //slow movement
        if (gamepad1.right_bumper)
        {
            leftFrontPower /= 4;
            leftBackPower /= 4;
            rightFrontPower /= 4;
            rightBackPower /= 4;
        }

        //slowest movement
        if (gamepad1.left_bumper)
        {
            leftFrontPower *= 1.25;
            leftBackPower *= 1.25;
            rightFrontPower *= 1.25;
            rightBackPower *= 1.25
            ;
        }


        //this is med pole
        if (gamepad2.a)
        {
            lift.setTargetPosition(400);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.25);
        }

        if (gamepad1.a)
        {
            turret.setTargetPosition(0);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(1);
            lift.setTargetPosition(230);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.25);
        }


        //this is low pole
        if (gamepad2.b)
        {
            lift.setTargetPosition(625);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.25);
        }

        if (gamepad2.y)
        {
            lift.setTargetPosition(880);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.25);
        }


        //this is ground junction
        if (gamepad2.x)
        {
            lift.setTargetPosition(75);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.25);
            turret.setTargetPosition(0);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(1);
        }

        //sets arm too 0.12511238294583
        if (gamepad2.dpad_down)
        {
            turret.setTargetPosition(0);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(1);
            if (turretpos >= -10 && turretpos <= 10) {
                lift.setTargetPosition(0);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setPower(-0.3);
            }
        }

        if (gamepad2.touchpad)
        {
            turret.setTargetPosition(0);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(1);
        }
        if (gamepad2.left_trigger >= 0.8 && liftpos >= 100)
        {
            turret.setTargetPosition(-1500);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(1);
        }
        else if (gamepad2.right_trigger >= 0.8 && liftpos >= 100)
        {
            turret.setTargetPosition(1500);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(1);
        }

        //Claw Close:
        if(gamepad2.left_bumper){
            Claw.setPosition(0.0);
        }
        //Claw Open:
        else if(gamepad2.right_bumper){
            Claw.setPosition(0.6);
        }

        //Set drive power:
        leftFrontDrive.setPower((leftFrontPower));
        rightFrontDrive.setPower((rightFrontPower));
        leftBackDrive.setPower((leftBackPower));
        rightBackDrive.setPower((rightBackPower));
    }

    public void Rumble()
    {
        if ((runtime.seconds() > Endgamefloat) && !Endgametrue)  {
            gamepad1.runRumbleEffect(Endgame);
            Endgametrue =true;
        }

        // Display the time remaining while we are still counting down.
        if (!Endgametrue) {
            telemetry.addData(">", "Time until Endgame Alert:  \n", (Endgamefloat - runtime.seconds()) );
        }

        if ((runtime.seconds() > EndOfGamefloat) && !Parktrue)  {
            gamepad1.runRumbleEffect(Park);
            Parktrue =true;
        }

        // Display the time remaining while we are still counting down.
        if (!Parktrue) {
            telemetry.addData(">", "Time until Park Alert:  \n", (EndOfGamefloat - runtime.seconds()) );
        }
        telemetry.update();
    }

    @Override
    public void stop()
    {
        //stops robot
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
        lift.setPower(0);
    }

}
