package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
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
    public final static double ClawHome = 0.0;
    public float liftpos;
    public float turretpos;
    public boolean Forward = true;
    public boolean Backwark = true;
    public float time_elapsed = 0;
    public boolean Endgametrue = false;
    public boolean Parktrue = false;

    AutoDrive robot = new AutoDrive(this);

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
                .build();
        Park = new Gamepad.RumbleEffect.Builder()
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
        Claw.setPosition(ClawHome);

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
    }



    @Override
    public void loop() {
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
        leftFrontPower = Range.clip(drive+ turn + strafe, -0.6, 0.6);
        rightFrontPower = Range.clip(drive - turn - strafe, -0.6, 0.6);
        leftBackPower = Range.clip(drive + turn - strafe, -0.6, 0.6);
        rightBackPower = Range.clip(drive - turn + strafe, -0.6, 0.6);


        //telemetry define
        telemetry.addData("lift motor position: ", lift.getCurrentPosition());
        telemetry.addData("Turret Pos", turret.getCurrentPosition());
        telemetry.addData("Average Speed: ", (leftFrontPower + leftBackPower + rightBackPower + rightFrontPower) / 4);
        telemetry.addData("Left Stick X: ", gamepad1.left_stick_x);
        telemetry.addData("Left Stick Y: ", -gamepad1.left_stick_y);
        telemetry.addData("Right Stick X: ", gamepad1.right_stick_x);
        telemetry.addData("Right Stick Y: ", gamepad1.right_stick_y);



        //telemetry update
        telemetry.update();
        //endgame timer
        liftpos = lift.getCurrentPosition();

        if (time_elapsed < 14000) {
            time_elapsed += 1;
        }
        else {
            time_elapsed +=1;
            if (! Endgametrue) {
                gamepad1.runRumbleEffect(Endgame);
                gamepad2.runRumbleEffect(Endgame);
                telemetry.addLine(">>>>>Collect the DUCK BEACON<<<<<");
                Endgametrue = true;
            }

            if (time_elapsed >= 190000) {
                if (! Parktrue) {
                    gamepad1.runRumbleEffect(Park);
                    gamepad2.runRumbleEffect(Park);
                    telemetry.addLine(">>>>>PARK IN THE TERMINAL<<<<<");
                    Parktrue = true;
                }
            }
        }

        if(gamepad1.b){
            Claw.setPosition(0.35);
            lift.setTargetPosition(660);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.1);
            leftFrontPower = -0.16;
            leftBackPower = -0.16;
            rightFrontPower = -0.16;
            rightBackPower = -0.16;
        }
        if(gamepad1.y){
            Claw.setPosition(0.35);
            lift.setTargetPosition(995);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.1);
            leftFrontPower = -0.16;
            leftBackPower = -0.16;
            rightFrontPower = -0.16;
            rightBackPower = -0.16;
        }
        if(gamepad1.x){
            Claw.setPosition(0.35);
            lift.setTargetPosition(995);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.1);
            leftFrontPower = -0.16;
            leftBackPower = -0.16;
            rightFrontPower = -0.16;
            rightBackPower = -0.16;
        }

        if(gamepad2.left_stick_button && liftpos >= 150 && turretpos >= -1400){

            turretpos = turret.getCurrentPosition();
            turretpos -= 100;
            turret.setTargetPosition((int) turretpos);
            turret.setPower(1);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        else if(gamepad2.right_stick_button && liftpos >= 150 && turretpos <= 1400){
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
            sleep(200);
        }

        //custom down arm
        else if (gamepad2.dpad_left && liftpos > 0) {

            liftpos = lift.getCurrentPosition();
            liftpos -= 45;
            lift.setTargetPosition((int) liftpos);

            lift.setPower(0.1);

            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sleep(200);
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
            leftFrontPower /= 2;
            leftBackPower /= 2;
            rightFrontPower /= 2;
            rightBackPower /= 2;
        }


        //this is med pole
        if (gamepad2.a)
        {
            lift.setTargetPosition(450);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.25);
        }

        if (gamepad1.a)
        {
            turret.setTargetPosition(0);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(1);
            lift.setTargetPosition(220);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.25);
        }


        //this is low pole
        if (gamepad2.b)
        {
            lift.setTargetPosition(660);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.25);
        }

        if (gamepad2.y)
        {
            lift.setTargetPosition(995);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.25);
        }


        //this is ground junction
        if (gamepad2.x)
        {
            lift.setTargetPosition(100);
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
            lift.setTargetPosition(0);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(-0.3);
        }

        if (gamepad2.touchpad)
        {
            turret.setTargetPosition(0);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(1);
        }
        if (gamepad2.left_trigger >= 0.8 && liftpos >= -10 && liftpos <= 10)
        {
            turret.setTargetPosition(-1300);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(1);
        }
        else if (gamepad2.right_trigger >= 0.8 && liftpos >= -10 && liftpos <= 10)
        {
            turret.setTargetPosition(1300);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setPower(1);
        }

//hi

        //claw close
        if(gamepad2.left_bumper){
            Claw.setPosition(0.0);
        }
        //claw open
        else if(gamepad2.right_bumper){
            Claw.setPosition(0.35);
        }

        leftFrontDrive.setPower((leftFrontPower));
        rightFrontDrive.setPower((rightFrontPower));
        leftBackDrive.setPower((leftBackPower));
        rightBackDrive.setPower((rightBackPower));


    }



    @Override
    public void stop()
    {
        // when stop is activated it stops
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
            lift.setPower(0);

    }

}
