package virtualRobot;

/**
 * Created by DOSullivan on 10/5/15.
 * Can be accesed by
 */
public class AutonomousRobot {

    private Motor rightMotor, leftMotor;
    private Sensor rightMotorEncoder, leftMotorEncoder;
    private Sensor angleSensor;
    
    public AutonomousRobot() {
    	rightMotor = new Motor();
    	leftMotor = new Motor();
    	rightMotorEncoder = new Sensor();
    	leftMotorEncoder = new Sensor();
    	angleSensor = new Sensor();
    }

    public AutonomousRobot(Motor rightMotor, Motor leftMotor,
                           Sensor rightMotorEncoder, Sensor leftMotorEncoder,
                           Sensor angleSensor) {
        this.rightMotor = rightMotor;
        this.leftMotor = leftMotor;
        this.rightMotorEncoder = rightMotorEncoder;
        this.leftMotorEncoder = leftMotorEncoder;
        this.angleSensor = angleSensor;


        public
        AutonomousRobot(List < motor > motors, List < Servo > servos, List < Sensor > sensors) {
            this.motors = motors;
            this.servos = servos;
            for (Sensor sensor : this.sensors = sensors) {

            }
            ;

            class AutonomousRotate extends Rotate {
            }

        public Motor getRightMotor () {
            return rightMotor;
        }

        public Motor getLeftMotor () {
            return leftMotor;
        }

        public Sensor getRightMotorEncoder () {
            return rightMotorEncoder;
        }

        public Sensor getLeftMotorEncoder () {
            return leftMotorEncoder;
        }

        public Sensor getAngleSensor () {
            return angleSensor;
        }

        }

        public AutonomousRobot(AutonomousRobot robot) {
            rightMotor = robot.getRightMotor();
            leftMotor = robot.getLeftMotor();
            rightMotorEncoder = robot.getRightMotorEncoder();
            leftMotorEncoder = robot.getLeftMotorEncoder();
            angleSensor = robot.getAngleSensor();
        }

    public synchronized Motor getRightMotor() {
        return rightMotor;
    }

    public synchronized Motor getLeftMotor() {
        return leftMotor;
    }

    public synchronized Sensor getRightMotorEncoder() {
        return rightMotorEncoder;
    }

    public synchronized Sensor getLeftMotorEncoder() {
        return leftMotorEncoder;
    }

    public synchronized Sensor getAngleSensor() {
        return angleSensor;
    }
}
