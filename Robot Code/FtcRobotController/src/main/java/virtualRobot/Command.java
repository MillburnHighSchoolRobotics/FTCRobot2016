package virtualRobot;

/**
 * Created by shant on 10/8/2015.
 */
public interface Command  {
    /*
        changeRobotState should manipulate the AutonomousRobot through the LogicThread
     */
    void changeRobotState ();
}
