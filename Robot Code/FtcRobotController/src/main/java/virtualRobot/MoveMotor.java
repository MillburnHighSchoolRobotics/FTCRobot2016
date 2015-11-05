package virtualRobot;

import java.util.ArrayList;

/**
 * Created by shant on 11/5/2015.
 */
public class MoveMotor implements Command {
    private ExitCondition exitCondition;
    private ArrayList<Object[]> motors = new ArrayList<Object[]>();

    public MoveMotor() {
        exitCondition = new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                return false;
            }
        };
    }

    public void addMotorChange (Motor motor, double newPower) {
        motors.add(new Object[] {motor, new Double(newPower)});
    }

    public void removeMotorChange (Motor motor) {
        for (int i = 0; i < motors.size(); i++){
            if (motors.get(i)[0] == motor) {
                motors.remove(i);
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
    public boolean changeRobotState() throws InterruptedException {
        int i = 0;
        boolean isInterrupted = false;
        while (!exitCondition.isConditionMet() && i < motors.size()) {
            ((Motor) motors.get(i)[0]).setPower((Double) motors.get(i)[1]);
            i++;


            if (Thread.currentThread().isInterrupted()) {
                isInterrupted = true;
                break;
            }
        }

        return isInterrupted;
    }
}
