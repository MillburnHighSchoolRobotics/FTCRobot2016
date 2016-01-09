package virtualRobot.godThreads;

import java.util.concurrent.atomic.AtomicBoolean;

import virtualRobot.GodThread;
import virtualRobot.LogicThread;
import virtualRobot.logicThreads.BlueClimberDumpLogic;
import virtualRobot.logicThreads.PushLeftButton;
import virtualRobot.logicThreads.PushRightButton;

/**
 * Created by shant on 1/5/2016.
 */
public class BlueAutoGodThread extends GodThread {

    @Override
    public void realRun() throws InterruptedException {
        //shitWentWrong = false;
        AtomicBoolean redisLeft = new AtomicBoolean();

        // THIS IS THE STANDARD FORMAT FOR ADDING A LOGICTHREAD TO THE LIST
        LogicThread moveToBeacon = new BlueClimberDumpLogic(redisLeft);
        Thread mtb = new Thread(moveToBeacon);
        children.add(mtb);

        MonitorThread watchingForDebris = new DebrisMonitor();
        Thread dm = new Thread(watchingForDebris);
        children.add(dm);

        MonitorThread watchingForTime = new TimeMonitor();
        Thread tm = new Thread(watchingForTime);
        children.add(tm);

        delegateMonitor(mtb, new MonitorThread[]{watchingForDebris, watchingForTime});
        //waitToProceed (mtb);

        if (!redisLeft.get()) {
            LogicThread pushLeft = new PushLeftButton();
            Thread pl = new Thread(pushLeft);
            children.add(pl);
            waitToProceed(pl);
        } else if (redisLeft.get()) {
            LogicThread pushRight = new PushRightButton();
            Thread pr = new Thread(pushRight);
            children.add(pr);
            waitToProceed(pr);
        }

    }

    /*
    private void waitToProceed (Thread t) throws InterruptedException{
        while (!shitWentWrong && t.isAlive()) {
            requestApproval();
            if (shitWentWrong) {
                kill();
                return;
            }
        }
    }
    */

    private void delegateMonitor(Thread logic, MonitorThread[] monitors) throws InterruptedException {
        while (logic.isAlive()) {
            boolean isNormal = true;
            for (MonitorThread m : monitors) {
                if (m.getStatus() != MonitorThread.NORMAL) {
                    isNormal = false;
                    break;
                }
            }

            if (!isNormal) {
                killInnerThread();
            }

            requestApproval();
        }
    }
}
