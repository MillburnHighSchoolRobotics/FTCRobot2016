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
    DcMotor armReachL;
	DcMotor armReachR;
	/*Servo leftGate;
	Servo rightGate;
	Servo scorer;*/

	double currentPos = .8;
	final double servoDelta = 0.00115;

	final double ARM_TOP_CAP = 0.5;
	final double ARM_BOTTOM_CAP = 0.75;
	final double LEFT_GATE_REST_POSITION = 0.9;
	final double LEFT_GATE_DOWN_POSITION = 0.4;
	final double RIGHT_GATE_REST_POSITION = 1;
	final double RIGHT_GATE_DOWN_POSITION = 0.3;
	final double SWEEPER_MOTOR_SPEED = 0.4;

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
		armReachL = hardwareMap.dcMotor.get("armReachL");
		armReachR = hardwareMap.dcMotor.get("armReachR");
		Log.d("robot", "servo ports: " + armRight.getPortNumber() + "   " + armLeft.getPortNumber());
		armRight.setPosition(currentPos);
		armLeft.setPosition(currentPos);

		/*leftGate = hardwareMap.servo.get("leftGate");
		rightGate = hardwareMap.servo.get("rightGate");
		scorer = hardwareMap.servo.get("scorer");

		leftGate.setDirection(Servo.Direction.REVERSE);

		leftGate.setPosition(LEFT_GATE_REST_POSITION);
		rightGate.setPosition(1);
		scorer.setPosition(0.5);*/
	}

	@Override
    public void loop() {
		// tank drive
		// note that if y equal -1 then joystick is pushed all of the way forward.

		float left = -gamepad1.right_stick_y;
		float right = -gamepad1.left_stick_y;

		// clip the right/left values so that the values never exceed +/- 1
		right = Range.clip(right, -1, 1);
		left = Range.clip(left, -1, 1);

		//scale the joystick value to make it easier to control
		// the robot more precisely at slower speeds
		right = (float) scaleInput(right);
		left = (float) scaleInput(left);

		//write the values to the motors
		leftBottom.setPower(left);
		leftTop.setPower(left);

		rightBottom.setPower(right);
		rightTop.setPower(right);

		//leftGate.setPosition(LEFT_GATE_REST_POSITION);

		/*if (gamepad1.a && gamepad1.b) {

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

			}*/
			if (gamepad1.left_bumper){
				armReachL.setPower(.5);
			} else if (gamepad1.left_trigger >= .5){
				armReachL.setPower(-.5);
			} else {
				armReachL.setPower(0);
			}
			if (gamepad1.right_bumper){
				armReachR.setPower(.5);
			} else if (gamepad1.right_trigger >= .5){
				armReachR.setPower(-.5);
			} else {
				armReachR.setPower(0);
			}
			}


		/*if (currentPos == ARM_BOTTOM_CAP) {
			motorSweeper.setPower(-SWEEPER_MOTOR_SPEED);
		} else {
			motorSweeper.setPower(0);
		}



		if(gamepad1.left_trigger > 0.7) {
			leftGate.setPosition(LEFT_GATE_DOWN_POSITION);
			rightGate.setPosition(RIGHT_GATE_REST_POSITION);
			scorer.setPosition(0);
			motorSweeper.setPower(-SWEEPER_MOTOR_SPEED);
		} else if (gamepad1.right_trigger > 0.7) {
			rightGate.setPosition(RIGHT_GATE_DOWN_POSITION);
			leftGate.setPosition(LEFT_GATE_REST_POSITION);
			scorer.setPosition(1);
			motorSweeper.setPower(-SWEEPER_MOTOR_SPEED);
		} else {
			leftGate.setPosition(LEFT_GATE_REST_POSITION);
			rightGate.setPosition(1);
			scorer.setPosition(0.5);
			motorSweeper.setPower(0);
		}

		currentPos = Range.clip(currentPos, ARM_TOP_CAP, ARM_BOTTOM_CAP);
		if (currentPos == ARM_BOTTOM_CAP) {
			motorSweeper.setPower(-SWEEPER_MOTOR_SPEED);
		}
		//reverse the sweeper
		if (gamepad1.left_bumper && gamepad1.right_bumper) {
			motorSweeper.setPower(SWEEPER_MOTOR_SPEED);
		}

		armLeft.setPosition(currentPos);
		armRight.setPosition(currentPos);
		Log.d("robot", "setting value to " + currentPos);

    }*/

	/*private double findPower(double xValue, double yValue) {
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
*/
	private void setMotors(double rightPower, double leftPower) {
		rightTop.setPower(rightPower * .85);
		rightBottom.setPower(rightPower * .85);
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
