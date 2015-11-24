package virtualRobot;

/**
 * Created by shant on 11/14/2015.
 */
public class TeleopClimbingSupport3Logic extends LogicThread<TeleopRobot> {

    @Override
    public void loadCommands() {
        commands.add(new MoveServo(new Servo[] {robot.getArmLeftServo(), robot.getArmRightServo()}, new double[] {0.3, 0.3}));
        commands.add(new Pause(500));
    }
}
