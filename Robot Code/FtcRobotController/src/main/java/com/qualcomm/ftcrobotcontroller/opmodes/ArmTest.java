package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by shant on 10/15/2015.
 */
public class ArmTest extends OpMode {
    Servo armRight;
    Servo armLeft;
    double currentPos = 1D;
    Telemetry telemetry = new Telemetry();

    @Override
    public void init() {
        armRight = hardwareMap.servo.get("armRight");
        armLeft = hardwareMap.servo.get("armLeft");
        Log.d ("robot", "servo ports: " + armRight.getPortNumber() + "   " + armLeft.getPortNumber());
        armRight.setPosition(currentPos);
        armLeft.setPosition(currentPos);
        //telemetry.addData("arm", "servo moving to 1");



        //armLeft.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (gamepad1.a && gamepad1.b) {

        }
        else {
            if (gamepad1.a) {
                currentPos += 0.05;
                telemetry.addData("key", "adding value");
                Log.d("robot", "adding value");

            }
            if (gamepad1.b) {
                currentPos += 0.05;
                telemetry.addData("key", "subtracting value");
                Log.d("robot", "subtracting value");

            }
        }


        currentPos = Range.clip(currentPos, -1.00, 1.00);

        armLeft.setPosition(currentPos);
        armRight.setPosition(currentPos);
        Log.d("robot", "setting value");
        double curTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - curTime < 5) {

        }

        /*if(this.time <= 2){
            armRight.setPosition(0);
            armLeft.setPosition(0);
        }
        else if(this.time <=8 && this.time > 2){
            armLeft.setPosition(1);
            armRight.setPosition(1);
        }*/
        // float currentTime = System.currentTimeMillis();
        /*while (System.currentTimeMillis() - currentTime  < 2000)
        {
            telemetry.addData("arm", "servo moving to 0");
        }
        armRight.setPosition(-1);
        armLeft.setPosition(-1);
        currentTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - currentTime  < 2000)
        {
            telemetry.addData("arm", "servo moving to -1");

        }
        armRight.setPosition(1);
        armLeft.setPosition(1);
        currentTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - currentTime  < 2000)
        {
            telemetry.addData("arm", "servo moving to 1");

        }
*/

    }
}
