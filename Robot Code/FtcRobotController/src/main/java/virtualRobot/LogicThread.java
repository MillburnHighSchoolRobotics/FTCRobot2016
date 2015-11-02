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
public class LogicThread implements Runnable {
    List<Command> commands;
    List<Thread> children;
    AutonomousRobot robot;

    @Override
    public void run() {
    	
    	robot.getLeftMotorEncoder.clearValue();
    	robot.getRightMotorEncoder.clearValue();
    	robot.getAngleSensor().clearValue();

        while (!Thread.currentThread().isInterrupted() && (commands.size() != 0)) {
           
           Command c = commands.remove(0);
           boolean isInterrupted = c.changeRobotState();
           if (c instanceof SpawnNewThread)
                children.add(((SpawnNewThread) c).getThread());
           
           if (isInterrupted) 
        	   break;
        }
        
       
        for (Thread x: children)
        	if (x.isAlive())
        		x.interrupt();

    }
    
    public LogicThread() {
    	robot = Command.robot;
    	commands = new ArrayList<>();
    	children = new ArrayList<>();
    }
}
