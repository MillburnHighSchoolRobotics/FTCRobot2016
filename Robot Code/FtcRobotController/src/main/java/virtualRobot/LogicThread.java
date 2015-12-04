package virtualRobot;


import java.util.ArrayList;
import java.util.List;
/**
 * Created by shant on 10/8/2015.
 * can access certain virtualRobot features, such as setting motor power.
 *
 * Implements Runnable, which contains a loop that executes commands and
 * exits when the thread is interrupted or when custom ExitCondition is met
 */
public abstract class LogicThread<T extends AutonomousRobot> implements Runnable {
    protected List<Command> commands;
    protected List<Thread> children;
    protected T robot;

    protected double startTime, elapsedTime;

    @Override
    public void run(){

        loadCommands();
        startTime = System.currentTimeMillis();

        while (!Thread.currentThread().isInterrupted() && (commands.size() != 0)) {
           boolean isInterrupted = false;
           Command c = commands.remove(0);
           //robot.addToProgress(robot.getCommands().remove(0));
           try {
               isInterrupted = c.changeRobotState();
           }
           catch (InterruptedException e) {
        	   isInterrupted = true;
           }
           if (c instanceof SpawnNewThread) {
               List<Thread> threadList = ((SpawnNewThread) c).getThreads();

               for (Thread t : threadList) {
                   children.add(t);
               }
           }

            elapsedTime = System.currentTimeMillis() - startTime;

           
           if (isInterrupted) 
        	   break;
        }
        
       
        for (Thread x: children)
        	if (x.isAlive())
        		x.interrupt();

    }
    
    public LogicThread() {
    	robot = (T) Command.ROBOT;
    	commands = new ArrayList<Command>();
    	children = new ArrayList<Thread>();
    }

    public abstract void loadCommands();
}
