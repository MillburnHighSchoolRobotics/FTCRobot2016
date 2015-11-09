package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

import virtualRobot.AutonomousRobot;
import virtualRobot.AutonomousTest1Logic;
import virtualRobot.Command;
import virtualRobot.ContinuousRotationServo;
import virtualRobot.LogicThread;
import virtualRobot.Motor;
import virtualRobot.Sensor;

public abstract class UpdateThread extends OpMode {
	
	private AutonomousRobot robot;
	protected Class<? extends LogicThread> logicThread;
	private Thread t;
	
	private DcMotor rightTop, rightBottom, leftTop, leftBottom, armLeftMotor, armRightMotor, reaper;
	private Servo armLeft, armRight, gateLeft, gateRight, spinner;
	private GyroSensor gyro;
	
	private Motor vDriveLeftMotor, vDriveRightMotor, vArmLeftMotor, vArmRightMotor, vReaperMotor;
	private virtualRobot.Servo vArmLeftServo, vArmRightServo, vGateLeftServo, vGateRightServo;
    private ContinuousRotationServo vSpinnerServo;
	private Sensor vDriveLeftMotorEncoder, vDriveRightMotorEncoder, vArmLeftMotorEncoder, vArmRightMotorEncoder, vAngleSensor;
	
	double curTime, prevTime, curRot, prevRot, gyroOffset;
	
	@Override
	public void init() {
		rightTop = hardwareMap.dcMotor.get("rightTop");
		rightBottom = hardwareMap.dcMotor.get("rightBottom");
		leftTop = hardwareMap.dcMotor.get("leftTop");
		leftBottom = hardwareMap.dcMotor.get("leftBottom");
        armLeftMotor = hardwareMap.dcMotor.get("armLeftMotor");
        armRightMotor = hardwareMap.dcMotor.get("armRightMotor");
        reaper = hardwareMap.dcMotor.get("reaper");

        armLeft = hardwareMap.servo.get("armLeft");
        armRight = hardwareMap.servo.get("armRight");
        gateLeft = hardwareMap.servo.get("gateLeft");
        gateRight = hardwareMap.servo.get("gateRight");
        spinner = hardwareMap.servo.get("spinner");

		rightTop.setDirection(DcMotor.Direction.REVERSE);
		rightBottom.setDirection(DcMotor.Direction.REVERSE);
		gyro = hardwareMap.gyroSensor.get("gyro");
				
		robot = Command.robot;

        vDriveLeftMotor = robot.getDriveLeftMotor();
        vDriveRightMotor = robot.getDriveRightMotor();
        vArmLeftMotor = robot.getArmLeftMotor();
        vArmRightMotor = robot.getArmRightMotor();
        vReaperMotor = robot.getReaperMotor();

        vDriveLeftMotorEncoder = robot.getDriveLeftMotorEncoder();
        vDriveRightMotorEncoder = robot.getDriveRightMotorEncoder();
        vArmLeftMotorEncoder = robot.getArmLeftMotorEncoder();
        vArmRightMotorEncoder = robot.getArmRightMotorEncoder();
        vAngleSensor = robot.getAngleSensor();

        vArmLeftServo = robot.getArmLeftServo();
        vArmRightServo = robot.getArmRightServo();
        vGateLeftServo = robot.getGateLeftServo();
        vGateRightServo = robot.getGateRightServo();
        vSpinnerServo = robot.getSpinnerServo();

        setLogicThread();

		try {
			t = new Thread(logicThread.newInstance());
		} catch (InstantiationException e) {
			return;
		} catch (IllegalAccessException e) {
			return;
		}

		gyroOffset = gyro.getRotation();
	}
	
	public void start() {
		
		curTime = System.currentTimeMillis();
		prevTime = System.currentTimeMillis();
		
		curRot = gyro.getRotation();
		prevRot = gyro.getRotation();
		
		vAngleSensor.setRawValue(0);
		
		vDriveLeftMotorEncoder.setRawValue(-leftTop.getCurrentPosition());
		vDriveRightMotorEncoder.setRawValue(-rightTop.getCurrentPosition());
		
		t.start();
	}
	
	public void loop() {
		
		// Capture
		
		double leftPower = vDriveLeftMotor.getPower();
		double rightPower = vDriveRightMotor.getPower();
        double armLeftPower = vArmLeftMotor.getPower();
        double armRightPower = vArmRightMotor.getPower();
        double reaperPower = vReaperMotor.getPower();
		
		// Update
		
		curTime = System.currentTimeMillis();
		curRot = gyro.getRotation();
		
		double delta = (curRot + prevRot) * 0.5 * (curTime - prevTime) * 0.001;
		vAngleSensor.setRawValue(vAngleSensor.getRawValue() + delta);
		
		vDriveLeftMotorEncoder.setRawValue(-leftTop.getCurrentPosition());
		vDriveRightMotorEncoder.setRawValue(-rightTop.getCurrentPosition());
        vArmLeftMotorEncoder.setRawValue(-armLeftMotor.getCurrentPosition());
        vArmRightMotorEncoder.setRawValue(-armRightMotor.getCurrentPosition());
		
		// Copy State
		
		leftTop.setPower(leftPower);
		leftBottom.setPower(leftPower);
		
		rightTop.setPower(rightPower);
		rightBottom.setPower(rightPower);

        armLeftMotor.setPower(armLeftPower);
        armRightMotor.setPower(armRightPower);

        reaper.setPower(reaperPower);

        gateLeft.setPosition(vGateLeftServo.getPosition());
        gateRight.setPosition(vGateRightServo.getPosition());
        armLeft.setPosition(vArmLeftServo.getPosition());
        armRight.setPosition(vArmRightServo.getPosition());
        spinner.setPosition(vSpinnerServo.getPosition());

		telemetry.addData("leftRawEncoder", Double.toString(leftTop.getCurrentPosition()));
		telemetry.addData("rightRawEncoder", Double.toString(rightTop.getCurrentPosition()));
		telemetry.addData("leftEncoder", Double.toString(vDriveLeftMotorEncoder.getRawValue()));
		telemetry.addData("rightEncoder", Double.toString(vDriveRightMotorEncoder.getRawValue()));
	}
	
	public void stop() {
		t.interrupt();
	}

	public abstract void setLogicThread();

}
