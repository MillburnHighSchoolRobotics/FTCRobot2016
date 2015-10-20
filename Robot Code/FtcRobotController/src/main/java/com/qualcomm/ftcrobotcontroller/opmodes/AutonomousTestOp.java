package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by shant on 10/20/2015.
 */
public class AutonomousTestOp extends OpMode {

    DcMotor rightTop;
    DcMotor rightBottom;
    DcMotor leftTop;
    DcMotor leftBottom;
    GyroSensor gyroSensor;




    @Override
    public void init() {
        rightTop = hardwareMap.dcMotor.get("rightTop");
        rightBottom = hardwareMap.dcMotor.get("rightBottom");
        leftTop = hardwareMap.dcMotor.get("leftTop");
        leftBottom = hardwareMap.dcMotor.get("leftBottom");
        gyroSensor = hardwareMap.gyroSensor.get("gyro");

        leftTop.setDirection(DcMotor.Direction.REVERSE);
        leftBottom.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        RotateRaw(90, -1);
    }

    private double RotateRaw(double angleInDegrees, double timeout) {
        double offset;
        offset = 0;
        if (angleInDegrees < 0) {
            setMotors(1, -1);
            while (offset > angleInDegrees) {
                offset = gyroSensor.getRotation();
                if (timeout != -1 && this.getRuntime() > timeout) {
                    setMotors(0, 0);
                    return offset;
                }
            }

        } else {
            setMotors(-1, 1);
            while (offset < angleInDegrees) {
                offset = gyroSensor.getRotation();
                if (timeout != -1 && this.getRuntime() > timeout) {
                    setMotors(0,0);
                    return offset;
                }
            }
        }
        setMotors(0,0);
        return offset;
    }

    private void setMotors(double rightPower, double leftPower) {
        rightTop.setPower(rightPower);
        rightBottom.setPower(rightPower);
        leftTop.setPower(leftPower);
        leftBottom.setPower(leftPower);
    }
}
