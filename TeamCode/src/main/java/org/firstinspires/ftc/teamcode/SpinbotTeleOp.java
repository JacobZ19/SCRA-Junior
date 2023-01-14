package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.concurrent.TimeUnit;

@TeleOp

public class SpinbotTeleOp extends OpMode {


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
        telemetry.clearAll();
    }



    @Override
    public void loop() {
        //sets most drive things
        double leftFrontPower;
        double leftBackPower;
        double rightFrontPower;
        double rightBackPower;
//        double turretpower;


        //set drive controls
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;
//        double turretleft = gamepad2.left_trigger;
//        double turretright = gamepad2.right_trigger;

        //set moter power
        leftFrontPower = Range.clip(drive+ turn + strafe, -0.5, 0.5);
        rightFrontPower = Range.clip(drive - turn - strafe, -0.5, 0.5);
        leftBackPower = Range.clip(drive + turn - strafe, -0.5, 0.5);
        rightBackPower = Range.clip(drive - turn + strafe, -0.5, 0.5);
//        turretpower = Range.clip(turretleft - turretright / 10, -0.5, 0.5);


        //telemetry define
        telemetry.addData("lift motor position: ", lift.getCurrentPosition());
        telemetry.addData("Turret Pos", turret.getCurrentPosition());
        telemetry.addData("Average Speed: ", (leftFrontPower + leftBackPower + rightBackPower + rightFrontPower) / 4);


        //telemetry update
        telemetry.update();
        //endgame timer
        liftpos = lift.getCurrentPosition();
//movement code
        if(gamepad1.left_stick_y >= 0.1 && gamepad1.right_stick_x <= -0.1){
            rightFrontPower = 1;
            rightBackPower = 1;
            leftFrontPower = -1;
            leftBackPower = -1;
        }

        else if(gamepad1.left_stick_y <= -0.1 && gamepad1.right_stick_x <= -0.1){
            leftFrontPower = 1;
            leftBackPower = 1;
            rightFrontPower = -1;
            rightBackPower = -1;
        }

        if(gamepad2.left_stick_button && liftpos >= 200){
            turretpos = turret.getCurrentPosition();
            turretpos -= 24;
            turret.setTargetPosition((int) turretpos);
            turret.setPower(1);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        else if(gamepad2.right_stick_button && liftpos >= 200){
            turretpos = turret.getCurrentPosition();
            turretpos += 24;
            turret.setTargetPosition((int) turretpos);
            turret.setPower(1);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        //custom up arm
        if (gamepad2.dpad_right && liftpos <= 870) {
            liftpos = lift.getCurrentPosition();
            liftpos += 24;
            lift.setTargetPosition((int) liftpos);

            lift.setPower(0.08);

            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        //custom down arm
        else if (gamepad2.dpad_left && liftpos > 0) {

            liftpos = lift.getCurrentPosition();
            liftpos -= 24;
            lift.setTargetPosition((int) liftpos);

            lift.setPower(0.08);

            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        //slow movement
        if (gamepad1.right_bumper)
        {
            leftFrontPower /= 3;
            leftBackPower /= 3;
            rightFrontPower /= 3;
            rightBackPower /= 3;
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
            lift.setTargetPosition(325);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.08);
        }


        //this is low pole
        if (gamepad2.b)
        {
            lift.setTargetPosition(625);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.08);
        }

        if (gamepad2.y)
        {
            lift.setTargetPosition(866);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.08);
        }


        //this is ground junction
        if (gamepad2.x)
        {
            lift.setTargetPosition(100);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.08);
        }

        //sets arm too 0.12511238294583
        if (gamepad2.dpad_down)
        {
            lift.setTargetPosition(0);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.1);
        }



        //claw close
        if(gamepad2.left_bumper){
            Claw.setPosition(0.0);
        }
        //claw open
        else if(gamepad2.right_bumper){
            Claw.setPosition(0.35);
        }
        //else if(!gamepad1.a) changed = false;

        leftFrontDrive.setPower((leftFrontPower));
        rightFrontDrive.setPower((rightFrontPower));
        leftBackDrive.setPower((leftBackPower));
        rightBackDrive.setPower((rightBackPower));

        if(gamepad1.share && gamepad2.share) {
            if (turretpos != 0) {
                lift.setTargetPosition(310);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setPower(0.08);
                sleep(100000);
                turret.setTargetPosition(0);
                turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if (turretpos > 0) {
                    turret.setPower(-0.05);
                } else if (turretpos < 0) {
                    turret.setPower(0.05);
                }
                sleep(10000);
                lift.setTargetPosition(0);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setPower(0.1);
                sleep(10000);
            }
        }

    }



    @Override
    public void stop()
    {
        // when stop is activated it stops
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
        if (turretpos != 0) {
            lift.setTargetPosition(310);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.08);
            sleep(2000);
            turret.setTargetPosition(0);
            turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (turretpos > 0) {
                turret.setPower(-0.05);
            } else if (turretpos < 0) {
                turret.setPower(0.05);
            }
            sleep(1000);
            lift.setTargetPosition(0);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.1);
            sleep(1000);
            lift.setPower(0);
        }

    }

}
