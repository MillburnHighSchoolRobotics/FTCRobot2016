package virtualRobot;

/**
 * Created by Yanjun on 11/12/2015.
 */
public class AutonomousTest3Logic extends LogicThread {
    @Override
    public void loadCommands() {
        commands.add(new Command() {
            @Override
            public boolean changeRobotState() {

                while (true) {
                    robot.getArmLeftServo().setPosition(0.7);
                    robot.getArmRightServo().setPosition(0.7);
                }
            }
        });
    }
}
