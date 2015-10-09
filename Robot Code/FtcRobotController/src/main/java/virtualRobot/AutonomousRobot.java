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

}
}
