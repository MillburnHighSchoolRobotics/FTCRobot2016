package virtualRobot;

/**
 * Created by Yanjun on 11/28/2015.
 */
public class PIDTester extends LogicThread<AutonomousRobot> {
    @Override
    public void loadCommands() {
        commands.add(new Rotate(90, 0.6));
    }
}
