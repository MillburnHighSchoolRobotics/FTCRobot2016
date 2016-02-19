package virtualRobot.logicThreads;

import virtualRobot.AutonomousRobot;
import virtualRobot.LogicThread;
import virtualRobot.commands.MoveMotor;
import virtualRobot.commands.Translate;

/**
 * Created by Yanjun on 11/28/2015.
 */
public class PIDTester extends LogicThread<AutonomousRobot> {
    @Override
    public void loadCommands() {

        commands.add(new MoveMotor(robot.getLiftMotor(), 0.5, robot.getLiftMotorEncoder(), 5000, Translate.RunMode.CUSTOM, false));
    }
}
