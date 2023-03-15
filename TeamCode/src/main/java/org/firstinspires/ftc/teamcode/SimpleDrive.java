package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp

public class SimpleDrive extends OpMode {


    //sets all other varables
    boolean secondHalf = false;
    boolean LastCall = false;
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor lift = null;
    public Servo Claw = null;
    public final static double ClawHome = 0.0;
    public float liftpos;

    //rumble effects
    Gamepad.RumbleEffect customRumbleEffect;
    Gamepad.RumbleEffect customRumbleEffect2;
    ElapsedTime runtime = new ElapsedTime();

    //endgame timers
    final double Endgame = 80.0;
    final double EndofGame = 110.0;



    @Override
    public void init()
    {
//        //custom rumble effect
//        customRumbleEffect = new Gamepad.RumbleEffect.Builder()
//                .addStep(1.0, 1.0, 500)
//                .build();
//        customRumbleEffect2 = new Gamepad.RumbleEffect.Builder()
//                .addStep(1.0, 1.0, 1000)
//                .build();

        //tells you to press start
        telemetry.addData(">", "Press Start");
        telemetry.update();

        //finds the DCMOTORS
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back");
        Claw = hardwareMap.get(Servo.class, "Claw");
        lift = hardwareMap.get(DcMotor.class,"Lift");
        Claw.setPosition(ClawHome);

        //resets and sets lift and drive motions
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        lift.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void start() {

    }



    @Override
    public void loop() {
        //sets most drive things
        boolean cooldown = false;
        double leftFrontPower;
        double leftBackPower;
        double rightFrontPower;
        double rightBackPower;
        runtime.reset();
        boolean started = true;


        //set drive controls
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        //set moter power
        leftFrontPower = Range.clip(drive+ turn + strafe, -0.5, 0.5);
        rightFrontPower = Range.clip(drive - turn - strafe, -0.5, 0.5);
        leftBackPower = Range.clip(drive + turn - strafe, -0.5, 0.5);
        rightBackPower = Range.clip(drive - turn + strafe, -0.5, 0.5);


        //telemetry define
        telemetry.addData("lift motor pos", lift.getCurrentPosition());
        telemetry.addData("left stick X position", gamepad1.left_stick_x);
        telemetry.addData("left front power", leftFrontPower);

        telemetry.addData("ArmHight", liftpos);
//hi

        if (!!cooldown) {
            try {
                wait(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cooldown = false;
        }


        //telemetry update
        telemetry.update();
            //endgame timer
            if ((runtime.seconds() > Endgame) && !secondHalf)  {
                gamepad1.runRumbleEffect(customRumbleEffect);
                gamepad2.runRumbleEffect(customRumbleEffect);
                secondHalf =true;
                gamepad1.rumbleBlips(3);
            }

            if (!secondHalf) {
                telemetry.addData(">", "Endgame Countdown \n", (Endgame - runtime.seconds()) );
            }
            if ((runtime.seconds() > EndofGame) && !LastCall) {
                gamepad1.runRumbleEffect(customRumbleEffect2);
                gamepad2.runRumbleEffect(customRumbleEffect2);
                LastCall =true;
                gamepad1.rumbleBlips(3);
            }

            if (!LastCall) {
                telemetry.addData(">", "END COUNTDOWN \n", (EndofGame - runtime.seconds()) );
            }
            else{
                telemetry.addLine("GET TO THE TERMINAL!!!");
                telemetry.addLine("GET TO THE TERMINAL!!!");
                telemetry.addLine("GET TO THE TERMINAL!!!");
                telemetry.addLine("GET TO THE TERMINAL!!!");
            }



//movement code
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

        //custom up arm
        if (gamepad2.dpad_right && liftpos <=450) {
            liftpos = lift.getCurrentPosition();
            liftpos += 1;
            lift.setTargetPosition((int) liftpos);

            lift.setPower(0.05);

            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        //custom down arm
        else if (gamepad2.dpad_left && liftpos > 0) {

            liftpos = lift.getCurrentPosition();
            liftpos -= 1;
            lift.setTargetPosition((int) liftpos);

            lift.setPower(0.05);

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
            lift.setTargetPosition(270);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.1);
        }


        //this is low pole
        if (gamepad2.b)
        {
            lift.setTargetPosition(430);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.1);
        }


        //this is ground junction
        if (gamepad2.x)
        {
            lift.setTargetPosition(20);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.2);
        }

        //sets arm too 0.12511238294583
        if (gamepad2.dpad_down)
        {
            lift.setTargetPosition(0);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.12511238294583);
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

        //custom rumble effect
        if(gamepad1.x)
        {
            gamepad1.runRumbleEffect(customRumbleEffect2);
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
//k754a+Aaron
//4:07 final compition before worlds
//4:33  so close! we lost lol
