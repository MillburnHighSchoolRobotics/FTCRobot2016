package com.qualcomm.ftcrobotcontroller.opmodes;

import com.kauailabs.navx.ftc.MPU9250;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import java.util.ArrayList;

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

	//drive and shields
	private DcMotor rightFront, rightBack, leftFront, leftBack;
	private Servo frontShieldRight, frontShieldLeft, backShieldLeft, backShieldRight;


	//tape measure system
	private DcMotor tapeMeasureFrontM, tapeMeasureBackMotor;
	private Servo tapeMeasureLeft, tapeMeasureRight;
	private Servo hangRight, hangLeft;

	//misc
	private Servo flipperLeft, flipperRight;
	private Servo dumper, buttonPusher;

	private MPU9250 imu;
	private UltrasonicSensor ultrasonicSensor;
	private ColorSensor colorSensor;
	
	private Motor vDriveLeftMotor, vDriveRightMotor, vTapeMeasureBackMotor, vTapeMeasureFrontMotor;
	private virtualRobot.Servo vTapeMeasureServo, vFlipperLeftServo, vFlipperRightServo, vDumperServo, vBackShieldServo, vFrontShieldServo, vButtonPusherServo, vHangServo;
	private Sensor vDriveLeftMotorEncoder, vDriveRightMotorEncoder, vTapeMeasureBackMotorEncoder, vTapeMeasureFrontMotorEncoder, vHeadingSensor, vUltrasoundSensor, vTiltSensor;

	private virtualRobot.ColorSensor vColorSensor;
	private JoystickController vJoystickController1, vJoystickController2;

	private ArrayList<String> robotProgress;
	
	@Override
	public void init() {
        //MOTOR SETUP
		rightFront = hardwareMap.dcMotor.get("rightFront");
		rightBack = hardwareMap.dcMotor.get("rightBack");
		leftFront = hardwareMap.dcMotor.get("leftFront");
		leftBack = hardwareMap.dcMotor.get("leftBack");
        tapeMeasureFrontM = hardwareMap.dcMotor.get("tapeMeasureFrontM");
        tapeMeasureBackMotor = hardwareMap.dcMotor.get("tapeMeasureBackMotor");

        //SERVO SETUP
        tapeMeasureLeft = hardwareMap.servo.get("tapeMeasureLeft");
        tapeMeasureRight = hardwareMap.servo.get("tapeMeasureRight");
        flipperLeft = hardwareMap.servo.get("flipperLeft");
        flipperRight = hardwareMap.servo.get("flipperRight");
		dumper = hardwareMap.servo.get("dumper");
		backShieldLeft = hardwareMap.servo.get("backShieldLeft");
        backShieldRight = hardwareMap.servo.get("backShieldRight");
		frontShieldRight = hardwareMap.servo.get("frontShieldRight");
		frontShieldLeft = hardwareMap.servo.get("frontShieldLeft");
		buttonPusher = hardwareMap.servo.get("buttonPusher");
		hangLeft = hardwareMap.servo.get("leftHang");
		hangRight = hardwareMap.servo.get("rightHang");

        //REVERSE RIGHT SIDE
        backShieldRight.setDirection(Servo.Direction.REVERSE);
        flipperRight.setDirection(Servo.Direction.REVERSE);
		rightFront.setDirection(DcMotor.Direction.REVERSE);
		rightBack.setDirection(DcMotor.Direction.REVERSE);
        tapeMeasureRight.setDirection(Servo.Direction.REVERSE);
		frontShieldRight.setDirection(Servo.Direction.REVERSE);
		hangRight.setDirection(Servo.Direction.REVERSE);


        //SENSOR SETUP
		imu = MPU9250.getInstance(hardwareMap.deviceInterfaceModule.get("dim"), 5);
		colorSensor = hardwareMap.colorSensor.get("color");
		ultrasonicSensor = hardwareMap.ultrasonicSensor.get("ultrasonic");

        //FETCH VIRTUAL ROBOT FROM COMMAND INTERFACE
		robot = Command.ROBOT;

        //FETCH VIRTUAL COMPONENTS OF VIRTUAL ROBOT
        vDriveLeftMotor = robot.getDriveLeftMotor();
        vDriveRightMotor = robot.getDriveRightMotor();
        vTapeMeasureFrontMotor = robot.getTapeMeasureFrontMotor();
        vTapeMeasureBackMotor = robot.getTapeMeasureBackMotor();

        vDriveLeftMotorEncoder = robot.getDriveLeftMotorEncoder();
        vDriveRightMotorEncoder = robot.getDriveRightMotorEncoder();
        vTapeMeasureFrontMotorEncoder = robot.getTapeMeasureFrontMotorEncoder();
        vTapeMeasureBackMotorEncoder = robot.getTapeMeasureBackMotorEncoder();
        vHeadingSensor = robot.getHeadingSensor();
		vColorSensor = robot.getColorSensor();
		vTiltSensor = robot.getTiltSensor();

        vTapeMeasureServo = robot.getTapeMeasureServo();
        vFlipperLeftServo = robot.getFlipperLeftServo();
        vFlipperRightServo = robot.getFlipperRightServo();
		vDumperServo = robot.getDumperServo();
		vBackShieldServo = robot.getBackShieldServo();
		vFrontShieldServo = robot.getFrontShieldServo();
		vButtonPusherServo = robot.getButtonPusherServo();
		vHangServo = robot.getHangServo();

		vUltrasoundSensor = robot.getUltrasoundSensor();

        vJoystickController1 = robot.getJoystickController1();
        vJoystickController2 = robot.getJoystickController2();

		robotProgress = new ArrayList<String>();

        setLogicThread();

		try {
			t = new Thread(logicThread.newInstance());
		} catch (InstantiationException e) {
			return;
		} catch (IllegalAccessException e) {
			return;
		}

	}

	public void init_loop() {
		imu.zeroYaw();
	}

	public void start() {

		vDriveLeftMotorEncoder.setRawValue((-leftFront.getCurrentPosition() + -leftBack.getCurrentPosition())/2);
		vDriveRightMotorEncoder.setRawValue((-rightFront.getCurrentPosition() + -rightBack.getCurrentPosition())/2);
        vTapeMeasureBackMotorEncoder.setRawValue(-tapeMeasureBackMotor.getCurrentPosition());
        vTapeMeasureFrontMotorEncoder.setRawValue(-tapeMeasureFrontM.getCurrentPosition());
		tapeMeasureLeft.setPosition(0.485);
		tapeMeasureRight.setPosition(0.485);
		
		t.start();
	}
	
	public void loop() {
		
		// Capture
		
		double leftPower = vDriveLeftMotor.getPower();
		double rightPower = vDriveRightMotor.getPower();
        double tapeMeasureFrontPower = vTapeMeasureFrontMotor.getPower();
        double tapeMeasureBackPower = vTapeMeasureBackMotor.getPower();
		
		// Update

		vTiltSensor.setRawValue(imu.getIntegratedPitch());
        vHeadingSensor.setRawValue(imu.getIntegratedYaw());
        vColorSensor.setRawValue(colorSensor.argb());
		if (ultrasonicSensor.getUltrasonicLevel() > 0) {
			vUltrasoundSensor.setRawValue(ultrasonicSensor.getUltrasonicLevel());
		}
		else { /*don't update*/ }
		
		vDriveLeftMotorEncoder.setRawValue(-leftFront.getCurrentPosition());
		vDriveRightMotorEncoder.setRawValue(-rightFront.getCurrentPosition());
		vTapeMeasureFrontMotorEncoder.setRawValue(-tapeMeasureFrontM.getCurrentPosition());
        vTapeMeasureBackMotorEncoder.setRawValue(-tapeMeasureBackMotor.getCurrentPosition());

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

       	tapeMeasureFrontM.setPower(tapeMeasureFrontPower);
		tapeMeasureBackMotor.setPower(tapeMeasureBackPower);


		telemetry.addData("tape Measure front", tapeMeasureFrontPower);
		telemetry.addData("tape measure back", tapeMeasureBackPower);
		telemetry.addData("angle", vHeadingSensor.getRawValue());
		telemetry.addData("aangle", imu.getIntegratedYaw());

		flipperLeft.setPosition(vFlipperLeftServo.getPosition());
		flipperRight.setPosition(vFlipperRightServo.getPosition());
        tapeMeasureLeft.setPosition(vTapeMeasureServo.getPosition());
        tapeMeasureRight.setPosition(vTapeMeasureServo.getPosition());
		dumper.setPosition(vDumperServo.getPosition());
		backShieldLeft.setPosition(vBackShieldServo.getPosition());
        backShieldRight.setPosition(vBackShieldServo.getPosition());
		frontShieldRight.setPosition(vFrontShieldServo.getPosition());
		frontShieldLeft.setPosition(vFrontShieldServo.getPosition());
		buttonPusher.setPosition(vButtonPusherServo.getPosition());
		hangLeft.setPosition(vHangServo.getPosition());
		hangRight.setPosition(vHangServo.getPosition());

		telemetry.addData("button pusher servo", vButtonPusherServo.getPosition());
		telemetry.addData("tape Measure front", tapeMeasureFrontPower);
		telemetry.addData("tape measure backj", tapeMeasureBackPower);
		telemetry.addData("raw angle", imu.getIntegratedYaw());
		telemetry.addData("virtual angle", vHeadingSensor.getRawValue());
		telemetry.addData("real right encoders", rightFront.getCurrentPosition() + "  " + rightBack.getCurrentPosition());
		telemetry.addData("real left encoders", Double.toString(leftFront.getCurrentPosition()) + "   " + Double.toString(leftBack.getCurrentPosition()));
		telemetry.addData("virtual encoders", vDriveRightMotorEncoder.getValue() + " " + vDriveLeftMotorEncoder.getValue());
		telemetry.addData("sonar value", vUltrasoundSensor.getValue());

		//telemetry.a

		for (int i = 0; i < robot.getProgress().size(); i++) {
			telemetry.addData("robot progress " + i, robot.getProgress().get(i));
		}

		//telemetry.addData("le joystick", vJoystickController2.getValue(JoystickController.Y_1));
		//telemetry.addData("servo Value", tapeMeasureLeft.getPosition());
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
		imu.close();
		t.interrupt();
	}

	public abstract void setLogicThread();

}
