package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by shant on 10/17/2015.
 */
public class TankTeleOp extends OpMode {
    DcMotor rightTop;
    DcMotor rightBottom;
    DcMotor leftTop;
    DcMotor leftBottom;
    Servo armRight;
    Servo armLeft;
    double currentPos = 1;
    final double servoDelta = 0.00115;
    final double SERVO_MIN_POS = 0.7;
    final double SERVO_MAX_POS = 1D;



    @Override
    public void init() {
        rightTop = hardwareMap.dcMotor.get("rightTop");
        rightBottom = hardwareMap.dcMotor.get("rightBottom");
        leftTop = hardwareMap.dcMotor.get("leftTop");
        leftBottom = hardwareMap.dcMotor.get("leftBottom");

        leftTop.setDirection(DcMotor.Direction.REVERSE);
        leftBottom.setDirection(DcMotor.Direction.REVERSE);

        armRight = hardwareMap.servo.get("armRight");
        armLeft = hardwareMap.servo.get("armLeft");
        armLeft.setDirection(Servo.Direction.REVERSE);


    }

    @Override
    public void loop() {
        //lower the arm
        if (gamepad1.a) {
            currentPos += servoDelta;
            telemetry.addData("key", "adding value");
            Log.d("robot", "adding value");

            currentPos = Range.clip(currentPos, SERVO_MIN_POS, SERVO_MAX_POS);
            armLeft.setPosition(currentPos);
            armRight.setPosition(currentPos);
        }

        //raise the arm
        if (gamepad1.b) {
            currentPos -= servoDelta;
            telemetry.addData("key", "subtracting value");
            Log.d("robot", "subtracting value");


            currentPos = Range.clip(currentPos, SERVO_MIN_POS, SERVO_MAX_POS);
            armLeft.setPosition(currentPos);
            armRight.setPosition(currentPos);
        }

        Log.d("robot", "setting value");
    }
}