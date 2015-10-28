package virtualRobot;
import java.util.*;
/**
 * Created by DOSullivan on 10/5/15.
 * Can be accesed by
 */
public class AutonomousRobot {

    public List<motor> motors = new ArrayList<motor>();
    public List<Servo> servos = new ArrayList<Servo>();
    public List<Sensor> sensors = new ArrayList<Sensor>();

public AutonomousRobot(List<motor> motors, List<Servo> servos, List<Sensor> sensors) {
    this.motors = motors;
    this.servos = servos;
    this.sensors = sensors;


    /* DriveTrain Motors */
    public static motor[] driveMotors = {new motor(), new motor()};
    //in the updated driveMotor array 0 = RightMotor and 1 = LeftMotor
    //from what I have gathered the general consensus is that we will be grouping the drive train motors
    // the original code is commented below
    /*
    //public static motor[] driveMotors = {new motor(), new motor(), new motor(), new motor()};
    //in this array, 0 = rightTop, 1 = rightBottom, 2 = leftTop, 3 = leftBottom
    */
    public static MoveServo[] servo = {new servo(), new servo()};
    // in the array, 0 = ZiplineServo, 1 = BeaconPressServo
    public static Sensor[] driveMotorEncoders = {new Sensor(), new Sensor(), new Sensor(), new Sensor()};

    /*Other Motors*/

    /*Define Other Servos Here */
    //if the array is unnecessary then we can just use these
    /*servos ServoBeacon;
    servos ServoClimbers;*/




    public AutonomousRobot(List<motor> motors, List<Servo> servos, List<Sensor> sensors) {
        this.motors = motors;
        this.servos = servos;
        this.sensors = sensors;

    public class AutonomousRotate extends Rotate{

    }
}}}
