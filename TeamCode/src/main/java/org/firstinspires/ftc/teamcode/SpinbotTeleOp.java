package org.firstinspires.ftc.teamcode;

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


    }

    @Override
    public void start() {
        if ((runtime.seconds() > Endgame) && !secondHalf) {
            gamepad1.runRumbleEffect(customRumbleEffect);
            gamepad2.runRumbleEffect(customRumbleEffect);
            secondHalf = true;
            gamepad1.rumbleBlips(3);
        }

        if (!secondHalf) {
            telemetry.addData(">", "Endgame Countdown \n", (Endgame - runtime.seconds()));
        }
        if ((runtime.seconds() > EndofGame) && !LastCall) {
            gamepad1.runRumbleEffect(customRumbleEffect2);
            gamepad2.runRumbleEffect(customRumbleEffect2);
            LastCall = true;
            gamepad1.rumbleBlips(3);
        }

        if (!LastCall) {
            telemetry.addData(">", "END COUNTDOWN \n", (EndofGame - runtime.seconds()));
        } else {
            telemetry.addLine("GET TO THE TERMINAL!!!");
            telemetry.addLine("GET TO THE TERMINAL!!!");
            telemetry.addLine("GET TO THE TERMINAL!!!");
            telemetry.addLine("GET TO THE TERMINAL!!!");
        }
    }


    @Override
    public void loop() {


    }
}