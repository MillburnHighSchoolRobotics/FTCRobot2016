package virtualRobot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 12/17/2015.
 * Keeps a list of LogicThreads spawned during runtime
 * Will be able to kill threads it manages
 *
 * Implements Runnable, which contains a loop that executes commands and
 * exits when the thread is interrupted or when custom ExitCondition is met
 * !! IDK IF WE NEED COMMANDS DELETE IF NEEDED !!
 */
public abstract class GodThread<T extends AutonomousRobot> implements Runnable {
    protected List<Command> commands;
    protected List<LogicThread> logicThread;

    @Override
    public void run(){

    //Stuff to add or get rid of LogicThreads when they are spawned or killed

    }
    public GodThread(){
        logicThread = new ArrayList<LogicThread>();
    }
}


