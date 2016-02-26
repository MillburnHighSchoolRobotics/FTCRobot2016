package virtualRobot.logicThreads;

import virtualRobot.AutonomousRobot;
import virtualRobot.LogicThread;
import virtualRobot.commands.MoveLift;
import virtualRobot.commands.Pause;

/**
 * Created by shant on 1/9/2016.
 */
public class PushRightButton extends LogicThread<AutonomousRobot> {
    final double BUTTON_PUSHER_RIGHT = 0.05;
    public void loadCommands () {
        robot.addToProgress("Pushed Right Button");
        commands.add(new MoveLift(MoveLift.RunMode.TO_VALUE, MoveLift.Direction.OUT, 4000));
        commands.add(new Pause(500));
        commands.add(new MoveLift(MoveLift.RunMode.TO_VALUE, MoveLift.Direction.IN, 0));
    }
}
