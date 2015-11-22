package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

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
	
	private DcMotor rightTop, rightBottom, leftTop, leftBottom, armLeftMotor, armRightMotor, reaper, conveyor;
	private Servo armLeft, armRight, gateLeft, gateRight, spinner, blockerLeft, blockerRight, rampLift;
	//private GyroSensor gyro;
    //private ColorSensor colorSensor;
	//private UltrasonicSensor ultrasonicSensor;
	
	private Motor vDriveLeftMotor, vDriveRightMotor, vArmLeftMotor, vArmRightMotor, vReaperMotor, vConveyorMotor;
	private virtualRobot.Servo vArmLeftServo, vArmRightServo, vGateLeftServo, vGateRightServo, vBlockerLeftServo, vBlockerRightServo, vRampLift;
    //private ContinuousRotationServo vSpinnerServo;
	private Sensor vDriveLeftMotorEncoder, vDriveRightMotorEncoder, vArmLeftMotorEncoder, vArmRightMotorEncoder; //vAngleSensor, vColorSensor, vUltrasonicSensor;

	private JoystickController vJoystickController1, vJoystickController2;
	double curTime, prevTime, curRot, prevRot, gyroOffset;

	private ArrayList<Integer> gyroReadings;
	
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
        conveyor = hardwareMap.dcMotor.get("conveyor");


        //SERVO SETUP
        armLeft = hardwareMap.servo.get("armLeft");
        armRight = hardwareMap.servo.get("armRight");
        gateLeft = hardwareMap.servo.get("gateLeft");
        gateRight = hardwareMap.servo.get("gateRight");
        //spinner = hardwareMap.servo.get("spinner");
		blockerLeft = hardwareMap.servo.get("blockerLeft");
		blockerRight = hardwareMap.servo.get("blockerRight");
		rampLift = hardwareMap.servo.get("rampLift");

        //REVERSE RIGHT SIDE
        blockerRight.setDirection(Servo.Direction.REVERSE);
        gateRight.setDirection(Servo.Direction.REVERSE);
		rightTop.setDirection(DcMotor.Direction.REVERSE);
		rightBottom.setDirection(DcMotor.Direction.REVERSE);
        armRight.setDirection(Servo.Direction.REVERSE);
		armRightMotor.setDirection(DcMotor.Direction.REVERSE);

        //SENSOR SETUP
		/*gyro = hardwareMap.gyroSensor.get("gyro");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
		ultrasonicSensor = hardwareMap.ultrasonicSensor.get("ultrasonic");
*/
        //FETCH VIRTUAL ROBOT FROM COMMAND INTERFACE
		robot = Command.ROBOT;

        //FETCH VIRTUAL COMPONENTS OF VIRTUAL ROBOT
        vDriveLeftMotor = robot.getDriveLeftMotor();
        vDriveRightMotor = robot.getDriveRightMotor();
        vArmLeftMotor = robot.getArmLeftMotor();
        vArmRightMotor = robot.getArmRightMotor();
        vReaperMotor = robot.getReaperMotor();
        vConveyorMotor = robot.getConveyorMotor();

        vDriveLeftMotorEncoder = robot.getDriveLeftMotorEncoder();
        vDriveRightMotorEncoder = robot.getDriveRightMotorEncoder();
        vArmLeftMotorEncoder = robot.getArmLeftMotorEncoder();
        vArmRightMotorEncoder = robot.getArmRightMotorEncoder();
        //vAngleSensor = robot.getAngleSensor();
		//vColorSensor = robot.getColorSensor();

        vArmLeftServo = robot.getArmLeftServo();
        vArmRightServo = robot.getArmRightServo();
        vGateLeftServo = robot.getGateLeftServo();
        vGateRightServo = robot.getGateRightServo();
        //vSpinnerServo = robot.getSpinnerServo();
		vBlockerLeftServo = robot.getBlockerLeftServo();
		vBlockerRightServo = robot.getBlockerRightServo();
		vRampLift = robot.getRampLift();

		//vUltrasonicSensor = robot.getUltrasoundSensor();

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

		gyroReadings = new ArrayList<Integer>();
	}

	public void init_loop() {
		//gyroReadings.add((int)gyro.getRotation());
	}
	
	public void start() {

		/*Collections.sort(gyroReadings);
		gyroOffset = gyroReadings.get(gyroReadings.size() / 2);
		
		curTime = System.currentTimeMillis();
		prevTime = System.currentTimeMillis();
		
		curRot = gyro.getRotation()-gyroOffset;
		prevRot = gyro.getRotation()-gyroOffset;
		
		vAngleSensor.setRawValue(0);
		*/
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
        double conveyorPower = vConveyorMotor.getPower();

		//telemetry.addData("conveyorPower", Double.toString(conveyorPower));
		
		// Update
		
		curTime = System.currentTimeMillis();

		/*curRot = gyro.getRotation()-gyroOffset;

		double delta = (curRot + prevRot) * 0.5 * (curTime - prevTime) * 0.001;
		if (Math.abs(curRot) < 2) {
			delta = 0;
		}
		vAngleSensor.incrementRawValue(delta);

        vColorSensor.setRawValue(colorSensor.argb());

		vUltrasonicSensor.setRawValue(ultrasonicSensor.getUltrasonicLevel());
		telemetry.addData("le gyro", vAngleSensor.getValue());
		telemetry.addData("le avg gyro", (curRot + prevRot) * 0.5);
		telemetry.addData("le time", curTime - prevTime);
		telemetry.addData("le delta", delta);
*/
		
		vDriveLeftMotorEncoder.setRawValue(-leftTop.getCurrentPosition());
		vDriveRightMotorEncoder.setRawValue(-rightTop.getCurrentPosition());
        vArmLeftMotorEncoder.setRawValue(-armLeftMotor.getCurrentPosition());
        vArmRightMotorEncoder.setRawValue(-armRightMotor.getCurrentPosition());

        try {
            vJoystickController1.copyStates(gamepad1);
            vJoystickController2.copyStates(gamepad2);
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
        conveyor.setPower(conveyorPower);

        gateLeft.setPosition(vGateLeftServo.getPosition());
        gateRight.setPosition(vGateRightServo.getPosition());
        armLeft.setPosition(vArmLeftServo.getPosition());
        armRight.setPosition(vArmRightServo.getPosition());
        //spinner.setPosition(vSpinnerServo.getPosition());
		blockerLeft.setPosition(vBlockerLeftServo.getPosition());
		blockerRight.setPosition(vBlockerRightServo.getPosition());
		rampLift.setPosition(vRampLift.getPosition());

		telemetry.addData("le joystick", vJoystickController2.getValue(JoystickController.Y_1));

		//telemetry.addData("leftRawEncoder", Double.toString(leftTop.getCurrentPosition()));
		//telemetry.addData("rightRawEncoder", Double.toString(rightTop.getCurrentPosition()));
		//telemetry.addData("leftEncoder", Double.toString(vDriveLeftMotorEncoder.getRawValue()));
		//telemetry.addData("rightEncoder", Double.toString(vDriveRightMotorEncoder.getRawValue()));
        //telemetry.addData("color", String.format("%06x", (int) vColorSensor.getRawValue()));
		//telemetry.addData("ultrasound", Double.toString(vUltrasonicSensor.getValue()));
		//telemetry.addData("rightArmPosition", Double.toString(armRight.getPosition()));
		//telemetry.addData("leftArmPosition", Double.toString(armLeft.getPosition()));

		//telemetry.addData("angleSensor", Double.toString(vAngleSensor.getValue()));

		prevTime = curTime;
		prevRot = curRot;
	}
	
	public void stop() {
		t.interrupt();
	}

	public abstract void setLogicThread();

}
