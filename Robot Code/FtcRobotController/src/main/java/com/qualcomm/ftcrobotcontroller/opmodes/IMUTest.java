package com.qualcomm.ftcrobotcontroller.opmodes;

import com.kauailabs.navx.ftc.AHRS;
import com.kauailabs.navx.ftc.MPU9250;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Yanjun on 11/24/2015.
 */
public class IMUTest extends OpMode {

    MPU9250 imu;

    @Override
    public void init() {
        imu = MPU9250.getInstance(hardwareMap.deviceInterfaceModule.get("dim"), 0);
    }

    public void start() {
        imu.zeroYaw();
    }

    @Override
    public void loop() {
        telemetry.addData("yaw", imu.getIntegratedYaw());
        telemetry.addData("pitch", imu.getIntegratedPitch());
        telemetry.addData("roll", imu.getIntegratedRoll());
    }
}
