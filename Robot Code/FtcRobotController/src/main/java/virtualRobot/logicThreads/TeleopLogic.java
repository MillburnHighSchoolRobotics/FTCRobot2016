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

    @Override
    public void loadCommands() {
        /**
         * CONTROLLER 1 FUNCTIONS
         * 1. Joysticks: Tank drive
         * 2. Bumpers: Tape Measure Platform
         * 3. Triggers: Tape Measure In/Out
         * 4. A/B: Collection System Toggle
         * 5. Dpad Up/Down: Shield Up/Shield Down
         * 6. Dpad Left/Right: Scoop Servo Up/Down
         *
         * CONTROLLER 2 FUNCTIONS
         * 1. Triggers: Lift Up/Down
         * 2. Dpad Left/Right: Zipline Flippers
         * 3. Dpad Up/Down: People Dumper
         */
        commands.add(new Command() {
            @Override
            public boolean changeRobotState() {
                JoystickController joystick1 = robot.getJoystickController1();
                JoystickController joystick2 = robot.getJoystickController2();
                boolean FLIPPED1 = false;
                boolean FLIPPED2 = false;
                boolean isInterrupted = false;

                //dumper servo caps and deltas
                double dumperCurrentPos = 0;
                double dumperDelta = 0.005;

                //TAPE Measure Servo caps and delta
                double tapeMeasureCurrentPos = 0.25;
                final double servoDelta = 0.0023; //0.00115
                final double ARM_BOTTOM_CAP = 0.2;
                final double ARM_TOP_CAP = 0.3;

                boolean isRightFlipperDown = false;
                boolean isLeftFlipperDown = false;

                int collectionSystemDirection = 0;
                while (!isInterrupted) {
                    joystick1.logicalRefresh();
                    joystick2.logicalRefresh();


                    /**
                        JOYSTICK 1 PROGRAMMING
                     */

                    /**Back Button to flip right and left*/

                    if (joystick1.isPressed(JoystickController.BUTTON_BACK)) {
                        FLIPPED1 = !FLIPPED1;
                    }

                    /** 1. DRIVE - Joystick
                     *  Polynomial drive control for greater handling at the lower and mid range of the joystick.
                     */

                    double leftJoystick = -joystick1.getValue(JoystickController.Y_1);
                    double rightJoystick = -joystick1.getValue(JoystickController.Y_2);

                    if (FLIPPED1){
                        robot.getDriveLeftMotor().setPower(getDriveValue(Math.abs(leftJoystick), Math.signum(leftJoystick)));
                        robot.getDriveRightMotor().setPower(getDriveValue(Math.abs(rightJoystick), Math.signum(rightJoystick)));
                    }

                    else {
                        robot.getDriveLeftMotor().setPower(-getDriveValue(Math.abs(rightJoystick), Math.signum(rightJoystick)));
                        robot.getDriveRightMotor().setPower(-getDriveValue(Math.abs(leftJoystick), Math.signum(leftJoystick)));
                    }

                    /** 2. Tape Measure Platform - Bumpers*/
                    if ((joystick1.isDown(JoystickController.BUTTON_RB)) && (!joystick1.isDown(JoystickController.BUTTON_LB))) {
                        tapeMeasureCurrentPos += servoDelta;
                    }

                    if ((!joystick1.isDown(JoystickController.BUTTON_RB)) && (joystick1.isDown(JoystickController.BUTTON_LB))) {
                        tapeMeasureCurrentPos -= servoDelta;
                    }

                    tapeMeasureCurrentPos = Math.max(Math.min(tapeMeasureCurrentPos, ARM_TOP_CAP), ARM_BOTTOM_CAP);
                    robot.getTapeMeasureServo().setPosition(tapeMeasureCurrentPos);

                    /** 3. Tape Measure Extension - Triggers*/
                    if ((!joystick1.isDown(JoystickController.BUTTON_LT)) && (joystick1.isDown(JoystickController.BUTTON_RT))) {
                        robot.getTapeMeasureMotor().setPower(1);
                    }
                    else if ((joystick1.isDown(JoystickController.BUTTON_LT)) && (!joystick1.isDown(JoystickController.BUTTON_RT))) {
                        robot.getTapeMeasureMotor().setPower(-1);
                    }
                    else {
                        robot.getTapeMeasureMotor().setPower(0);
                    }

                    /** 4. Collection System - A/B */
                    if ((joystick1.isPressed(JoystickController.BUTTON_A)) && !(joystick1.isPressed(JoystickController.BUTTON_B))) {
                        if (collectionSystemDirection == 1) {
                            collectionSystemDirection = 0;
                        } else {
                            collectionSystemDirection = 1;
                        }
                    } else if (!(joystick1.isPressed(JoystickController.BUTTON_A)) && (joystick1.isPressed(JoystickController.BUTTON_B))) {
                        if (collectionSystemDirection == -1) {
                            collectionSystemDirection = 0;
                        } else {
                            collectionSystemDirection = -1;
                        }
                    }
                    robot.getReaperMotor().setPower(collectionSystemDirection);


                    /** 5. Shields */
                    if (joystick1.isDpadDown()) {
                        robot.getBackShieldServo().setPosition(0);
                    }

                    if (joystick1.isDpadUp()) {
                        robot.getBackShieldServo().setPosition(1);
                    }

                    /** 6. Scoop Servo */

                    if (joystick1.isDpadRight()) {
                        robot.getScoopServo().setPosition(0.5);
                    }
                    if (joystick1.isDpadLeft()) {
                        robot.getScoopServo().setPosition(1);
                    }


                    /**
                     * JOYSTICK 2 PROGRAMMING
                     */


                    /**Back Button to flip right and left */
                    if (joystick2.isPressed(JoystickController.BUTTON_BACK)) {
                        FLIPPED2 = !FLIPPED2;
                    }

                    /** 1. Lift Up and Down: Triggers
                     * NOTE: The original code for this was to set the lift power according to the pressure of the trigger,
                     *          but that majorly messed up the PID controller in the UpdateThread, so I had to change it back
                     *          to a static value.
                     **/

                    if ((joystick2.getValue(JoystickController.RT_PRESSURE) > .3) && !(joystick2.getValue(JoystickController.LT_PRESSURE) > .3)) {
                        robot.getLiftMotor().setPower(.6);
                    }
                    else if (!(joystick2.getValue(JoystickController.RT_PRESSURE) > .3) && (joystick2.getValue(JoystickController.LT_PRESSURE) > .3)) {
                        robot.getLiftMotor().setPower(-.6);
                    }
                    else {
                        robot.getLiftMotor().setPower(0);
                    }


                    /** 2. Zipline Flippers */
                    if (!joystick2.isDpadLeft() && joystick2.isDpadRight()) {
                        if (FLIPPED2){
                            isLeftFlipperDown = !isLeftFlipperDown;
                        }
                        else {
                            isRightFlipperDown = !isRightFlipperDown;
                        }
                    }
                    else if (joystick2.isDpadLeft() && !joystick2.isDpadRight()) {
                        if (FLIPPED2){
                            isRightFlipperDown = !isRightFlipperDown;
                        }
                        else {
                            isLeftFlipperDown = !isLeftFlipperDown;
                        }
                    }

                    //TODO tune flippers
                    robot.getFlipperLeftServo().setPosition(isLeftFlipperDown ? 0 : 0.5);
                    robot.getFlipperRightServo().setPosition(isRightFlipperDown ? 0 : 0.5);



                    /** 3. Dumping the People */
                    if (joystick2.isDpadDown()){
                        dumperCurrentPos += dumperDelta;


                    }

                    if (joystick2.isDpadUp()){
                        dumperCurrentPos -= dumperDelta;

                    }

                    dumperCurrentPos = Math.max(Math.min(dumperCurrentPos, 1), 0);
                    robot.getDumperServo().setPosition(dumperCurrentPos);


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
    public double getDriveValue(double x, double sign) {
        return sign * (0.5 * Math.pow((2*x - 1), 3) + 0.5);
    }
}