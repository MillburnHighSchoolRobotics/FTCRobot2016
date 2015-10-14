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

    /* DriveTrain Motors */



    public AutonomousRobot(List<Motor> motors, List<Servo> servos, List<Sensor> sensors) {
        this.motors = motors;
        this.servos = servos;
        this.sensors = sensors;
    }
}
