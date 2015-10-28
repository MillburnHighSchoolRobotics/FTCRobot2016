package virtualRobot;


import java.util.Queue;
import java.util.*;
/**
 * Created by shant on 10/8/2015.
 * can access certain virtualRobot features, such as setting motor power.
 *
 * Implements Runnable, which contains a loop that executes commands and
 * exits when the thread is interrupted or when custom ExitCondition is met
 */
public abstract class LogicThread implements Runnable {
    Queue<Command> commands;
    List<Thread> children;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && (commands.size() != 0)) {
           Command c = commands.poll();
            c.changeRobotState();
            if (c instanceof SpawnNewThread)
                children.add(((SpawnNewThread) c).getThread());
        }
    }
}
