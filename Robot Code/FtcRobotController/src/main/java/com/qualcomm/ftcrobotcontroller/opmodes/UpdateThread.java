
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;

public class UpdateThread extends OpMode {
	
	private AutonomousRobot robot;
	private LogicThread logicThread;
	private Thread t;
	
	private DcMotor rightTop, rightBottom, leftTop, leftBottom;
	private GyroSensor gyro;
	
	private Motor leftMotor, rightMotor;
	private Sensor leftMotorEncoder, rightMotorEncoder, angleSensor;
	
	double curTime, prevTime, curRot, prevRot, gyroOffset;
	
	@Override
	public void init() {
		rightTop = hardwareMap.dcMotor.get("rightTop");
		rightBottom = hardwareMap.dcMotor.get("rightBottom");
		leftTop = hardwareMap.dcMotor.get("leftTop");
		leftBottom = hardwareMap.dcMotor.get("leftBottom");
		
		gyroSensor = hardwareMap.gyroSensor.get("gyro");
				
		robot = new AutonomousRobot();
		
		Command.robot = robot;
		
		leftMotor = robot.getLeftMotor();
		rightMotor = robot.getRightMotor();
		leftMotorEncoder = robot.getLeftMotorEncoder();
		rightMotorEncoder = robot.getRightMotorEncoder();
		angleSensor = robot.getAngleSensor();
		
		logicThread = new LogicThread();
		t = new Thread(logicThread);
		
		gyroOffset = gyro.getRotation();
	}
	
	public void start() {
		
		curTime = System.currentTimeMillis();
		prevTime = System.currentTimeMillis();
		
		curRot = gyro.getRotation();
		prevRot = gyro.getRotation();
		
		angleSensor.setRawValue(0);
		
		leftMotorEncoder.setRawValue(leftTop.getCurrentPosition());
		rightMotorEncoder.setRawValue(rightTop.getCurrentPosition());
		
		t.start();
	}
	
	public void loop() {
		
		// Capture
		
		double leftPower = leftMotor.getPower();
		double rightPower = rightMotor.getPower();
		
		// Update
		
		curTime = System.currentTimeMillis();
		curRot = gyro.getRotation();
		
		double delta = (curRot + prevRot) * 0.5 * (curTime - prevTime) * 0.001;
		angleSensor.setRawValue(angleSensor.getRawValue() + delta);
		
		leftMotorEncoder.setRawValue(leftTop.getCurrentPosition());
		rightMotorEncoder.setRawValue(rightTop.getCurrentPosition());
		
		// Copy State
		
		leftTop.setPower(leftPower);
		leftBottom.setPower(leftPower);
		
		rightTop.setPower(rightPower);
		rightBottom.setPower(rightPower);
	}
	
	public void stop() {
		t.interrupt();
	}
}
