package virtualRobot;

import java.util.ArrayList;

import virtualRobot.components.ColorSensor;
import virtualRobot.components.LocationSensor;
import virtualRobot.components.Motor;
import virtualRobot.components.Sensor;
import virtualRobot.components.Servo;

/**
 * Created by Yanjun on 11/18/2015.
 */
public interface AutonomousRobot {

    Motor getDriveRightMotor();

    Motor getDriveLeftMotor();

    Motor getReaperMotor();

    Motor getLiftMotor();

    Motor getTapeMeasureMotor();

    Sensor getDriveRightMotorEncoder();

    Sensor getDriveLeftMotorEncoder();

    Sensor getTapeMeasureMotorEncoder();

    Sensor getLiftMotorEncoder();

    Sensor getHeadingSensor();

    Sensor getPitchSensor();

    Sensor getRollSensor();

    Sensor getUltrasoundSensor1();

    Sensor getUltrasoundSensor2();

    Sensor getUltrasoundSensor3();

    ColorSensor getColorSensor();

    LocationSensor getLocationSensor();

    Servo getFlipperLeftServo();

    Servo getFlipperRightServo();

    Servo getBackShieldServo();

    Servo getTapeMeasureServo();

    Servo getDumperServo();

    Servo getBasketServo();

    Servo getGateServo();

    Servo getScoopServo();

    void addToProgress(String s);

    ArrayList<String> getProgress();
}
