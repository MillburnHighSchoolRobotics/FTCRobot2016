package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by shant on 2/14/2016.
 */
public class LiftTestBoth extends OpMode {
    DcMotor left;
    DcMotor right;
    int initLeftEncoder;
    int initRightEncoder;

    @Override
    public void init() {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        initLeftEncoder = left.getCurrentPosition();
        initRightEncoder = right.getCurrentPosition();
    }

    @Override
    public void loop() {
        if (gamepad1.a && !(gamepad1.a && gamepad1.b)) {
            left.setPower(-0.8);
            right.setPower(0.8);
        }
        else if (gamepad1.b && !(gamepad1.a && gamepad1.b)) {
            left.setPower(0.8);
            right.setPower(-0.8);
        }
        else {
            left.setPower(0);
            right.setPower(0);
        }

        telemetry.addData("left encoder", left.getCurrentPosition() - initLeftEncoder);
        telemetry.addData("right encoder", right.getCurrentPosition() - initRightEncoder);
    }
}
