package virtualRobot;

import java.util.ArrayList;

/**
 * Created by shant on 10/9/2015.
 */
public class MoveServo implements Command {
    private ExitCondition exitCondition;
    private ArrayList<Object[]> servos = new ArrayList<Object[]>();

    public MoveServo() {
        exitCondition = new ExitCondition() {
            public boolean isConditionMet() {
                return false;
            }
        };
    }


    public void addServoChange(Servo servo, double newPosition) {
        servos.add(new Object[] {servo, new Double(newPosition)});
    }

    public void removeServoChange(Servo servo) {
        for (int i = 0; i < servos.size(); i++) {
            if (servos.get(i)[0] == servo) {
                servos.remove(i);
                break;
            }
        }
    }

    public void setExitCondition (ExitCondition e) {
        exitCondition = e;
    }

    public ExitCondition getExitCondition () {
        return exitCondition;
    }

    @Override
    public boolean changeRobotState() {
        int i = 0;
        boolean isInterrupted = false;
        while (!exitCondition.isConditionMet() && i < servos.size()) {
            ((Servo)servos.get(i)[0]).setPosition(((Double)servos.get(i)[1]));
            i++;
            
            if (Thread.currentThread().isInterrupted()) {
            	isInterrupted = true;
            	break;
            }
        }
        
        return isInterrupted;
    }

}
