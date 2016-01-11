package virtualRobot.godThreads;

import java.util.concurrent.atomic.AtomicBoolean;

import virtualRobot.GodThread;
import virtualRobot.MonitorThread;
import virtualRobot.monitorThreads.DebrisMonitor;
import virtualRobot.monitorThreads.TimeMonitor;

/**
 * Created by shant on 1/10/2016.
 */
public class RedAutoGodThread extends GodThread {
    @Override
    public void realRun() throws InterruptedException {
        AtomicBoolean redisLeft = new AtomicBoolean();

        //These two threads should be running from the beginning of the program to provide accurate data
        MonitorThread watchingForDebris = new DebrisMonitor();
        Thread dm = new Thread(watchingForDebris);
        children.add(dm);

        MonitorThread watchingForTime = new TimeMonitor(System.currentTimeMillis(), 30000);
        Thread tm = new Thread(watchingForTime);
        children.add(tm);


        //@TODO ADD RED AUTONOMOUS HERE

    }
}
