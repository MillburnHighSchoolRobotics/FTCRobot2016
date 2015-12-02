package virtualRobot;

/**
 * Created by DOSullivan on 10/5/15.
 * Can be accesed by
 */
public class SallyJoeBot implements AutonomousRobot, TeleopRobot {

    private Motor driveLeftMotor, driveRightMotor, tapeMeasureLeftMotor, tapeMeasureRightMotor;
    private Sensor driveLeftMotorEncoder, driveRightMotorEncoder, tapeMeasureLeftMotorEncoder, tapeMeasureRightMotorEncoder;
    private Sensor headingSensor, colorSensor, tiltSensor, ultrasoundSensor;

    private Servo tapeMeasureLeftServo, tapeMeasureRightServo, flipperLeftServo, flipperRightServo, snowPlowServo, backShieldServo, dumperServo, buttonPusherServo;

    private JoystickController joystickController1, joystickController2;

    public SallyJoeBot() {
        driveLeftMotor = new Motor();
        driveRightMotor = new Motor();
        tapeMeasureLeftMotor = new Motor();
        tapeMeasureRightMotor = new Motor();

        driveLeftMotorEncoder = new Sensor();
        driveRightMotorEncoder = new Sensor();
        tapeMeasureLeftMotorEncoder = new Sensor();
        tapeMeasureRightMotorEncoder = new Sensor();

        headingSensor = new Sensor();
        colorSensor = new Sensor();
        tiltSensor = new Sensor();
        ultrasoundSensor = new Sensor();
        
        tapeMeasureLeftServo = new Servo();
        tapeMeasureRightServo = new Servo();
        flipperLeftServo = new Servo();
        flipperRightServo = new Servo();
        snowPlowServo = new Servo();
        backShieldServo = new Servo();
        dumperServo = new Servo();
        buttonPusherServo = new Servo();

        joystickController1 = new JoystickController();
        joystickController2 = new JoystickController();

        tapeMeasureLeftServo.setPosition(0.63);
        tapeMeasureRightServo.setPosition(0.63);
    }

    public synchronized Motor getDriveRightMotor() {
        return driveRightMotor;
    }

    public synchronized Motor getDriveLeftMotor() {
        return driveLeftMotor;
    }

    public synchronized Motor getTapeMeasureLeftMotor() {
        return tapeMeasureLeftMotor;
    }

    public synchronized Motor getTapeMeasureRightMotor() {
        return tapeMeasureRightMotor;
    }

    public synchronized Sensor getDriveRightMotorEncoder() {
        return driveRightMotorEncoder;
    }

    public synchronized Sensor getDriveLeftMotorEncoder() {
        return driveLeftMotorEncoder;
    }

    public synchronized Sensor getTapeMeasureLeftMotorEncoder() {
        return tapeMeasureLeftMotorEncoder;
    }

    public synchronized Sensor getTapeMeasureRightMotorEncoder() {
        return tapeMeasureRightMotorEncoder;
    }

    public synchronized Sensor getHeadingSensor() {
        return headingSensor;
    }

    public synchronized Sensor getColorSensor() {
        return colorSensor;
    }

    public synchronized Sensor getTiltSensor() {
        return tiltSensor;
    }

    public synchronized Sensor getUltrasoudSensor() {
        return ultrasoundSensor;
    }

    public synchronized Servo getTapeMeasureLeftServo() {
        return tapeMeasureLeftServo;
    }

    public synchronized Servo getTapeMeasureRightServo() {
        return tapeMeasureRightServo;
    }

    public synchronized Servo getFlipperLeftServo() {
        return flipperLeftServo;
    }

    public synchronized Servo getFlipperRightServo() {
        return flipperRightServo;
    }

    public synchronized Servo getSnowPlowServo() {
        return snowPlowServo;
    }

    public synchronized Servo getBackShieldServo() {
        return backShieldServo;
    }

    public synchronized Servo getDumperServo() {
        return dumperServo;
    }

    public synchronized Servo getButtonPusherServo() {
        return buttonPusherServo;
    }

    public synchronized JoystickController getJoystickController1() {
        return joystickController1;
    }

    public synchronized JoystickController getJoystickController2() {
        return joystickController2;
    }
}
