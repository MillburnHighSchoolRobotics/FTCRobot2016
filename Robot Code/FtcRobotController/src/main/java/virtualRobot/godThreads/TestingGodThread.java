package virtualRobot.godThreads;

import virtualRobot.GodThread;
import virtualRobot.LogicThread;
import virtualRobot.MonitorThread;
import virtualRobot.commands.Translate;
import virtualRobot.monitorThreads.DebrisMonitor;
import virtualRobot.monitorThreads.TimeMonitor;

/**
 * Created by shant on 1/15/2016.
 */
public class TestingGodThread extends GodThread {
    @Override
    public void realRun() throws InterruptedException {
        //These two threads should be running from the beginning of the program to provide accurate data
        MonitorThread watchingForDebris = new DebrisMonitor();
        Thread dm = new Thread(watchingForDebris);
        dm.start();
        children.add(dm);

        MonitorThread watchingForTime = new TimeMonitor(System.currentTimeMillis(), 30000);
        Thread tm = new Thread(watchingForTime);
        tm.start();
        children.add(tm);


        // THIS IS THE STANDARD FORMAT FOR ADDING A LOGICTHREAD TO THE LIST
        LogicThread moveSlowly = new LogicThread() {
            @Override
            public void loadCommands() {
                commands.add(new Translate(5000, Translate.Direction.FORWARD, 0.3, 0));
            }
        };
        Thread mtb = new Thread(moveSlowly);
        mtb.start();
        children.add(mtb);

        delegateMonitor(mtb, new MonitorThread[]{watchingForDebris, watchingForTime});


    }
}
