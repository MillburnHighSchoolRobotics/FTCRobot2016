package virtualRobot;

import java.util.ArrayList;

/**
 * Created by Yanjun on 11/18/2015.
 */
public interface AutonomousRobot {

    Motor getDriveRightMotor();

    Motor getDriveLeftMotor();

    Motor getTapeMeasureFrontMotor();

    Motor getTapeMeasureBackMotor();

    Sensor getDriveRightMotorEncoder();

    Sensor getDriveLeftMotorEncoder();

    Sensor getTapeMeasureFrontMotorEncoder();

    Sensor getTapeMeasureBackMotorEncoder();

    Sensor getHeadingSensor();

    ColorSensor getColorSensor();

    Sensor getTiltSensor();

    Sensor getUltrasoundSensor();

    Servo getFlipperLeftServo();

    Servo getFlipperRightServo();

    Servo getFrontShieldServo();

    Servo getBackShieldServo();

    Servo getTapeMeasureServo();

    Servo getDumperServo();

    Servo getButtonPusherServo();

    void addToProgress(String s);

    ArrayList<String> getProgress();

    void addToCommands(String s);

    ArrayList<String> getCommands();
}
