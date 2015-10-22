package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by shant on 10/20/2015.
 */
public class BlueFirstMountain extends AutonomousTemplate {

    @Override
    public void runOpMode() throws InterruptedException {
        initializeComponents();

        waitForStart();

        moveStraight(2500);

        try {
            RotateHP(-45, -1);
        }
        catch (InterruptedException E){

        }

        moveStraight(-3000);
    }
}
