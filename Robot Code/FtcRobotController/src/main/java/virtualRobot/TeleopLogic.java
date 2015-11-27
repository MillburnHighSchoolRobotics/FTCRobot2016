package virtualRobot;

/**
 * _____ ______   ___  ___  ________
 * |\   _ \  _   \|\  \|\  \|\   ____\             .-""-.
 * \ \  \\\__\ \  \ \  \\\  \ \  \___|_           /[] _ _\
 * \ \  \\|__| \  \ \   __  \ \_____  \         _|_o_LII|_
 * \ \  \    \ \  \ \  \ \  \|____|\  \       / | ==== | \
 * \ \__\    \ \__\ \__\ \__\____\_\  \      |_| ==== |_|
 * \|__|     \|__|\|__|\|__|\_________\      ||" ||  ||
 * \|_________|      ||LI  o ||
 * ||'----'||
 * /__|    |__\
 * <p/>
 * Created by shant on 11/25/2015.
 */
public class TeleopLogic extends LogicThread<TeleopRobot> {
    @Override
    public void loadCommands() {

        commands.add(new Command() {
            @Override
            public boolean changeRobotState() {
                JoystickController joystick1 = robot.getJoystickController1();
                JoystickController joystick2 = robot.getJoystickController2();
                boolean FLIPPED = false;
                boolean isInterrupted = false;
                double dumperCurrentPos = 0;
                double buttonCurrentPos = 0;
                double tapeMeasureCurrentPos = 0.485;
                final double servoDelta = 0.0023; //0.00115
                final double ARM_BOTTOM_CAP = 0.485;
                final double ARM_TOP_CAP = 0.725;
                while (!isInterrupted) {

                    joystick1.logicalRefresh();
                    joystick2.logicalRefresh();


                    //Back Button to flip right and left
                    if (joystick1.isPressed(JoystickController.BUTTON_BACK)) {
                        FLIPPED = !FLIPPED;
                    }


                    //DRIVE
                    if (FLIPPED){
                        robot.getDriveLeftMotor().setPower(-joystick1.getValue(JoystickController.Y_2));
                        robot.getDriveRightMotor().setPower(-joystick1.getValue(JoystickController.Y_1));
                    }

                    else {
                        robot.getDriveLeftMotor().setPower(joystick1.getValue(JoystickController.Y_1));
                        robot.getDriveRightMotor().setPower(joystick1.getValue(JoystickController.Y_2));
                    }


                    //SHIELDS
                    if (joystick1.isDpadDown()) {
                        robot.getFrontShieldServo().setPosition(1);
                        robot.getBackShieldServo().setPosition(0);
                    }

                    if (joystick1.isDpadUp()) {
                        robot.getFrontShieldServo().setPosition(0.4);
                        robot.getBackShieldServo().setPosition(1);
                    }





                    //FLIPPING THE PEOPLE OFF
                    if (!joystick1.isDown(JoystickController.BUTTON_LEFT_STICK) && joystick1.isDown(JoystickController.BUTTON_RIGHT_STICK)) {
                        if (FLIPPED){
                            robot.getFlipperLeftServo().setPosition(1);
                        }
                        else {
                            robot.getFlipperRightServo().setPosition(1);
                        }
                    }
                    else if (joystick1.isDown(JoystickController.BUTTON_LEFT_STICK) && !joystick1.isDown(JoystickController.BUTTON_RIGHT_STICK)) {
                        if (FLIPPED){
                            robot.getFlipperRightServo().setPosition(1);
                        }
                        else {
                            robot.getFlipperLeftServo().setPosition(1);
                        }
                    }

                    else {
                        robot.getFlipperLeftServo().setPosition(0.5);
                        robot.getFlipperRightServo().setPosition(0.5);
                    }


                    //Dumping the People
                    if (!joystick1.isDown(JoystickController.BUTTON_RB) && joystick1.isDown(JoystickController.BUTTON_RT)){
                        dumperCurrentPos += 0.05;
                        if (dumperCurrentPos > 1) dumperCurrentPos = 1;
                        if (dumperCurrentPos < 0) dumperCurrentPos = 0;

                        robot.getDumperServo().setPosition(dumperCurrentPos);
                    }

                    if (joystick1.isDown(JoystickController.BUTTON_RB) && !joystick1.isDown(JoystickController.BUTTON_RT)){
                        dumperCurrentPos -= 0.05;
                        if (dumperCurrentPos > 1) dumperCurrentPos = 1;
                        if (dumperCurrentPos < 0) dumperCurrentPos = 0;

                        robot.getDumperServo().setPosition(dumperCurrentPos);
                    }

                    /*
                    //Button Pusher
                    if (!joystick1.isDown(JoystickController.BUTTON_LB) && joystick1.isDown(JoystickController.BUTTON_LT)) {
                        buttonCurrentPos += 0.05;
                        if (buttonCurrentPos > .2) buttonCurrentPos = 0.2;
                        if (buttonCurrentPos < 0) buttonCurrentPos = 0;

                        robot.getButtonPusherServo().setPosition(buttonCurrentPos);

                    }
                    */



                    if (joystick1.isDown(JoystickController.BUTTON_LB) && !joystick1.isDown(JoystickController.BUTTON_LT)) {
                        buttonCurrentPos -= 0.05;
                        if (buttonCurrentPos > .2) buttonCurrentPos = 0.2;
                        if (buttonCurrentPos < 0) buttonCurrentPos = 0;

                        robot.getButtonPusherServo().setPosition(buttonCurrentPos);
                    }

                    //TAPE MEASURE
                    if ((joystick2.isDown(JoystickController.BUTTON_RB)) && (!joystick2.isDown(JoystickController.BUTTON_RT))) {
                        tapeMeasureCurrentPos += servoDelta;
                        if (tapeMeasureCurrentPos > ARM_TOP_CAP) tapeMeasureCurrentPos = ARM_TOP_CAP;
                        if (tapeMeasureCurrentPos < ARM_BOTTOM_CAP) tapeMeasureCurrentPos = ARM_BOTTOM_CAP;

                        robot.getTapeMeasureServo().setPosition(tapeMeasureCurrentPos);
                    }

                    if ((!joystick2.isDown(JoystickController.BUTTON_RB)) && (joystick2.isDown(JoystickController.BUTTON_RT))) {
                        tapeMeasureCurrentPos -= servoDelta;
                        if (tapeMeasureCurrentPos > ARM_TOP_CAP) tapeMeasureCurrentPos = ARM_TOP_CAP;
                        if (tapeMeasureCurrentPos < ARM_BOTTOM_CAP) tapeMeasureCurrentPos = ARM_BOTTOM_CAP;

                        robot.getTapeMeasureServo().setPosition(tapeMeasureCurrentPos);
                    }

                    robot.getTapeMeasureFrontMotor().setPower(joystick2.getValue(JoystickController.Y_1));
                    robot.getTapeMeasureBackMotor().setPower(joystick2.getValue(JoystickController.Y_2));




                    try {
                        Thread.currentThread().sleep(30);
                    } catch (InterruptedException e) {
                        isInterrupted = true;
                    }

                }

                return isInterrupted;

            }

        });
    }
}