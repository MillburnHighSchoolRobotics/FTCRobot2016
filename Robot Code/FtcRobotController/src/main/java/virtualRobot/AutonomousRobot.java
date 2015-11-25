package virtualRobot;

/**
 * Created by Yanjun on 11/18/2015.
 */
public interface AutonomousRobot {

    Motor getDriveRightMotor();

    Motor getDriveLeftMotor();

    Motor getTapeMeasureLeftMotor();

    Motor getTapeMeasureRightMotor();

    Sensor getDriveRightMotorEncoder();

    Sensor getDriveLeftMotorEncoder();

    Sensor getTapeMeasureLeftMotorEncoder();

    Sensor getTapeMeasureRightMotorEncoder();

    Sensor getHeadingSensor();

    Sensor getColorSensor();

    Sensor getTiltSensor();

    Sensor getUltrasoundSensor();

    Servo getFlipperLeftServo();

    Servo getFlipperRightServo();

    Servo getSnowPlowServo();

    Servo getBackShieldServo();

    Servo getTapeMeasureLeftServo();

    Servo getTapeMeasureRightServo();

    Servo getDumperServo();

    Servo getButtonPusherServo();
}
