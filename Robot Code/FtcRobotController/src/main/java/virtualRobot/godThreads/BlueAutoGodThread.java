package virtualRobot.godThreads;

import java.util.concurrent.atomic.AtomicBoolean;

import virtualRobot.GodThread;
import virtualRobot.LogicThread;
import virtualRobot.MonitorThread;
//import virtualRobot.logicThreads.BlueGetToBeacon;
import virtualRobot.logicThreads.PushLeftButton;
import virtualRobot.logicThreads.PushRightButton;
import virtualRobot.monitorThreads.DebrisMonitor;
import virtualRobot.monitorThreads.TimeMonitor;

/**
 * Created by shant on 1/5/2016.
 */
public class BlueAutoGodThread extends GodThread {

    @Override
    public void realRun() throws InterruptedException {
        AtomicBoolean redisLeft = new AtomicBoolean();

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
        /*LogicThread moveToBeacon = new BlueGetToBeacon(redisLeft);
        Thread mtb = new Thread(moveToBeacon);
        mtb.start();
        children.add(mtb);*/

        //keep the program alive as long as the two monitor threads are still going - should proceed every logicThread addition
       // delegateMonitor(mtb, new MonitorThread[]{watchingForDebris, watchingForTime});
        //waitToProceed (mtb);

        if (!redisLeft.get()) {
            LogicThread pushLeft = new PushLeftButton();
            Thread pl = new Thread(pushLeft);
            pl.start();
            children.add(pl);
            delegateMonitor(pl, new MonitorThread[]{watchingForDebris, watchingForTime});
        }

        else if (redisLeft.get()) {
            LogicThread pushRight = new PushRightButton();
            Thread pr = new Thread(pushRight);
            pr.start();
            children.add(pr);
            delegateMonitor(pr, new MonitorThread[]{watchingForDebris, watchingForTime});
        }

    }
}
