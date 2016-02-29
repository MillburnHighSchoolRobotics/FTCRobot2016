package virtualRobot;

import java.util.ArrayList;

import virtualRobot.components.ColorSensor;
import virtualRobot.components.LocationSensor;
import virtualRobot.components.Motor;
import virtualRobot.components.Sensor;
import virtualRobot.components.Servo;

/**
 * Created by DOSullivan on 10/5/15.
 * Can be accesed by
 */
public class SallyJoeBot implements AutonomousRobot, TeleopRobot {

    private Motor driveLeftMotor, driveRightMotor, reaperMotor, liftRightMotor, liftLeftMotor, tapeMeasureMotor;
    private Sensor driveLeftMotorEncoder, driveRightMotorEncoder, tapeMeasureMotorEncoder, liftRightMotorEncoder, liftLeftMotorEncoder;
    private Sensor headingSensor, pitchSensor, rollSensor, ultrasoundSensor1, ultrasoundSensor2, ultrasoundSensor3;
    private ColorSensor colorSensor;
    private Servo tapeMeasureServo, flipperLeftServo, flipperRightServo, backShieldServo, dumperServo, basketServo, gateServo, scoopServo;

    private Servo permaHang;

    private LocationSensor locationSensor;
    private JoystickController joystickController1, joystickController2;

    private ArrayList<String> robotProgress;


    public SallyJoeBot() {
        driveLeftMotor = new Motor();
        driveRightMotor = new Motor();
        reaperMotor = new Motor();
        liftRightMotor = new Motor();
        liftLeftMotor = new Motor();
        tapeMeasureMotor = new Motor();

        driveLeftMotorEncoder = new Sensor();
        driveRightMotorEncoder = new Sensor();
        tapeMeasureMotorEncoder = new Sensor();
        liftLeftMotorEncoder = new Sensor();
        liftRightMotorEncoder = new Sensor();

        headingSensor = new Sensor();
        colorSensor = new ColorSensor();
        pitchSensor = new Sensor();
        rollSensor = new Sensor();
        ultrasoundSensor1 = new Sensor();
        ultrasoundSensor2 = new Sensor();
        ultrasoundSensor3 = new Sensor();
        
        tapeMeasureServo = new Servo();
        flipperLeftServo = new Servo();
        flipperRightServo = new Servo();
        backShieldServo = new Servo();
        backShieldServo = new Servo();
        dumperServo = new Servo();
        basketServo = new Servo();
        gateServo = new Servo();
        scoopServo = new Servo();

        permaHang = new Servo();

        locationSensor = new LocationSensor();

        joystickController1 = new JoystickController();
        joystickController2 = new JoystickController();

        robotProgress = new ArrayList<String>();

        tapeMeasureServo.setPosition(0.3);
        //scoopServo.setPosition(0.75);
    }

    @Override
    public synchronized Motor getDriveLeftMotor() {
        return driveLeftMotor;
    }

    @Override
    public synchronized Motor getDriveRightMotor() {
        return driveRightMotor;
    }

    public synchronized Motor getReaperMotor() {
        return reaperMotor;
    }

    public synchronized Motor getLiftLeftMotor() {
        return liftLeftMotor;
    }

    public synchronized Motor getLiftRightMotor() {
        return liftRightMotor;
    }

    public synchronized Motor getTapeMeasureMotor() {
        return tapeMeasureMotor;
    }

    @Override
    public synchronized Sensor getDriveLeftMotorEncoder() {
        return driveLeftMotorEncoder;
    }

    @Override
    public synchronized Sensor getDriveRightMotorEncoder() {
        return driveRightMotorEncoder;
    }

    public synchronized Sensor getTapeMeasureMotorEncoder() {
        return tapeMeasureMotorEncoder;
    }

    public synchronized Sensor getLiftRightMotorEncoder() {
        return liftRightMotorEncoder;
    }

    public synchronized Sensor getLiftLeftMotorEncoder() {
        return liftLeftMotorEncoder;
    }

    @Override
    public synchronized Sensor getHeadingSensor() {
        return headingSensor;
    }

    @Override
    public synchronized Sensor getPitchSensor() {
        return pitchSensor;
    }

    @Override
    public synchronized Sensor getRollSensor() {
        return rollSensor;
    }

    public synchronized Sensor getUltrasoundSensor1() {
        return ultrasoundSensor1;
    }

    public synchronized Sensor getUltrasoundSensor2() {
        return ultrasoundSensor2;
    }

    public synchronized Sensor getUltrasoundSensor3() {
        return ultrasoundSensor3;
    }

    @Override
    public synchronized ColorSensor getColorSensor() {
        return colorSensor;
    }

    @Override
    public synchronized Servo getTapeMeasureServo() {
        return tapeMeasureServo;
    }

    @Override
    public synchronized Servo getFlipperLeftServo() {
        return flipperLeftServo;
    }

    @Override
    public synchronized Servo getFlipperRightServo() {
        return flipperRightServo;
    }

    @Override
    public synchronized Servo getBackShieldServo() {
        return backShieldServo;
    }

    @Override
    public synchronized Servo getDumperServo() {
        return dumperServo;
    }

    public synchronized Servo getBasketServo() {
        return basketServo;
    }

    public synchronized Servo getGateServo() {
        return gateServo;
    }

    public synchronized Servo getScoopServo() {
        return scoopServo;
    }

    public synchronized LocationSensor getLocationSensor() {
        return locationSensor;
    }

    public synchronized JoystickController getJoystickController1() {
        return joystickController1;
    }

    public synchronized JoystickController getJoystickController2() {
        return joystickController2;
    }

    public synchronized void addToProgress (String s) {
        robotProgress.add(s);
    }

    public synchronized ArrayList<String> getProgress () {
        return robotProgress;
    }

    public synchronized Servo getPermaHang() {
        return permaHang;
    }


}
