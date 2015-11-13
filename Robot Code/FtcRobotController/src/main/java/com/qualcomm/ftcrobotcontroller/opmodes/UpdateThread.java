package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

import virtualRobot.AutonomousRobot;
import virtualRobot.Command;
import virtualRobot.ContinuousRotationServo;
import virtualRobot.JoystickController;
import virtualRobot.LogicThread;
import virtualRobot.Motor;
import virtualRobot.Sensor;

public abstract class UpdateThread extends OpMode {
	
	private AutonomousRobot robot;
	protected Class<? extends LogicThread> logicThread;
	private Thread t;
	
	private DcMotor rightTop, rightBottom, leftTop, leftBottom, armLeftMotor, armRightMotor, reaper;
	private Servo armLeft, armRight, gateLeft, gateRight, spinner, blockerLeft, blockerRight;
	private GyroSensor gyro;
    private ColorSensor colorSensor;
	
	private Motor vDriveLeftMotor, vDriveRightMotor, vArmLeftMotor, vArmRightMotor, vReaperMotor;
	private virtualRobot.Servo vArmLeftServo, vArmRightServo, vGateLeftServo, vGateRightServo, vBlockerLeftServo, vBlockerRightServo;
    private ContinuousRotationServo vSpinnerServo;
	private Sensor vDriveLeftMotorEncoder, vDriveRightMotorEncoder, vArmLeftMotorEncoder, vArmRightMotorEncoder, vAngleSensor, vColorSensor;


    private JoystickController vGamepad;

	double curTime, prevTime, curRot, prevRot, gyroOffset;
	
	@Override
	public void init() {
        //MOTOR SETUP
		rightTop = hardwareMap.dcMotor.get("rightTop");
		rightBottom = hardwareMap.dcMotor.get("rightBottom");
		leftTop = hardwareMap.dcMotor.get("leftTop");
		leftBottom = hardwareMap.dcMotor.get("leftBottom");
        armLeftMotor = hardwareMap.dcMotor.get("armLeftMotor");
        armRightMotor = hardwareMap.dcMotor.get("armRightMotor");
        reaper = hardwareMap.dcMotor.get("reaper");

        //SERVO SETUP
        armLeft = hardwareMap.servo.get("armLeft");
        armRight = hardwareMap.servo.get("armRight");
        gateLeft = hardwareMap.servo.get("gateLeft");
        gateRight = hardwareMap.servo.get("gateRight");
        spinner = hardwareMap.servo.get("spinner");
		blockerLeft = hardwareMap.servo.get("blockerLeft");
		blockerRight = hardwareMap.servo.get("blockerRight");

        //REVERSE RIGHT SIDE
        blockerRight.setDirection(Servo.Direction.REVERSE);
        gateRight.setDirection(Servo.Direction.REVERSE);
		rightTop.setDirection(DcMotor.Direction.REVERSE);
		rightBottom.setDirection(DcMotor.Direction.REVERSE);
        armRight.setDirection(Servo.Direction.REVERSE);

        //SENSOR SETUP
		gyro = hardwareMap.gyroSensor.get("gyro");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");

        //FETCH VIRTUAL ROBOT FROM COMMAND INTERFACE
		robot = Command.robot;

        //FETCH VIRTUAL COMPONENTS OF VIRTUAL ROBOT
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
        vColorSensor = robot.getColorSensor();

        vArmLeftServo = robot.getArmLeftServo();
        vArmRightServo = robot.getArmRightServo();
        vGateLeftServo = robot.getGateLeftServo();
        vGateRightServo = robot.getGateRightServo();
        vSpinnerServo = robot.getSpinnerServo();
		vBlockerLeftServo = robot.getBlockerLeftServo();
		vBlockerRightServo = robot.getBlockerRightServo();

        vGamepad = robot.getJoystickController();

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

        vColorSensor.setRawValue(colorSensor.argb());
		
		vDriveLeftMotorEncoder.setRawValue(-leftTop.getCurrentPosition());
		vDriveRightMotorEncoder.setRawValue(-rightTop.getCurrentPosition());
        vArmLeftMotorEncoder.setRawValue(-armLeftMotor.getCurrentPosition());
        vArmRightMotorEncoder.setRawValue(-armRightMotor.getCurrentPosition());

        try {
            vGamepad.copyStates(gamepad1);
        } catch (RobotCoreException e) {
            e.printStackTrace();
        }

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
		blockerLeft.setPosition(vBlockerLeftServo.getPosition());
		blockerRight.setPosition(vBlockerRightServo.getPosition());

		telemetry.addData("leftRawEncoder", Double.toString(leftTop.getCurrentPosition()));
		telemetry.addData("rightRawEncoder", Double.toString(rightTop.getCurrentPosition()));
		telemetry.addData("leftEncoder", Double.toString(vDriveLeftMotorEncoder.getRawValue()));
		telemetry.addData("rightEncoder", Double.toString(vDriveRightMotorEncoder.getRawValue()));
        telemetry.addData("color", String.format("%06x", (int) vColorSensor.getRawValue()));
	}
	
	public void stop() {
		t.interrupt();
	}

	public abstract void setLogicThread();

}
