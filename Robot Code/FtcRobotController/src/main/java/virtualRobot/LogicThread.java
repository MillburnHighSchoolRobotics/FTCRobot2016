package virtualRobot;


import java.util.Queue;

/**
 * Created by shant on 10/8/2015.
 * can access certain virtualRobot features, such as setting motor power.
 *
 * Implements Runnable, which contains a loop that executes commands and
 * exits when the thread is interrupted or when custom ExitCondition is met
 */
public abstract class LogicThread implements Runnable, Command {
    Queue<Command> commands;

    @Override
    public void run() {

    }
}
