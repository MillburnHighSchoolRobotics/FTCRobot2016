package virtualRobot.logicThreads;

import virtualRobot.JoystickController;
import virtualRobot.LogicThread;
import virtualRobot.PIDController;
import virtualRobot.TeleopRobot;
import virtualRobot.commands.Command;
import virtualRobot.commands.MoveLift;

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
         * 5. Dpad Up/Down: Scoop Servo
         *
         * CONTROLLER 2 FUNCTIONS
         * 1. Triggers: Lift Up/Down
         * 2. Bumpers: Lift Adjustment
         * 3. Dpad Left/Right: Zipline Flippers
         * 4. X/Y: People Dumper
         * 5. A/B: Basket Dump
         * 6. Dpad Up/Down: Shield Up/Shield Down
         */
        commands.add(new Command() {
            @Override
            public boolean changeRobotState() throws InterruptedException {
                JoystickController joystick1 = robot.getJoystickController1();
                JoystickController joystick2 = robot.getJoystickController2();
                boolean FLIPPED1 = false;
                boolean FLIPPED2 = false;
                boolean isInterrupted = false;

                //dumper servo caps and deltas
                double dumperCurrentPos = 0;
                double dumperDelta = 0.05;

                //TAPE Measure Servo caps and delta
                double tapeMeasureCurrentPos = 0.25;
                final double servoDelta = 0.0023; //0.00115
                final double ARM_BOTTOM_CAP = 0.2;
                final double ARM_TOP_CAP = 0.3;

                boolean isRightFlipperDown = false;
                boolean isLeftFlipperDown = false;

                int collectionSystemDirection = 0;
                int liftMultiplier = 0;

                PIDController liftController = new PIDController(0.005, 0, 0, 0);
                liftController.setTarget(0);

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

                    double rightJoystick = -joystick1.getValue(JoystickController.Y_1);
                    double leftJoystick = -joystick1.getValue(JoystickController.Y_2);

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

                    /** 5. Scoop Servo */

                    if (joystick1.isDpadDown()) {
                        robot.getScoopServo().setPosition(0.5);
                    }
                    if (joystick1.isDpadUp()) {
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


                    double liftPIDOut = liftController.getPIDOutput(robot.getLiftLeftMotorEncoder().getValue() - robot.getLiftRightMotorEncoder().getValue());
                    liftPIDOut /= 2;

                    if ((joystick2.getValue(JoystickController.RT_PRESSURE) > .3) && !(joystick2.getValue(JoystickController.LT_PRESSURE) > .3)) {
                        new MoveLift(MoveLift.RunMode.CONTINUOUS, MoveLift.Direction.OUT).changeRobotState();
                        liftMultiplier = 1;


                    }
                    else if (!(joystick2.getValue(JoystickController.RT_PRESSURE) > .3) && (joystick2.getValue(JoystickController.LT_PRESSURE) > .3)) {
                        new MoveLift(MoveLift.RunMode.CONTINUOUS, MoveLift.Direction.IN).changeRobotState();
                        liftMultiplier = -1;
                    }
                    else {
                        robot.getLiftRightMotor().setPower(0);
                        robot.getLiftLeftMotor().setPower(0);

                        /** 2. Bumpers: Lift Adjustment */
                        //The Lift motor encoder values are being cleared to reset the PID Values
                        if (joystick2.isDown(JoystickController.BUTTON_RB)) {
                            robot.getLiftRightMotor().setPower(liftMultiplier*0.6);

                            robot.getLiftRightMotorEncoder().clearValue();
                            robot.getLiftLeftMotorEncoder().clearValue();
                        }
                        if (joystick2.isDown(JoystickController.BUTTON_LB)) {
                            robot.getLiftLeftMotor().setPower(liftMultiplier*0.6);

                            robot.getLiftRightMotorEncoder().clearValue();
                            robot.getLiftLeftMotorEncoder().clearValue();
                        }
                    }

                    /** 3. Zipline Flippers */
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
                    robot.getFlipperLeftServo().setPosition(isLeftFlipperDown ? 0.2 : 0.8);
                    robot.getFlipperRightServo().setPosition(isRightFlipperDown ? 0.2 : 0.8);



                    /** 4. Dumping the People */
                    if (joystick2.isDown(JoystickController.BUTTON_X)){
                        dumperCurrentPos += dumperDelta;


                    }

                    if (joystick2.isDown(JoystickController.BUTTON_Y)){
                        dumperCurrentPos -= dumperDelta;

                    }

                    dumperCurrentPos = Math.max(Math.min(dumperCurrentPos, 1), 0);
                    robot.getDumperServo().setPosition(dumperCurrentPos);

                    /** 5. Dump Debris */
                    if (joystick2.isPressed(JoystickController.BUTTON_A)) {
                        robot.getScoopServo().setPosition(SCOOP_DOWN);
                        robot.getGateServo().setPosition(GATE_OPEN);
                        robot.getBasketServo().setPosition(BASKET_UP);

                        Thread.sleep(1500);

                        robot.getBasketServo().setPosition(BASKET_DOWN);
                        robot.getGateServo().setPosition(GATE_CLOSED);
                        robot.getScoopServo().setPosition(SCOOP_UP);

                        Thread.sleep(1500);
                    }


                    /** 6. Shields */
                    if (joystick2.isDpadDown()) {
                        robot.getBackShieldServo().setPosition(0);
                    }

                    if (joystick2.isDpadUp()) {
                        robot.getBackShieldServo().setPosition(1);
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
    public double getDriveValue(double x, double sign) {
        return sign * (0.5 * Math.pow((2*x - 1), 3) + 0.5);
    }

    private final double SCOOP_UP = 0.7;
    private final double SCOOP_DOWN = 0;
    private final double GATE_OPEN = 0.6;
    private final double GATE_CLOSED = 0;
    private final double BASKET_UP = 0.6;
    private final double BASKET_DOWN = 0;}