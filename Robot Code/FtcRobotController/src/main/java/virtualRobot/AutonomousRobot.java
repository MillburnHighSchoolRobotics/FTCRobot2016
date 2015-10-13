package virtualRobot;
import java.util.*;
/**
 * Created by DOSullivan on 10/5/15.
 * Can be accesed by
 */
public class AutonomousRobot {
    private List<motor> motors = new ArrayList<motor>();
    private List<Servo> servos = new ArrayList<Servo>();
    private List<Sensor> sensors = new ArrayList<Sensor>();
public AutonomousRobot(List<motor> motors, List<Servo> servos, List<Sensor> sensors) {
    this.motors = motors;
    this.servos = servos;
    this.sensors = sensors;

}
    public void readCommand(Command c) {
        c.changeRobotState();
    }
}
