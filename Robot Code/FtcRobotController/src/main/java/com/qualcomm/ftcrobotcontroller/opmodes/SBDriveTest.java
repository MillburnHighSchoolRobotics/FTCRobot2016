package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

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
public class SBDriveTest extends OpMode {
    DcMotor rightFront, rightBack, leftFront, leftBack;
    @Override
    public void init() {
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.REVERSE);

    }

    @Override
    public void loop() {
        double leftPower = -gamepad1.left_stick_y;
        double rightPower = -gamepad1.right_stick_y;
        rightFront.setPower(rightPower);
        rightBack.setPower(rightPower);
        leftFront.setPower(leftPower);
        leftBack.setPower(leftPower);
    }
}
