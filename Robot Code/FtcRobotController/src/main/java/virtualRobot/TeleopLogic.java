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
                double buttonCurrentPos = 0.5;
                double hangCurrentPos = 0;
                final double BUTTON_PUSHER_LEFT = 0.05;
                final double BUTTON_PUSHER_RIGHT = 0.45;
                double tapeMeasureCurrentPos = 0.63;
                final double servoDelta = 0.0023; //0.00115
                final double ARM_BOTTOM_CAP = 0.485;
                final double ARM_TOP_CAP = 0.725;
                boolean isRightFlipperDown = false;
                boolean isLeftFlipperDown = false;
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
                    if (!joystick1.isPressed(JoystickController.BUTTON_LEFT_STICK) && joystick1.isPressed(JoystickController.BUTTON_RIGHT_STICK)) {
                        if (FLIPPED){
                            isLeftFlipperDown = !isLeftFlipperDown;
                            //robot.getFlipperLeftServo().setPosition(0);
                        }
                        else {
                            isRightFlipperDown = !isRightFlipperDown;
                            //robot.getFlipperRightServo().setPosition(0);
                        }
                    }
                    else if (joystick1.isPressed(JoystickController.BUTTON_LEFT_STICK) && !joystick1.isPressed(JoystickController.BUTTON_RIGHT_STICK)) {
                        if (FLIPPED){
                            isRightFlipperDown = !isRightFlipperDown;
                            //robot.getFlipperRightServo().setPosition(0);
                        }
                        else {
                            isLeftFlipperDown = !isLeftFlipperDown;
                            //robot.getFlipperLeftServo().setPosition(0);
                        }
                    }


                        robot.getFlipperLeftServo().setPosition(isLeftFlipperDown ? 0 : 0.5);
                        robot.getFlipperRightServo().setPosition(isRightFlipperDown ? 0 : 0.5);



                    //Dumping the People
                    if (joystick2.isDpadDown()){


                        robot.getDumperServo().setPosition(0);
                    }

                    if (joystick2.isDpadUp()){

                        robot.getDumperServo().setPosition(1);
                    }

                    /*JUST FOR TESTING AUTONOMOUS

                    //Button Pusher
                    if (!joystick1.isDown(JoystickController.BUTTON_LB) && joystick1.isDown(JoystickController.BUTTON_LT)) {
                        buttonCurrentPos += 0.01;
                        if (buttonCurrentPos > BUTTON_PUSHER_RIGHT) buttonCurrentPos = BUTTON_PUSHER_RIGHT;
                        if (buttonCurrentPos < BUTTON_PUSHER_LEFT) buttonCurrentPos = BUTTON_PUSHER_LEFT;

                        robot.getButtonPusherServo().setPosition(buttonCurrentPos);

                    }

                    else if (joystick1.isDown(JoystickController.BUTTON_LB) && !joystick1.isDown(JoystickController.BUTTON_LT)) {
                        buttonCurrentPos -= 0.01;
                        if (buttonCurrentPos > BUTTON_PUSHER_RIGHT) buttonCurrentPos = BUTTON_PUSHER_RIGHT;
                        if (buttonCurrentPos < BUTTON_PUSHER_LEFT) buttonCurrentPos = BUTTON_PUSHER_LEFT;

                        robot.getButtonPusherServo().setPosition(buttonCurrentPos);

                    }






                    if (joystick1.isDown(JoystickController.BUTTON_LB) && !joystick1.isDown(JoystickController.BUTTON_LT)) {
                        buttonCurrentPos -= 0.05;
                        if (buttonCurrentPos > .2) buttonCurrentPos = 0.2;
                        if (buttonCurrentPos < 0) buttonCurrentPos = 0;

                        robot.getButtonPusherServo().setPosition(buttonCurrentPos);
                    }
                    */
                    //TAPE MEASURE PLATFORM
                    if ((joystick2.isDown(JoystickController.BUTTON_RB)) && (!joystick2.isDown(JoystickController.BUTTON_LB))) {
                        tapeMeasureCurrentPos += servoDelta;
                        if (tapeMeasureCurrentPos > ARM_TOP_CAP) tapeMeasureCurrentPos = ARM_TOP_CAP;
                        if (tapeMeasureCurrentPos < ARM_BOTTOM_CAP) tapeMeasureCurrentPos = ARM_BOTTOM_CAP;

                        robot.getTapeMeasureServo().setPosition(tapeMeasureCurrentPos);
                    }

                    if ((!joystick2.isDown(JoystickController.BUTTON_RB)) && (joystick2.isDown(JoystickController.BUTTON_LB))) {
                        tapeMeasureCurrentPos -= servoDelta;
                        if (tapeMeasureCurrentPos > ARM_TOP_CAP) tapeMeasureCurrentPos = ARM_TOP_CAP;
                        if (tapeMeasureCurrentPos < ARM_BOTTOM_CAP) tapeMeasureCurrentPos = ARM_BOTTOM_CAP;

                        robot.getTapeMeasureServo().setPosition(tapeMeasureCurrentPos);
                    }

                    //TAPE Measures
                    if ((!joystick1.isDown(JoystickController.BUTTON_LT)) && (joystick1.isDown(JoystickController.BUTTON_LB))) {
                        robot.getTapeMeasureBackMotor().setPower(1);
                    }
                    else if ((joystick1.isDown(JoystickController.BUTTON_LT)) && (!joystick1.isDown(JoystickController.BUTTON_LB))) {
                        robot.getTapeMeasureBackMotor().setPower(-1);
                    }
                    else {
                        robot.getTapeMeasureBackMotor().setPower(0);
                    }

                    if ((!joystick1.isDown(JoystickController.BUTTON_RT)) && (joystick1.isDown(JoystickController.BUTTON_RB))) {
                        robot.getTapeMeasureFrontMotor().setPower(1);
                    }
                    else if ((joystick1.isDown(JoystickController.BUTTON_RT)) && (!joystick1.isDown(JoystickController.BUTTON_RB))) {
                        robot.getTapeMeasureFrontMotor().setPower(-1);
                    }
                    else {
                        robot.getTapeMeasureFrontMotor().setPower(0);
                    }



                    //permanent hang servos
                    if ((!joystick2.isDown(JoystickController.BUTTON_LT)) && (joystick2.isDown(JoystickController.BUTTON_RT))) {
                        hangCurrentPos += 0.05;
                        if (hangCurrentPos > .5) hangCurrentPos = .5;
                        if (hangCurrentPos < 0) hangCurrentPos = 0;

                        robot.getHangServo().setPosition(hangCurrentPos);
                    }

                    if ((joystick2.isDown(JoystickController.BUTTON_LT)) && (!joystick2.isDown(JoystickController.BUTTON_RT))) {
                        hangCurrentPos -= 0.05;
                        if (hangCurrentPos > .5) hangCurrentPos = .5;
                        if (hangCurrentPos < 0) hangCurrentPos = 0;

                        robot.getHangServo().setPosition(hangCurrentPos);
                    }




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