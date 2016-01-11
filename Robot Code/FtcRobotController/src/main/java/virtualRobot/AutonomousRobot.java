package virtualRobot;

import java.util.ArrayList;

import virtualRobot.components.ColorSensor;
import virtualRobot.components.Motor;
import virtualRobot.components.Sensor;
import virtualRobot.components.Servo;

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

    Sensor getPitchSensor();

    Sensor getRollSensor();

    Sensor getUltrasoundSensor();

    Servo getFlipperLeftServo();

    Servo getFlipperRightServo();

    Servo getFrontShieldServo();

    Servo getBackShieldServo();

    Servo getTapeMeasureServo();

    Servo getDumperServo();

    Servo getButtonPusherServo();

    Servo getHangServo();

    void addToProgress(String s);

    ArrayList<String> getProgress();

    void addToCommands(String s);

    ArrayList<String> getCommands();
}
