package com.qualcomm.ftcrobotcontroller.opmodes.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Yanjun on 11/6/2015.
 */
public class TestServo extends OpMode {

    Servo servo;

    public void init() {
        servo = hardwareMap.servo.get("servo");
    }

    public void loop() {
        servo.setPosition(0);
    }
}
