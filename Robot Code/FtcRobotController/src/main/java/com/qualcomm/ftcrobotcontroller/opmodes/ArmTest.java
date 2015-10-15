package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by shant on 10/15/2015.
 */
public class ArmTest extends OpMode {
    Servo armRight;
    Servo armLeft;
    int currentPos = 1;

    @Override
    public void init() {
        armRight = hardwareMap.servo.get("armRight");
        armLeft = hardwareMap.servo.get("armLeft");
        armRight.setPosition(currentPos);
        armLeft.setPosition(currentPos);


        //armLeft.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_down) {
            currentPos -=0.05;
            armRight.setPosition(currentPos);
            armLeft.setPosition(currentPos);
        }
        if (gamepad1.dpad_up) {
            currentPos +=0.05;
            armRight.setPosition(currentPos);
            armLeft.setPosition(currentPos);
        }

    }
}
