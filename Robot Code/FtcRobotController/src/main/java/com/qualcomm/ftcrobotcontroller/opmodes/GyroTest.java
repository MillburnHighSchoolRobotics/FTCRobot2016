package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Yanjun on 10/20/2015.
 */
public class GyroTest extends OpMode {

    GyroSensor gyro;
    double gyroOffset = 0;

    @Override
    public void init() {
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyroOffset = gyro.getRotation();
    }

    @Override
    public void loop() {
        telemetry.addData("Gyro", gyro.getRotation() - gyroOffset);
    }
}
