package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by shant on 10/17/2015.
 */
public class TankTreadDrive extends OpMode {
	DcMotor rightTop;
	DcMotor rightBottom;
	DcMotor leftTop;
	DcMotor leftBottom;

	Servo armRight;
	Servo armLeft;
	DcMotor motorSweeper;

	Servo leftGate;
	Servo rightGate;
	Servo scorer;

	double currentPos = .8;
	final double servoDelta = 0.00115;

	final double ARM_TOP_CAP = 0.55;
	final double ARM_BOTTOM_CAP = 0.8;

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
		motorSweeper = hardwareMap.dcMotor.get("motorSweeper");
		armLeft.setDirection(Servo.Direction.REVERSE);
		Log.d("robot", "servo ports: " + armRight.getPortNumber() + "   " + armLeft.getPortNumber());
		armRight.setPosition(currentPos);
		armLeft.setPosition(currentPos);

		leftGate = hardwareMap.servo.get("leftGate");
		rightGate = hardwareMap.servo.get("rightGate");
		scorer = hardwareMap.servo.get("scorer");

		leftGate.setDirection(Servo.Direction.REVERSE);

		leftGate.setPosition(1);
		rightGate.setPosition(1);
		scorer.setPosition(0.5);
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

		/*double x = gamepad1.right_stick_x;
		double y = -gamepad1.right_stick_y;

		double SQRT_2 = Math.sqrt(2);
		double radius = Math.sqrt(x*x + y*y);
		double angle = Math.atan2(y, x);

		if (x == 1 || x == -1 || y == 1 || y == -1) {
			radius = SQRT_2;

			if (x == 1) {
				angle = Math.asin(y/radius);
			} else if (x == -1) {
				angle = Math.PI - Math.asin(y/radius);
			} else if (y == 1) {
				angle = Math.acos(x/radius);
			} else if (y == -1) {
				angle = 2*Math.PI - Math.acos(x/radius);
			}
		}

		x = radius * Math.cos(angle) * SQRT_2 * 0.5;
		y = radius * Math.sin(angle) * SQRT_2 * 0.5;

        // note that if y equal -1 then joystick is pushed all of the way forward.
        double speedForward = y;

        // Qualcomm is very inconsistent, so the x-values are 1 if pushed the the right
        double turnRadius = x;
        double turnInPlace = gamepad1.left_stick_x;

        if (Math.abs(turnInPlace) > TURN_IN_PLACE_THRESHOLD) {
            double motorPower = Math.signum(turnInPlace) * scaleValues(Math.abs(turnInPlace), TURN_IN_PLACE_THRESHOLD, JOYSTICK_MAX, MIN_TURN_IN_PLACE_POWER, MAX_POWER);
            //scale [0.7, 1] to [0.3, 1]
            motorPower = Range.clip(motorPower, MIN_POWER, MAX_POWER);
            setMotors(motorPower, -motorPower);
        } else {
        	
        	speedForward = (Math.signum(speedForward) == 0 ? 1 : Math.signum(speedForward)) * radius * SQRT_2 * 0.5;
        	speedForward = Range.clip(speedForward, -1, 1);
            if (turnRadius < 0) {
            	double leftPower = (MAX_POWER + turnRadius) * speedForward;
            	setMotors(leftPower, speedForward);
            }
            
            if (turnRadius >= 0) {
            	double rightPower = (MAX_POWER - turnRadius) * speedForward;
            	setMotors(speedForward, rightPower);
            }
        }
        */

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

		if (currentPos == ARM_BOTTOM_CAP) {
			motorSweeper.setPower(-.5);
		} else {
			motorSweeper.setPower(0);
		}

		if(gamepad1.left_trigger > 0.7) {
			leftGate.setPosition(0);
			rightGate.setPosition(1);
			scorer.setPosition(0);
			motorSweeper.setPower(-0.5);
		} else if (gamepad1.right_trigger > 0.7) {
			rightGate.setPosition(0);
			leftGate.setPosition(1);
			scorer.setPosition(1);
			motorSweeper.setPower(-0.5);
		} else {
			leftGate.setPosition(1);
			rightGate.setPosition(1);
			scorer.setPosition(0.5);
			motorSweeper.setPower(0);
		}

		currentPos = Range.clip(currentPos, ARM_TOP_CAP, ARM_BOTTOM_CAP);
		if (currentPos == ARM_BOTTOM_CAP) {
			motorSweeper.setPower(-.5);
		}

		armLeft.setPosition(currentPos);
		armRight.setPosition(currentPos);
		Log.d("robot", "setting value to " + currentPos);

    }

	private double findPower(double xValue, double yValue) {
		return Math.sqrt(xValue * xValue + yValue * yValue);
	}

	private double scaleValues(double numToScale, double origMin,
			double origMax, double newMin, double newMax) {
		numToScale -= origMin;
		// now numToScale is [0, origMax-origMin]

		numToScale *= (newMax - newMin) / (origMax - origMin);
		// now numToScale is [0, newMax-newMin]

		numToScale += newMin;

		return numToScale;
	}

	private void setMotors(double rightPower, double leftPower) {
		rightTop.setPower(rightPower);
		rightBottom.setPower(rightPower);
		leftTop.setPower(leftPower);
		leftBottom.setPower(leftPower);
	}

	double scaleInput(double dVal)  {
		double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
				0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

		// get the corresponding index for the scaleInput array.
		int index = (int) (dVal * 16.0);

		// index should be positive.
		if (index < 0) {
			index = -index;
		}

		// index cannot exceed size of array minus 1.
		if (index > 16) {
			index = 16;
		}

		// get value from the array.
		double dScale = 0.0;
		if (dVal < 0) {
			dScale = -scaleArray[index];
		} else {
			dScale = scaleArray[index];
		}

		// return scaled value.
		return dScale;
	}

	public static double TURN_IN_PLACE_THRESHOLD = 0.7;
	public static double MIN_TURN_IN_PLACE_POWER = 0.3;
	public static double NO_POWER = 0.0;
	public static double MAX_POWER = 1.0;
	public static double MIN_POWER = -1.0;
	public static double JOYSTICK_MAX = 1.0;
}
