package virtualRobot.logicThreads;

import virtualRobot.AutonomousRobot;
import virtualRobot.LogicThread;
import virtualRobot.commands.MoveLift;
import virtualRobot.commands.Pause;

/**
 * Created by shant on 1/9/2016.
 */
public class PushLeftButton extends LogicThread<AutonomousRobot> {
    final double BUTTON_PUSHER_LEFT = 0.45;
    @Override
    public void loadCommands() {

        robot.addToProgress("Pushed Left Button");
        commands.add(new MoveLift(MoveLift.RunMode.TO_VALUE, MoveLift.Direction.OUT, 1000));
        commands.add(new Pause(500));
        commands.add(new MoveLift(MoveLift.RunMode.TO_VALUE, MoveLift.Direction.IN, 0));
    }
}
