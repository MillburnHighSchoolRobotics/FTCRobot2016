package virtualRobot;

/**
 * Created by DOSullivan on 10/5/15.
 * Can be accesed by
 */
public class AutonomousRobot {

    private Motor rightMotor, leftMotor;
    private Sensor rightMotorEncoder, leftMotorEncoder;
    private Sensor angleSensor;

    public AutonomousRobot(Motor rightMotor, Motor leftMotor,
                           Sensor rightMotorEncoder, Sensor leftMotorEncoder,
                           Sensor angleSensor) {
        this.rightMotor = rightMotor;
        this.leftMotor = leftMotor;
        this.rightMotorEncoder = rightMotorEncoder;
        this.leftMotorEncoder = leftMotorEncoder;
        this.angleSensor = angleSensor;

    }

    public Motor getRightMotor () {
        return rightMotor;
    }

    public Motor getLeftMotor () {
        return leftMotor;
    }

    public Sensor getRightMotorEncoder() {
        return rightMotorEncoder;
    }

    public Sensor getLeftMotorEncoder() {
        return leftMotorEncoder;
    }

    public Sensor getAngleSensor() {
        return angleSensor;
    }
}
