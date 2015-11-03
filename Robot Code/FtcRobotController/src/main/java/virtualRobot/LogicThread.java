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
public class LogicThread implements Runnable {
    List<Command> commands;
    List<Thread> children;
    AutonomousRobot robot;



    @Override
    public void run(){
    	
    	robot.getLeftMotorEncoder().clearValue();
    	robot.getRightMotorEncoder().clearValue();
    	robot.getAngleSensor().clearValue();

        while (!Thread.currentThread().isInterrupted() && (commands.size() != 0)) {
           boolean isInterrupted = false;
           Command c = commands.remove(0);
           try {
               isInterrupted = c.changeRobotState();
           }
           catch (InterruptedException e) {

           }
           if (c instanceof SpawnNewThread) {
               List<Thread> threadList = ((SpawnNewThread) c).getThreads();

               for (Thread t : threadList) {
                   children.add(t);
               }
           }

           
           if (isInterrupted) 
        	   break;
        }
        
       
        for (Thread x: children)
        	if (x.isAlive())
        		x.interrupt();

    }
    
    public LogicThread() {
    	robot = Command.robot;
    	commands = new ArrayList<Command>();
    	children = new ArrayList<Thread>();
    }
}
