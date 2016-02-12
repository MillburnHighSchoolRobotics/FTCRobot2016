package virtualRobot.logicThreads;

import virtualRobot.AutonomousRobot;
import virtualRobot.LogicThread;
import virtualRobot.commands.MoveServo;
import virtualRobot.components.Servo;

/**
 * Created by shant on 1/9/2016.
 */
public class PushLeftButton extends LogicThread<AutonomousRobot> {
    final double BUTTON_PUSHER_LEFT = 0.45;
    @Override
    public void loadCommands() {
        commands.add(new MoveServo(
                new Servo[]{
                      //  robot.getButtonPusherServo()
                },
                new double[]{
                        BUTTON_PUSHER_LEFT
                }
        ));
    }
}
