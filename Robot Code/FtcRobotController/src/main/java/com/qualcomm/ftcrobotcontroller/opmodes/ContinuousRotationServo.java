package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Alex on 10/1/2015.
 */
public class ContinuousRotationServo extends Servo {
    int crsChangeDirection;
    @Override
    public int getServoSpeed() {

        return super.getServoSpeed();

    }
    public int getCrsChangeDirection() {
        crsChangeDirection = -servoSpeed;
        return crsChangeDirection;
    }
}
