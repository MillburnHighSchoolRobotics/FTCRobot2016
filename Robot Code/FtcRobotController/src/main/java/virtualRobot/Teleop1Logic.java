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

        final double servoDelta = 0.0023; //0.00115
        final double ARM_TOP_CAP = 0.3;
        final double ARM_BOTTOM_CAP = 0.63;
        final double RIGHT_GATE_CLOSED = 0; //0.5
        final double RIGHT_GATE_DEPOSIT = 0.5;
        final double RIGHT_GATE_ZIPLINE = 0.75;
        final double LEFT_GATE_CLOSED = 0.15; //0.12
        final double LEFT_GATE_DEPOSIT = 0.65;
        final double LEFT_GATE_ZIPLINE = 0.85;


        commands.add(new Command() {
            @Override
            public boolean changeRobotState() {
                JoystickEvent joystick = robot.getJoystickController();
                robot.getArmRightMotorEncoder().clearValue();
                boolean reaperForwardsOn = false;
                boolean reaperBackwardOn = false;

                while (true) {
                    joystick = robot.getJoystickController();
                    if (joystick == null) continue;
                    
                    double currentPos = robot.getArmRightServo().getPosition();
                    //SHIELDS
                    if (joystick.dpad_up) { //ONE BUTTON TO LOWER THE SHIELDS
                        robot.getBlockerLeftServo().setPosition(.8);
                        robot.getBlockerRightServo().setPosition(.8);
                        robot.getRampLift().setPosition(0);
                    }

                    if (joystick.dpad_down) { //ONE BUTTON TO RAISE THEM
                        robot.getBlockerLeftServo().setPosition(0);
                        robot.getBlockerRightServo().setPosition(0);
                        robot.getRampLift().setPosition(1);
                    }

                    if (joystick.dpad_right) {
                        //ONE BUTTON TO RULE THEM ALL
                        //AND IN THE DARKNESS BIND THEM
                    }

                    //DRIVE
                    robot.getDriveLeftMotor().setPower(joystick.stickValues[JoystickController.Y_1]);
                    robot.getDriveRightMotor().setPower(joystick.stickValues[JoystickController.Y_2]);

                    //CONVEYOR BELT + GATE
                    if (!joystick.buttonStates[JoystickController.BUTTON_LB] && joystick.buttonStates[JoystickController.BUTTON_RB]) {
                        //DEPOSIT TO THE LEFT
                        robot.getGateLeftServo().setPosition(LEFT_GATE_DEPOSIT);
                        robot.getGateRightServo().setPosition(RIGHT_GATE_CLOSED);
                        robot.getConveyorMotor().setPower(-1.0);
                    }
                    else if (joystick.buttonStates[JoystickController.BUTTON_LB] && !joystick.buttonStates[JoystickController.BUTTON_RB]) {
                        //DEPOSIT TO THE RIGHT
                        robot.getGateLeftServo().setPosition(LEFT_GATE_CLOSED);
                        robot.getGateRightServo().setPosition(RIGHT_GATE_DEPOSIT);
                        robot.getConveyorMotor().setPower(1.0);
                    }

                    //HIT THE ZIPLINE
                    else if (joystick.dpad_right) {
                        robot.getGateLeftServo().setPosition(LEFT_GATE_ZIPLINE);
                    }

                    else if (joystick.dpad_left) {
                        robot.getGateRightServo().setPosition(RIGHT_GATE_ZIPLINE);
                    }

                    else {
                        robot.getConveyorMotor().setPower(0);
                        robot.getGateRightServo().setPosition(RIGHT_GATE_CLOSED);
                        robot.getGateLeftServo().setPosition(LEFT_GATE_CLOSED);
                    }
/*
                    //REAPER
                    if (reaperForwardsOn && !reaperBackwardOn) {
                        robot.getReaperMotor().setPower(-1);
                    }

                    if (!reaperForwardsOn && reaperBackwardOn) {
                        robot.getReaperMotor().setPower(1);
                    }

                    if (!reaperForwardsOn && !reaperBackwardOn) {
                        robot.getReaperMotor().setPower(0);
                    }*/

                    /*if (reaperForwardsOn || reaperBackwardOn)
                        robot.getReaperMotor().setPower(reaperForwardsOn ? 1: -1);

                    else{robot.getReaperMotor().setPower(0);}
                    */

                    if (!joystick.buttonStates[JoystickController.BUTTON_LT] && joystick.buttonStates[JoystickController.BUTTON_RT]){
                        robot.getReaperMotor().setPower(1);
                    }

                    else if (joystick.buttonStates[JoystickController.BUTTON_LT] && !joystick.buttonStates[JoystickController.BUTTON_RT]) {
                       robot.getReaperMotor().setPower(-1);
                    }

                    else {
                        robot.getReaperMotor().setPower(0);
                    }




                    //ARM ROTATION
                    if (!joystick.buttonStates[JoystickController.BUTTON_A] && joystick.buttonStates[JoystickController.BUTTON_B]) {
                        currentPos += servoDelta;

                        if (currentPos <= ARM_TOP_CAP) currentPos = ARM_TOP_CAP;
                        if (currentPos >= ARM_BOTTOM_CAP) currentPos = ARM_BOTTOM_CAP;

                        robot.getArmLeftServo().setPosition(currentPos);
                        robot.getArmRightServo().setPosition(currentPos);

                    }
                    if (joystick.buttonStates[JoystickController.BUTTON_A] && !joystick.buttonStates[JoystickController.BUTTON_B]) {
                        currentPos -= servoDelta;

                        if (currentPos <= ARM_TOP_CAP) currentPos = ARM_TOP_CAP;
                        if (currentPos >= ARM_BOTTOM_CAP) currentPos = ARM_BOTTOM_CAP;

                        robot.getArmLeftServo().setPosition(currentPos);
                        robot.getArmRightServo().setPosition(currentPos);

                    }

                    //ARM EXTENSION
                    if (!joystick.buttonStates[JoystickController.BUTTON_X] && joystick.buttonStates[JoystickController.BUTTON_Y]) {
                        double enc = robot.getArmRightMotorEncoder().getValue();

                        robot.getArmLeftMotor().setPower(0.75);
                        robot.getArmRightMotor().setPower(0.75);
                    }
                    else if (joystick.buttonStates[JoystickController.BUTTON_X] && !joystick.buttonStates[JoystickController.BUTTON_Y]) {
                        double enc = robot.getArmRightMotorEncoder().getValue();

                        robot.getArmLeftMotor().setPower(-0.75);
                        robot.getArmRightMotor().setPower(-0.75);
                    }

                    else {
                        robot.getArmLeftMotor().setPower(0);
                        robot.getArmRightMotor().setPower(0);
                    }


                    try {
                        Thread.currentThread().sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        });
    }
}
