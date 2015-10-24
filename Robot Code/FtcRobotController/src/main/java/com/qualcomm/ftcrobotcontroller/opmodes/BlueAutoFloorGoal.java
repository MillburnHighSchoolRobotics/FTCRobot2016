package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by shant on 10/23/2015.
 */
public class BlueAutoFloorGoal extends AutonomousTemplate {
    public void runOpMode() throws InterruptedException {
        initializeComponents();

        waitForStart();

        //1120 clicks for full rotation


        moveStraight(-3000);

        try {
            RotateHP(45, -1);
        }
        catch (InterruptedException e){

        }

        moveStraight(-25000);


    }
}
