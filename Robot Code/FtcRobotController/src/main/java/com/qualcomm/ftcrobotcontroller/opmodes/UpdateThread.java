package com.qualcomm.ftcrobotcontroller.opmodes;

import com.kauailabs.navx.ftc.MPU9250;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import virtualRobot.Command;
import virtualRobot.JoystickController;
import virtualRobot.LogicThread;
import virtualRobot.Motor;
import virtualRobot.SallyJoeBot;
import virtualRobot.Sensor;

public abstract class UpdateThread extends OpMode {
	
	private SallyJoeBot robot;
	protected Class<? extends LogicThread> logicThread;
	private Thread t;
	
	private DcMotor rightFront, rightBack, leftFront, leftBack, tapeMeasureLeftMotor, tapeMeasureRightMotor;
	private Servo tapeMeasureLeft, tapeMeasureRight, flipperLeft, flipperRight, dumper, snowPlowLeft, snowPlowRight, backShieldLeft, backShieldRight, buttonPusher;
	private MPU9250 imu;
	private AnalogInput ultrasound;
	private ColorSensor colorSensor;
	private DigitalChannel colorSensorLed;
	
	private Motor vDriveLeftMotor, vDriveRightMotor, vTapeMeasureLeftMotor, vTapeMeasureRightMotor;
	private virtualRobot.Servo vTapeMeasureLeftServo, vTapeMeasureRightServo, vFlipperLeftServo, vFlipperRightServo, vDumperServo, vBackShieldServo, vSnowPlowServo, vButtonPusherServo;
	private Sensor vDriveLeftMotorEncoder, vDriveRightMotorEncoder, vTapeMeasureLeftMotorEncoder, vTapeMeasureRightMotorEncoder, vHeadingSensor, vColorSensor, vUltrasoundSensor, vTiltSensor;

	private JoystickController vJoystickController1, vJoystickController2;
	
	@Override
	public void init() {
        //MOTOR SETUP
		rightFront = hardwareMap.dcMotor.get("rightFront");
		rightBack = hardwareMap.dcMotor.get("rightBack");
		leftFront = hardwareMap.dcMotor.get("leftFront");
		leftBack = hardwareMap.dcMotor.get("leftBack");
        tapeMeasureLeftMotor = hardwareMap.dcMotor.get("tapeMeasureLeftMotor");
        tapeMeasureRightMotor = hardwareMap.dcMotor.get("tapeMeasureRightMotor");

        //SERVO SETUP
        tapeMeasureLeft = hardwareMap.servo.get("tapeMeasureLeft");
        tapeMeasureRight = hardwareMap.servo.get("tapeMeasureRight");
        flipperLeft = hardwareMap.servo.get("flipperLeft");
        flipperRight = hardwareMap.servo.get("flipperRight");
		dumper = hardwareMap.servo.get("dumper");
		backShieldLeft = hardwareMap.servo.get("backShieldLeft");
        backShieldRight = hardwareMap.servo.get("backShieldRight");
		snowPlowLeft = hardwareMap.servo.get("snowPlowLeft");
        snowPlowRight = hardwareMap.servo.get("snowPlowRight");
		buttonPusher = hardwareMap.servo.get("buttonPusher");

        //REVERSE RIGHT SIDE
        backShieldRight.setDirection(Servo.Direction.REVERSE);
        snowPlowRight.setDirection(Servo.Direction.REVERSE);
        flipperRight.setDirection(Servo.Direction.REVERSE);
		rightFront.setDirection(DcMotor.Direction.REVERSE);
		rightBack.setDirection(DcMotor.Direction.REVERSE);
        tapeMeasureRight.setDirection(Servo.Direction.REVERSE);
		tapeMeasureRightMotor.setDirection(DcMotor.Direction.REVERSE);

        //SENSOR SETUP
		imu = MPU9250.getInstance(hardwareMap.deviceInterfaceModule.get("dim"), 0);
		colorSensor = hardwareMap.colorSensor.get("colorSensor");
		colorSensorLed = hardwareMap.digitalChannel.get("colorSensorLed");
		ultrasound = hardwareMap.analogInput.get("ultrasound");

        //FETCH VIRTUAL ROBOT FROM COMMAND INTERFACE
		robot = Command.ROBOT;

        //FETCH VIRTUAL COMPONENTS OF VIRTUAL ROBOT
        vDriveLeftMotor = robot.getDriveLeftMotor();
        vDriveRightMotor = robot.getDriveRightMotor();
        vTapeMeasureLeftMotor = robot.getTapeMeasureLeftMotor();
        vTapeMeasureRightMotor = robot.getTapeMeasureRightMotor();

        vDriveLeftMotorEncoder = robot.getDriveLeftMotorEncoder();
        vDriveRightMotorEncoder = robot.getDriveRightMotorEncoder();
        vTapeMeasureLeftMotorEncoder = robot.getTapeMeasureLeftMotorEncoder();
        vTapeMeasureRightMotorEncoder = robot.getTapeMeasureRightMotorEncoder();
        vHeadingSensor = robot.getHeadingSensor();
		vColorSensor = robot.getColorSensor();
		vTiltSensor = robot.getTiltSensor();

        vTapeMeasureLeftServo = robot.getTapeMeasureLeftServo();
        vTapeMeasureRightServo = robot.getTapeMeasureRightServo();
        vFlipperLeftServo = robot.getFlipperLeftServo();
        vFlipperRightServo = robot.getFlipperRightServo();
		vDumperServo = robot.getDumperServo();
		vBackShieldServo = robot.getBackShieldServo();
		vSnowPlowServo = robot.getSnowPlowServo();
		vButtonPusherServo = robot.getButtonPusherServo();

		vUltrasoundSensor = robot.getUltrasoundSensor();

        vJoystickController1 = robot.getJoystickController1();
        vJoystickController2 = robot.getJoystickController2();

        setLogicThread();

		try {
			t = new Thread(logicThread.newInstance());
		} catch (InstantiationException e) {
			return;
		} catch (IllegalAccessException e) {
			return;
		}

	}

	public void start() {

		vDriveLeftMotorEncoder.setRawValue(-leftFront.getCurrentPosition());
		vDriveRightMotorEncoder.setRawValue(-rightFront.getCurrentPosition());
        vTapeMeasureLeftMotorEncoder.setRawValue(-tapeMeasureLeftMotor.getCurrentPosition());
        vTapeMeasureRightMotorEncoder.setRawValue(-tapeMeasureRightMotor.getCurrentPosition());

        imu.zeroYaw();
        colorSensorLed.setState(true);
		
		t.start();
	}
	
	public void loop() {
		
		// Capture
		
		double leftPower = vDriveLeftMotor.getPower();
		double rightPower = vDriveRightMotor.getPower();
        double tapeMeasureLeftPower = vTapeMeasureLeftMotor.getPower();
        double tapeMeasureRightPower = vTapeMeasureRightMotor.getPower();
		
		// Update

		vTiltSensor.setRawValue(imu.getIntegratedPitch());
        vHeadingSensor.setRawValue(imu.getIntegratedYaw());
        vColorSensor.setRawValue(colorSensor.argb());
        vUltrasoundSensor.setRawValue(ultrasound.getValue());
		
		vDriveLeftMotorEncoder.setRawValue(-leftFront.getCurrentPosition());
		vDriveRightMotorEncoder.setRawValue(-rightFront.getCurrentPosition());
        vTapeMeasureLeftMotorEncoder.setRawValue(-tapeMeasureLeftMotor.getCurrentPosition());
        vTapeMeasureRightMotorEncoder.setRawValue(-tapeMeasureRightMotor.getCurrentPosition());

        try {
            vJoystickController1.copyStates(gamepad1);
            vJoystickController2.copyStates(gamepad2);
        } catch (RobotCoreException e) {
            e.printStackTrace();
        }

        // Copy State
		
		leftFront.setPower(leftPower);
		leftBack.setPower(leftPower);
		
		rightFront.setPower(rightPower);
		rightBack.setPower(rightPower);

        tapeMeasureLeftMotor.setPower(tapeMeasureLeftPower);
        tapeMeasureRightMotor.setPower(tapeMeasureRightPower);

        flipperLeft.setPosition(vFlipperLeftServo.getPosition());
        flipperRight.setPosition(vFlipperRightServo.getPosition());
        tapeMeasureLeft.setPosition(vTapeMeasureLeftServo.getPosition());
        tapeMeasureRight.setPosition(vTapeMeasureRightServo.getPosition());
		dumper.setPosition(vDumperServo.getPosition());
		backShieldLeft.setPosition(vBackShieldServo.getPosition());
        backShieldRight.setPosition(vBackShieldServo.getPosition());
		snowPlowLeft.setPosition(vSnowPlowServo.getPosition());
        snowPlowRight.setPosition(vSnowPlowServo.getPosition());
        buttonPusher.setPosition(vButtonPusherServo.getPosition());

		telemetry.addData("le joystick", vJoystickController2.getValue(JoystickController.Y_1));

		//telemetry.addData("leftRawEncoder", Double.toString(leftFront.getCurrentPosition()));
		//telemetry.addData("rightRawEncoder", Double.toString(rightFront.getCurrentPosition()));
		//telemetry.addData("leftEncoder", Double.toString(vDriveLeftMotorEncoder.getRawValue()));
		//telemetry.addData("rightEncoder", Double.toString(vDriveRightMotorEncoder.getRawValue()));
        //telemetry.addData("color", String.format("%06x", (int) vColorSensor.getRawValue()));
		//telemetry.addData("ultrasound", Double.toString(vUltrasonicSensor.getValue()));
		//telemetry.addData("rightTapeMeasurePosition", Double.toString(tapeMeasureRight.getPosition()));
		//telemetry.addData("leftTapeMeasurePosition", Double.toString(tapeMeasureLeft.getPosition()));

		//telemetry.addData("angleSensor", Double.toString(vHeadingSensor.getValue()));
	}
	
	public void stop() {
		t.interrupt();
	}

	public abstract void setLogicThread();

}
