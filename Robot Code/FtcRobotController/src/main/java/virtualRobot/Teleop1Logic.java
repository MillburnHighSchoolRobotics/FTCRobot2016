package virtualRobot;

/**
 * Created by shant on 11/12/2015.
 */
public class Teleop1Logic extends LogicThread {
    @Override
    public void loadCommands() {
        commands.add(new Command() {
            @Override
            public boolean changeRobotState() {
                JoystickController joystick = robot.getJoystickController();

                while (true) {

                    if (joystick.isDown(JoystickController.BUTTON_LT) && !joystick.isDown(JoystickController.BUTTON_RT)) {
                        robot.getBlockerLeftServo().setPosition(0);
                        robot.getBlockerRightServo().setPosition(0);
                    }

                    if (!joystick.isDown(JoystickController.BUTTON_LT) && joystick.isDown(JoystickController.BUTTON_RT)) {
                        robot.getBlockerLeftServo().setPosition(1);
                        robot.getBlockerRightServo().setPosition(1);
                    }

                    
                }
            }

        });
    }
}
