package virtualRobot;

/**
 * Created by Yanjun on 11/18/2015.
 */
public interface AutonomousRobot {

    Motor getDriveRightMotor();

    Motor getDriveLeftMotor();

    Motor getArmLeftMotor();

    Motor getArmRightMotor();

    Motor getReaperMotor();

    Motor getConveyorMotor();

    Sensor getDriveRightMotorEncoder();

    Sensor getDriveLeftMotorEncoder();

    Sensor getArmLeftMotorEncoder();

    Sensor getArmRightMotorEncoder();

    Sensor getAngleSensor();

    Sensor getColorSensor();

    Sensor getTiltSensor();

    Sensor getUltrasoundSensor();

    Servo getArmLeftServo();

    Servo getArmRightServo();

    Servo getGateLeftServo();

    Servo getGateRightServo();

  /* ContinuousRotationServo getSpinnerServo() {
    return spinnerServo;
  }*/

    Servo getBlockerLeftServo();

    Servo getBlockerRightServo();

    Servo getRampLift();
}
