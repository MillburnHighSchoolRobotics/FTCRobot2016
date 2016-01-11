package virtualRobot.monitorThreads;

import virtualRobot.AutonomousRobot;
import virtualRobot.MonitorThread;

/**
 * Created by shant on 1/10/2016.
 */
public class TimeMonitor extends MonitorThread<AutonomousRobot> {
    private double startingTime;
    private double endTime;

    @Override
    public boolean setStatus() {
        if (System.currentTimeMillis() - startingTime < endTime) {
            return true;
        }
        return false;
    }

    public TimeMonitor (double startingTime, double endTime) {
        this.startingTime = startingTime;
        this.endTime = endTime;
    }
}
