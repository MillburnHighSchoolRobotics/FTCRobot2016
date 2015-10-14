package virtualRobot;

/**
 * Created by shant on 10/9/2015.
 */
public class MoveServo implements Command {
    String servoName;
    int position;
    private ExitCondition exitCondition;


    public MoveServo() {
        exitCondition = new ExitCondition() {
            public boolean isConditionMet() {
                return false;
            }
        }
    }

    public MoveServo (String servoName, int position) {
        this.servoName = servoName;
        this.position = position;
    }



    @Override
    public void changeRobotState() {

    }

}
