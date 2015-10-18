package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by shant on 10/17/2015.
 */
public class TankTreadDrive extends OpMode {
    DcMotor rightTop;
    DcMotor rightBottom;
    DcMotor leftTop;
    DcMotor leftBottom;

    @Override
    public void init() {
        rightTop = hardwareMap.dcMotor.get("rightTop");
        rightBottom = hardwareMap.dcMotor.get("rightBottom");
        leftTop = hardwareMap.dcMotor.get("leftTop");
        leftBottom = hardwareMap.dcMotor.get("leftBottom");

        leftTop.setDirection(DcMotor.Direction.REVERSE);
        leftBottom.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        /*  INNOVATIVE DOPE DRIVING SYSTEM - PLEASE READ BEFORE MODIFYING

            the y-axis of the right stick controls speed
            the x-axis of the right stick makes long turns
            the higher the x-value, the bigger the radius of the turn
            the left stick is for turns in place
            the left stick should override the right stick in case it passes a threshold (0.5ish)
        */

        // left and right for turning and wheel names is defined from looking back to front.

        // note that if y equal -1 then joystick is pushed all of the way forward.
        double speedForward = -gamepad1.right_stick_y;

        // Qualcomm is very inconsistent, so the x-values are 1 if pushed the the right
        double turnRadius = gamepad1.right_stick_x;
        double turnInPlace = gamepad1.left_stick_x;

        if (Math.abs(turnInPlace) > TURN_IN_PLACE_THRESHOLD){
            double motorPower = scaleValues(Math.abs(turnInPlace), TURN_IN_PLACE_THRESHOLD, JOYSTICK_MAX, MIN_TURN_IN_PLACE_POWER, MAX_POWER);
            //scale [0.7, 1] to [0.3, 1]
            motorPower = Range.clip(motorPower, MIN_POWER, MAX_POWER);
            setMotors(-motorPower, motorPower);
        }
        else {
            if (turnRadius < 0) {
            	double leftPower = (MAX_POWER + turnRadius) * speedForward;
            	setMotors(leftPower, speedForward);
            }
            
            if (turnRadius >= 0) {
            	double rightPower = (MAX_POWER - turnRadius) * speedForward;
            	setMotors(speedForward, rightPower);
            }
        }






    }

    private double findPower (double xValue, double yValue) {
        return Math.sqrt(xValue * xValue + yValue * yValue);
    }


    private double scaleValues(double numToScale, double origMin, double origMax, double newMin, double newMax) {
        numToScale = numToScale - origMin;
        //now numToScale is [0, origMax-origMin]

        numToScale = numToScale * ((newMax-newMin)/(origMax-origMin));
        //now numToScale is [0, newMax-newMin]

        numToScale = numToScale + newMin;

        return numToScale;
    }

    private void setMotors (double rightPower, double leftPower) {
        rightTop.setPower(rightPower);
        rightBottom.setPower(rightPower);
        leftTop.setPower(leftPower);
        leftBottom.setPower(leftPower);
    }
    
    public static double TURN_IN_PLACE_THRESHOLD = 0.7;
    public static double MIN_TURN_IN_PLACE_POWER = 0.3;
    public static double NO_POWER = 0.0;
    public static double MAX_POWER = 1.0;
    public static double MIN_POWER = -1.0;
    public static double JOYSTICK_MAX = 1.0;
}
