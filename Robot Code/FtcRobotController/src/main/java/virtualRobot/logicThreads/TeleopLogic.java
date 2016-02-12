package virtualRobot.logicThreads;

import virtualRobot.JoystickController;
import virtualRobot.LogicThread;
import virtualRobot.TeleopRobot;
import virtualRobot.commands.Command;

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
    public double getDriveValue(double x, double sign) {
        return sign * 0.5 * Math.pow((2*x - 1), 3) + 0.5;
    }
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
                double tapeMeasureCurrentPos = 0.63;
                final double servoDelta = 0.0023; //0.00115
                final double ARM_BOTTOM_CAP = 0.2;
                final double ARM_TOP_CAP = 0.3;
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
                    //Polynomial drive control for greater handling at the lower and mid range of the joystick.
                    double leftJoystick = -joystick1.getValue(JoystickController.Y_2);
                    double rightJoystick = -joystick2.getValue(JoystickController.Y_1);



                    if (FLIPPED){
                        robot.getDriveLeftMotor().setPower(getDriveValue(Math.abs(leftJoystick), Math.signum(leftJoystick)));
                        robot.getDriveRightMotor().setPower(getDriveValue(Math.abs(rightJoystick), Math.signum(rightJoystick)));
                    }

                    else {
                        robot.getDriveLeftMotor().setPower(getDriveValue(Math.abs(rightJoystick), Math.signum(rightJoystick)));
                        robot.getDriveRightMotor().setPower(getDriveValue(Math.abs(leftJoystick), Math.signum(leftJoystick)));
                    }


                    //SHIELDS
                    if (joystick1.isDpadDown()) {
                        robot.getBackShieldServo().setPosition(1);
                    }

                    if (joystick1.isDpadUp()) {
                        robot.getBackShieldServo().setPosition(0);
                    }





                    //ZIPLINE FLIPPERS
                    if (!joystick1.isDpadLeft() && joystick1.isDpadRight()) {
                        if (FLIPPED){
                            isLeftFlipperDown = !isLeftFlipperDown;
                        }
                        else {
                            isRightFlipperDown = !isRightFlipperDown;
                        }
                    }
                    else if (joystick1.isDpadLeft() && !joystick1.isDpadRight()) {
                        if (FLIPPED){
                            isRightFlipperDown = !isRightFlipperDown;
                        }
                        else {
                            isLeftFlipperDown = !isLeftFlipperDown;
                        }
                    }

                    //TODO tune flippers
                    robot.getFlipperLeftServo().setPosition(isLeftFlipperDown ? 0 : 0.5);
                    robot.getFlipperRightServo().setPosition(isRightFlipperDown ? 0 : 0.5);



                    //Dumping the People
                    if (joystick2.isDpadDown()){
                        robot.getDumperServo().setPosition(0);
                    }

                    if (joystick2.isDpadUp()){

                        robot.getDumperServo().setPosition(1);
                    }


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
                    if ((!joystick1.isDown(JoystickController.BUTTON_LT)) && (joystick1.isDown(JoystickController.BUTTON_RT))) {
                        robot.getTapeMeasureMotor().setPower(1);
                    }
                    else if ((joystick1.isDown(JoystickController.BUTTON_LT)) && (!joystick1.isDown(JoystickController.BUTTON_RT))) {
                        robot.getTapeMeasureMotor().setPower(-1);
                    }
                    else {
                        robot.getTapeMeasureMotor().setPower(0);
                    }

                    /*
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

                    */


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