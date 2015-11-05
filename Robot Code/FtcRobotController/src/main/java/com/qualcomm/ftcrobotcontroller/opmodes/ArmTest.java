package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by shant on 10/15/2015.
 */
public class ArmTest extends OpMode {
    Servo armRight;
    Servo armLeft;
    //DcMotor motorSweeper;
    double currentPos = .8;
    final double servoDelta = 0.00115;

    final double ARM_TOP_CAP = 0.55;
    final double ARM_BOTTOM_CAP = 0.8;

    @Override
    public void init() {
        armRight = hardwareMap.servo.get("armRight");
        armLeft = hardwareMap.servo.get("armLeft");
        //motorSweeper = hardwareMap.dcMotor.get("motorSweeper");
        armLeft.setDirection(Servo.Direction.REVERSE);
        Log.d("robot", "servo ports: " + armRight.getPortNumber() + "   " + armLeft.getPortNumber());
        armRight.setPosition(currentPos);
        armLeft.setPosition(currentPos);
        //telemetry.addData("arm", "servo moving to 1");
    }

    @Override
    public void loop() {
        if (gamepad1.a && gamepad1.b) {

        }
        else {
            if (gamepad1.a) {
                currentPos += servoDelta;
                telemetry.addData("key", "adding value");
                Log.d("robot", "adding value");

            }
            if (gamepad1.b) {
                currentPos -= servoDelta;
                telemetry.addData("key", "subtracting value");
                Log.d("robot", "subtracting value");

            }
        }
        currentPos = Range.clip(currentPos, ARM_TOP_CAP, ARM_BOTTOM_CAP);
        /*if (currentPos == ARM_BOTTOM_CAP) {
            motorSweeper.setPower(-.5);
        } else {
            motorSweeper.setPower(0);
        }*/

        armLeft.setPosition(currentPos);
        armRight.setPosition(currentPos);
        Log.d("robot", "setting value to " + currentPos);
    }
}
