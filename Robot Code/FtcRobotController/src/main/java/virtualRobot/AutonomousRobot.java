package virtualRobot;

/**
 * Created by DOSullivan on 10/5/15.
 * Can be accesed by
 */
public class AutonomousRobot {

    private Motor driveLeftMotor, driveRightMotor, armLeftMotor, armRightMotor, reaperMotor;
    private Sensor driveLeftMotorEncoder, driveRightMotorEncoder, armLeftMotorEncoder, armRightMotorEncoder;
    private Sensor angleSensor, colorSensor, tiltSensor, ultrasoundSensor;

    private Servo armLeftServo, armRightServo, gateLeftServo, gateRightServo;
    private ContinuousRotationServo spinnerServo;

    private JoystickController joystickController;

    public AutonomousRobot() {
        driveLeftMotor = new Motor();
        driveRightMotor = new Motor();
        armLeftMotor = new Motor();
        armRightMotor = new Motor();
        reaperMotor = new Motor();

        driveLeftMotorEncoder = new Sensor();
        driveRightMotorEncoder = new Sensor();
        armLeftMotorEncoder = new Sensor();
        armRightMotorEncoder = new Sensor();

        angleSensor = new Sensor();
        colorSensor = new Sensor();
        tiltSensor = new Sensor();
        ultrasoundSensor = new Sensor();

        armLeftServo = new Servo();
        armRightServo = new Servo();
        gateLeftServo = new Servo();
        gateRightServo = new Servo();

        spinnerServo = new ContinuousRotationServo();

        joystickController = new JoystickController();
    }

    public synchronized Motor getDriveRightMotor() {
        return driveRightMotor;
    }

    public synchronized Motor getDriveLeftMotor() {
        return driveLeftMotor;
    }

    public synchronized Motor getArmLeftMotor() {
        return armLeftMotor;
    }

    public synchronized Motor getArmRightMotor() {
        return armRightMotor;
    }

    public synchronized Motor getReaperMotor() {
        return reaperMotor;
    }

    public synchronized Sensor getDriveRightMotorEncoder() {
        return driveRightMotorEncoder;
    }

    public synchronized Sensor getDriveLeftMotorEncoder() {
        return driveLeftMotorEncoder;
    }

    public synchronized Sensor getArmLeftMotorEncoder() {
        return armLeftMotorEncoder;
    }

    public synchronized Sensor getArmRightMotorEncoder() {
        return armRightMotorEncoder;
    }

    public synchronized Sensor getAngleSensor() {
        return angleSensor;
    }

    public synchronized Sensor getColorSensor() {
        return colorSensor;
    }

    public synchronized Sensor getTiltSensor() {
        return tiltSensor;
    }

    public synchronized Sensor getUltrasoundSensor() {
        return ultrasoundSensor;
    }

    public synchronized Servo getArmLeftServo() {
        return armLeftServo;
    }

    public synchronized Servo getArmRightServo() {
        return armRightServo;
    }

    public synchronized Servo getGateLeftServo() {
        return gateLeftServo;
    }

    public synchronized Servo getGateRightServo() {
        return gateRightServo;
    }

    public synchronized ContinuousRotationServo getSpinnerServo() {
        return spinnerServo;
    }

    public synchronized JoystickController getJoystickController() {
        return joystickController;
    }
}
