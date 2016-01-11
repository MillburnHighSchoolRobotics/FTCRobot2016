package virtualRobot.monitorThreads;

import virtualRobot.AutonomousRobot;
import virtualRobot.MonitorThread;

/**
 * Created by shant on 1/10/2016.
 */
public class DebrisMonitor extends MonitorThread<AutonomousRobot> {


    @Override
    public boolean setStatus() {
        if (robot.getRollSensor().getValue() > 5) {
            return false;
        }
        return true;
    }

}
