package virtualRobot;

/**
 * Created by Yanjun on 11/28/2015.
 */
public class PIDTester extends LogicThread<AutonomousRobot> {
    @Override
    public void loadCommands() {
        Rotate.setGlobalMaxPower(0.6);
        commands.add(new Rotate(3600));
    }
}
