package virtualRobot;
import java.util.*;
/**
 * Created by DOSullivan on 10/5/15.
 * Can be accesed by
 */
public class AutonomousRobot {

    public List<Motor> motors = new ArrayList<Motor>();
    public List<Servo> servos = new ArrayList<Servo>();
    public List<Sensor> sensors = new ArrayList<Sensor>();
<<<<<<< HEAD
public AutonomousRobot(List<motor> motors, List<Servo> servos, List<Sensor> sensors) {
this.motors = motors;
    this.servos = servos;
    this.sensors = sensors;
=======

    /* DriveTrain Motors */
    public static Motor[] driveMotors = {new Motor(), new Motor(), new Motor(), new Motor()};
    //in this array, 0 = rightTop, 1 = rightBottom, 2 = leftTop, 3 = leftBottom

    public static Sensor[] driveMotorEncoders = {new Sensor(), new Sensor(), new Sensor(), new Sensor()};

    /*Other Motors*/

    /*Define Other Servos Here */




    public AutonomousRobot(List<Motor> motors, List<Servo> servos, List<Sensor> sensors) {
        this.motors = motors;
        this.servos = servos;
        this.sensors = sensors;
>>>>>>> bae0c566d25540dddae9abeb92a4cb3a123fd744
    }
}
