package virtualRobot.monitorThreads;

import virtualRobot.AutonomousRobot;
import virtualRobot.MonitorThread;

/**
 * Created by shant on 1/10/2016.
 */
public class DebrisMonitor extends MonitorThread<AutonomousRobot> {


    @Override
    public boolean setStatus() {
        double totalAngle = Math.sqrt(Math.pow(robot.getRollSensor().getValue(), 2) + Math.pow(robot.getPitchSensor().getValue(), 2));
        if (totalAngle > 5) {
            return false;
        }
        return true;
    }

}
