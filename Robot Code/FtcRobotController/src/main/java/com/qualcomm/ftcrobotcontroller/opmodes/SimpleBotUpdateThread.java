package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import virtualRobot.LogicThread;
import virtualRobot.Motor;
import virtualRobot.SallyJoeBot;

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
 * Created by shant on 11/24/2015.
 */
public abstract class SimpleBotUpdateThread extends OpMode {

    private SallyJoeBot robot;
    protected Class<? extends LogicThread> logicThread;
    private Thread t;

    private DcMotor rightBack, rightFront, leftBack, leftFront;
    private Servo ziplineLeft, ziplineRight, peopleDumper, buttonPusher;
    private Servo frontShieldLeft, frontShieldRight, backShieldLeft, backShieldRight;
    private Servo tapeMeasureRight, tapeMeasureLeft;

    private Motor vDriveLeftMotor, vDriveRightMotor;
    private virtualRobot.Servo vZiplineLeftServo, vZiplineRightServo, vPeopleDumperServo, vButtonPusherServo;
    private virtualRobot.Servo vFrontShieldLeftServo, vFrontShieldRightServo, vBackShieldLeftServo, vBackShieldRightServo;
    private virtualRobot.Servo vTapeMeasureRightServo, vTapeMeasureLeftServo;

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }

    public void stop() {

    }

    public abstract void setLogicThread ();
}
