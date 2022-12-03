//Useless
package org.firstinspires.ftc.teamcode;


/**Imports*/
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
public class SpinbotTeleOp extends OpMode {
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
    public boolean good;

    Gamepad.RumbleEffect customRumbleEffect;
    Gamepad.RumbleEffect customRumbleEffect2;
    ElapsedTime runtime = new ElapsedTime();

    final double Endgame = 80.0;
    final double EndofGame = 110.0;

    @Override
    public void init() {
        customRumbleEffect = new Gamepad.RumbleEffect.Builder()
                .addStep(1.0, 1.0, 500)
                .build();
        customRumbleEffect2 = new Gamepad.RumbleEffect.Builder()
                .addStep(1.0, 1.0, 1000)
                .build();

        telemetry.addData(">", "Press Start");
        telemetry.update();
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back");
        Claw = hardwareMap.get(Servo.class, "Claw");
        lift = hardwareMap.get(DcMotor.class, "Lift");
        Claw.setPosition(ClawHome);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        lift.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void start() {
        good =true;
        while (good) {
            if ((runtime.seconds() > Endgame) && !secondHalf)  {
                gamepad1.runRumbleEffect(customRumbleEffect);
                gamepad2.runRumbleEffect(customRumbleEffect);
                secondHalf =true;
            }

            if (!secondHalf) {
                telemetry.addData(">", "Endgame Countdown \n", (Endgame - runtime.seconds()) );
            }
            if ((runtime.seconds() > EndofGame) && !LastCall) {
                gamepad1.runRumbleEffect(customRumbleEffect2);
                gamepad2.runRumbleEffect(customRumbleEffect2);
                LastCall =true;
            }

            if (!LastCall) {
                telemetry.addData(">", "END COUNTDOWN \n", (EndofGame - runtime.seconds()) );
            }
            else{
                telemetry.addLine("GET TO THE TERMINAL!!!");
                good =false;
            }
            telemetry.update();
        }
    }


    @Override
    public void loop() {
        boolean cooldown = false;
        double leftFrontPower;
        double leftBackPower;
        double rightFrontPower;
        double rightBackPower;
        runtime.reset();
        boolean started = true;


        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;


        leftFrontPower = Range.clip(drive + turn + strafe, -0.5, 0.5);
        rightFrontPower = Range.clip(drive - turn - strafe, -0.5, 0.5);
        leftBackPower = Range.clip(drive + turn - strafe, -0.5, 0.5);
        rightBackPower = Range.clip(drive - turn + strafe, -0.5, 0.5);


        telemetry.addData("lift motor pos", lift.getCurrentPosition());
        telemetry.addData("left stick X position", gamepad1.left_stick_x);
        telemetry.addData("left stick Y position", gamepad1.left_stick_y);
        telemetry.addData("left front power", leftFrontPower);
        telemetry.addData("right front power", rightFrontPower);
        telemetry.addData("left back power", leftBackPower);
        telemetry.addData("right back power", rightBackPower);

        if (!!cooldown) {
            try {
                wait(1/3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cooldown = false;
        }

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
    }
}
