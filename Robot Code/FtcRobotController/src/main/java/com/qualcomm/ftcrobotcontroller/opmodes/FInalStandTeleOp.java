package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import virtualRobot.gamepad1Controller;

/**
 * Created by shant on 11/15/2015.
 */
public class FinalStandTeleOp extends OpMode {
    private DcMotor rightTop, rightBottom, leftTop, leftBottom, armLeftMotor, armRightMotor, reaper, conveyor;
    private Servo armLeft, armRight, gateLeft, gateRight, spinner, blockerLeft, blockerRight, rampLift;


    final double servoDelta = 0.0023; //0.00115
    final double ARM_TOP_CAP = 0.3;
    final double ARM_BOTTOM_CAP = 0.63;
    final double RIGHT_GATE_CLOSED = 0; //0.5
    final double RIGHT_GATE_DEPOSIT = 0.5;
    final double RIGHT_GATE_ZIPLINE = 0.75;
    final double LEFT_GATE_CLOSED = 0.15; //0.12
    final double LEFT_GATE_DEPOSIT = 0.65;
    final double LEFT_GATE_ZIPLINE = 0.85;
    @Override
    public void init() {
        //MOTOR SETUP
        rightTop = hardwareMap.dcMotor.get("rightTop");
        rightBottom = hardwareMap.dcMotor.get("rightBottom");
        leftTop = hardwareMap.dcMotor.get("leftTop");
        leftBottom = hardwareMap.dcMotor.get("leftBottom");
        armLeftMotor = hardwareMap.dcMotor.get("armLeftMotor");
        armRightMotor = hardwareMap.dcMotor.get("armRightMotor");
        reaper = hardwareMap.dcMotor.get("reaper");
        conveyor = hardwareMap.dcMotor.get("conveyor");


        //SERVO SETUP
        armLeft = hardwareMap.servo.get("armLeft");
        armRight = hardwareMap.servo.get("armRight");
        gateLeft = hardwareMap.servo.get("gateLeft");
        gateRight = hardwareMap.servo.get("gateRight");
        //spinner = hardwareMap.servo.get("spinner");
        blockerLeft = hardwareMap.servo.get("blockerLeft");
        blockerRight = hardwareMap.servo.get("blockerRight");
        rampLift = hardwareMap.servo.get("rampLift");

        //REVERSE RIGHT SIDE
        blockerRight.setDirection(Servo.Direction.REVERSE);
        gateRight.setDirection(Servo.Direction.REVERSE);
        rightTop.setDirection(DcMotor.Direction.REVERSE);
        rightBottom.setDirection(DcMotor.Direction.REVERSE);
        armRight.setDirection(Servo.Direction.REVERSE);
        armRightMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        boolean reaperForwardsOn = false;
        boolean reaperBackwardOn = false;

        while (true) {

            gamepad1.logicalRefresh();

            double currentPos = robot.getArmRightServo().getPosition();
            //SHIELDS
            if (gamepad1.isDpadDown()) { //ONE BUTTON TO LOWER THE SHIELDS
                blockerLeft().setPosition(.8);
                blockerRight().setPosition(.8);
                robot.getRampLift().setPosition(0);
            }

            if (gamepad1.isDpadUp()) { //ONE BUTTON TO RAISE THEM
                robot.getBlockerLeftServo().setPosition(0);
                robot.getBlockerRightServo().setPosition(0);
                robot.getRampLift().setPosition(1);
            }

            if (gamepad1.isDpadRight()) {
                //ONE BUTTON TO RULE THEM ALL
                //AND IN THE DARKNESS BIND THEM
            }

            //DRIVE
            robot.getDriveLeftMotor().setPower(gamepad1.getValue(gamepad1Controller.Y_1));
            robot.getDriveRightMotor().setPower(gamepad1.getValue(gamepad1Controller.Y_2));

            //CONVEYOR BELT + GATE
            if (!gamepad1.isDown(gamepad1Controller.BUTTON_LB) && gamepad1.isDown(gamepad1Controller.BUTTON_RB)) {
                //DEPOSIT TO THE LEFT
                robot.getGateLeftServo().setPosition(LEFT_GATE_DEPOSIT);
                robot.getGateRightServo().setPosition(RIGHT_GATE_CLOSED);
                robot.getConveyorMotor().setPower(-1.0);
            }
            else if (gamepad1.isDown(gamepad1Controller.BUTTON_LB) && !gamepad1.isDown(gamepad1Controller.BUTTON_RB)) {
                //DEPOSIT TO THE RIGHT
                robot.getGateLeftServo().setPosition(LEFT_GATE_CLOSED);
                robot.getGateRightServo().setPosition(RIGHT_GATE_DEPOSIT);
                robot.getConveyorMotor().setPower(1.0);
            }

            //HIT THE ZIPLINE
            else if (gamepad1.isDpadRight()) {
                robot.getGateLeftServo().setPosition(LEFT_GATE_ZIPLINE);
            }

            else if (gamepad1.isDpadLeft()) {
                robot.getGateRightServo().setPosition(RIGHT_GATE_ZIPLINE);
            }

            else {
                robot.getConveyorMotor().setPower(0);
                robot.getGateRightServo().setPosition(RIGHT_GATE_CLOSED);
                robot.getGateLeftServo().setPosition(LEFT_GATE_CLOSED);
            }

            //REAPER
            if (reaperForwardsOn && !reaperBackwardOn) {
                robot.getReaperMotor().setPower(-1);
            }

            if (!reaperForwardsOn && reaperBackwardOn) {
                robot.getReaperMotor().setPower(1);
            }

            if (!reaperForwardsOn && !reaperBackwardOn) {
                robot.getReaperMotor().setPower(0);
            }

                    /*if (reaperForwardsOn || reaperBackwardOn)
                        robot.getReaperMotor().setPower(reaperForwardsOn ? 1: -1);

                    else{robot.getReaperMotor().setPower(0);}
                    */

            if (!gamepad1.isPressed(gamepad1Controller.BUTTON_LT) && gamepad1.isPressed(gamepad1Controller.BUTTON_RT)){
                if (reaperBackwardOn) {
                    reaperBackwardOn = false;
                }
                reaperForwardsOn = !reaperForwardsOn;

                //robot.getReaperMotor().setPower(1);
            }

            else if (gamepad1.isPressed(gamepad1Controller.BUTTON_LT) && !gamepad1.isPressed(gamepad1Controller.BUTTON_RT)) {
                if (reaperForwardsOn){
                    reaperForwardsOn = false;
                }
                reaperBackwardOn = !reaperBackwardOn;

                //robot.getReaperMotor().setPower(-1);
            }




            //ARM ROTATION
            if (!gamepad1.isDown(gamepad1Controller.BUTTON_A) && gamepad1.isDown(gamepad1Controller.BUTTON_B)) {
                currentPos += servoDelta;

                if (currentPos <= ARM_TOP_CAP) currentPos = ARM_TOP_CAP;
                if (currentPos >= ARM_BOTTOM_CAP) currentPos = ARM_BOTTOM_CAP;

                robot.getArmLeftServo().setPosition(currentPos);
                robot.getArmRightServo().setPosition(currentPos);

            }
            if (gamepad1.isDown(gamepad1Controller.BUTTON_A) && !gamepad1.isDown(gamepad1Controller.BUTTON_B)) {
                currentPos -= servoDelta;

                if (currentPos <= ARM_TOP_CAP) currentPos = ARM_TOP_CAP;
                if (currentPos >= ARM_BOTTOM_CAP) currentPos = ARM_BOTTOM_CAP;

                robot.getArmLeftServo().setPosition(currentPos);
                robot.getArmRightServo().setPosition(currentPos);

            }

            //ARM EXTENSION
            if (!gamepad1.isDown(gamepad1Controller.BUTTON_X) && gamepad1.isDown(gamepad1Controller.BUTTON_Y)) {
                double enc = robot.getArmRightMotorEncoder().getValue();

                robot.getArmLeftMotor().setPower(0.75);
                robot.getArmRightMotor().setPower(0.75);
            }
            else if (gamepad1.isDown(gamepad1Controller.BUTTON_X) && !gamepad1.isDown(gamepad1Controller.BUTTON_Y)) {
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
}
