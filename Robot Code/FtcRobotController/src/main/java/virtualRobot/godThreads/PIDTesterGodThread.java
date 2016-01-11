package virtualRobot.godThreads;

import virtualRobot.GodThread;
import virtualRobot.LogicThread;
import virtualRobot.MonitorThread;
import virtualRobot.logicThreads.PIDTester;
import virtualRobot.monitorThreads.TimeMonitor;

/**
 * Created by shant on 1/10/2016.
 */
public class PIDTesterGodThread extends GodThread {

    @Override
    public void realRun() throws InterruptedException {
        MonitorThread watchForTime = new TimeMonitor(System.currentTimeMillis(), -1);
        Thread tm = new Thread (watchForTime);
        children.add(tm);


        LogicThread PIDTest = new PIDTester();
        Thread pid = new Thread(PIDTest);
        children.add(pid);

        delegateMonitor(pid, new MonitorThread[]{watchForTime});
    }
}
