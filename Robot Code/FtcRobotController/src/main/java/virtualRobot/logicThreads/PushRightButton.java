package virtualRobot.logicThreads;

import virtualRobot.AutonomousRobot;
import virtualRobot.LogicThread;
import virtualRobot.commands.MoveServo;
import virtualRobot.components.Servo;

/**
 * Created by shant on 1/9/2016.
 */
public class PushRightButton extends LogicThread<AutonomousRobot> {
    final double BUTTON_PUSHER_RIGHT = 0.05;
    public void loadCommands () {
        commands.add (new MoveServo(
                new Servo[] {
                        //robot.getButtonPusherServo()
                },
                new double[] {
                        BUTTON_PUSHER_RIGHT
                }
        ));
    }
}
