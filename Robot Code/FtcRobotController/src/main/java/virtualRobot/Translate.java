package virtualRobot;

/**
 * Created by shant on 10/14/2015.
 */
public class Translate implements Command {
    private ExitCondition exitCondition;
    private Motor[] motors;

    public Translate () {
        exitCondition = new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                return false;
            }
        };
        motors = AutonomousRobot.driveMotors;
    }

    public void setExitCondition (ExitCondition e) {
        exitCondition = e;
    }

    pub



    @Override
    public void changeRobotState() {

    }
}
