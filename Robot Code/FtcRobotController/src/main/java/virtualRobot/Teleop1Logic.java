package virtualRobot;
/**
 * TO-DO
 * 1. Setup the Conveyor Belt Motor in the config file
 * 2. Wire the Arm Motor and Encoder
 * 3. Calibrate EVERYTHING
 */


/**
 * Created by shant on 11/12/2015.
 */
public class Teleop1Logic extends LogicThread {
    @Override
    public void loadCommands() {

        final double servoDelta = 0.00115;
        final double ARM_TOP_CAP = 0.55;
        final double ARM_BOTTOM_CAP = 0.7;


        commands.add(new Command() {
            @Override
            public boolean changeRobotState() {
                JoystickController joystick = robot.getJoystickController();
                robot.getArmRightMotorEncoder().clearValue();

                while (true) {
                    double currentPos = robot.getArmLeftServo().getPosition();
                    //SHIELDS
                    if (joystick.isDpadDown()) { //ONE BUTTON TO LOWER THE SHIELDS
                        robot.getBlockerLeftServo().setPosition(0);
                        robot.getBlockerRightServo().setPosition(0);
                    }

                    if (joystick.isDpadUp()) { //ONE BUTTON TO RAISE THEM
                        robot.getBlockerLeftServo().setPosition(1);
                        robot.getBlockerRightServo().setPosition(1);
                    }

                    if (joystick.isDpadLeft()) {
                        //ONE BUTTON TO RULE THEM ALL
                        //AND IN THE DARKNESS BIND THEM
                    }


                    //DRIVE
                    robot.getDriveLeftMotor().setPower(joystick.getValue(JoystickController.Y_1));
                    robot.getDriveRightMotor().setPower(joystick.getValue(JoystickController.Y_2));

                    //CONVEYOR BELT + GATE
                    if (joystick.isDown(JoystickController.BUTTON_LB) && !joystick.isDown(JoystickController.BUTTON_RB)) {
                        robot.getGateRightServo().setPosition(0); //TO CALIBRATE
                        robot.getConveyorMotor().setPower(1); //TO CALIBRATE
                    }

                    if (!joystick.isDown(JoystickController.BUTTON_LB) && joystick.isDown(JoystickController.BUTTON_RB)) {
                        robot.getGateRightServo().setPosition(1); //TO CALIBRATE
                        robot.getConveyorMotor().setPower(0); //TO CALIBRATE
                    }

                    //REAPER
                    if (joystick.isDown(JoystickController.BUTTON_LT) && !joystick.isDown(JoystickController.BUTTON_RT)){
                        robot.getReaperMotor().setPower(1);
                    }

                    if (!joystick.isDown(JoystickController.BUTTON_LT) && joystick.isDown(JoystickController.BUTTON_RT)) {
                        robot.getReaperMotor().setPower(0);
                    }

                    //ARM ROTATION
                    if (joystick.isDown(JoystickController.BUTTON_A) && !joystick.isDown(JoystickController.BUTTON_B)) {
                        if (currentPos >= ARM_TOP_CAP && currentPos <= ARM_BOTTOM_CAP) {
                            currentPos += servoDelta;
                        }
                    }
                    if (!joystick.isDown(JoystickController.BUTTON_A) && joystick.isDown(JoystickController.BUTTON_B)) {
                        if (currentPos >= ARM_TOP_CAP && currentPos <= ARM_BOTTOM_CAP) {
                            currentPos -= servoDelta;
                        }
                    }

                    //ARM EXTENSION
                    if (joystick.isDown(JoystickController.BUTTON_X) && !joystick.isDown(JoystickController.BUTTON_Y)) {
                        double enc = robot.getArmRightMotorEncoder().getValue();
                        if (enc >= 0 && enc <= 3500){
                            robot.getArmLeftMotor().setPower(0.5);
                            robot.getArmRightMotor().setPower(0.5);
                        }
                    }
                    if (!joystick.isDown(JoystickController.BUTTON_X) && joystick.isDown(JoystickController.BUTTON_Y)) {
                        double enc = robot.getArmRightMotorEncoder().getValue();
                        if (enc >= 0 && enc <= 3500){
                            robot.getArmLeftMotor().setPower(0.5);
                            robot.getArmRightMotor().setPower(-0.5);
                        }
                    }


                }
            }

        });
    }
}
