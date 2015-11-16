package virtualRobot;

/**
 * Created by shant on 11/14/2015.
 */
public class TeleopClimbingSupport1Logic extends LogicThread {
    @Override
    public void loadCommands() {
        commands.add(new MoveServo(new Servo[] {robot.getArmLeftServo(), robot.getArmRightServo()}, new double[] {0.35, 0.35}));
        commands.add(new Pause(2500));
    }
}
