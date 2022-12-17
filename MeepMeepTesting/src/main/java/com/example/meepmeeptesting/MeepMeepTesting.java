package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, -61, 7.86))
                                .waitSeconds(1)
                                .forward(25)
                                .turn(Math.toRadians(-45))
                                .waitSeconds(2)
                                .turn(Math.toRadians(135))
                                .strafeRight(25)
                                .waitSeconds(1)
                                .forward(20)
                                .waitSeconds(1)
                                .back(20)
                                .waitSeconds(1.5)
                                .turn(Math.toRadians(135))
                                .waitSeconds(2)
                                .turn(Math.toRadians(45))
                                .strafeRight(25)
                                .back(25)
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}