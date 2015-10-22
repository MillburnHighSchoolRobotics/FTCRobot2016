package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by shant on 10/20/2015.
 */
public class AutonomousTestOp extends LinearOpMode {

    DcMotor rightTop;
    DcMotor rightBottom;
    DcMotor leftTop;
    DcMotor leftBottom;
    GyroSensor gyroSensor;




    @Override
    public void runOpMode() throws InterruptedException {
        rightTop = hardwareMap.dcMotor.get("rightTop");
        rightBottom = hardwareMap.dcMotor.get("rightBottom");
        leftTop = hardwareMap.dcMotor.get("leftTop");
        leftBottom = hardwareMap.dcMotor.get("leftBottom");
        gyroSensor = hardwareMap.gyroSensor.get("gyro");

        leftTop.setDirection(DcMotor.Direction.REVERSE);
        leftBottom.setDirection(DcMotor.Direction.REVERSE);



        waitForStart();

        moveStraight(2500);

        try {
            RotateHP(-45, -1);
        }
        catch (InterruptedException E){

        }

        moveStraight(-3000);



    }

    private double RotateHP(double angleInDegrees, double timeout) throws InterruptedException {
        double offset = 0;
        double prevTime = System.currentTimeMillis();
        double curTime;
        double gyroOffset = gyroSensor.getRotation();

        if (angleInDegrees < 0) {
            setMotors(1, -1);
            while (offset > angleInDegrees) {
                double gyroValue = gyroSensor.getRotation() - gyroOffset;
                if (Math.abs(gyroValue) <= 2){
                    gyroValue = 0;
                }

                curTime = System.currentTimeMillis();
                offset += gyroValue * 0.001 * (curTime - prevTime);
                prevTime = curTime;

                if (timeout != -1 && this.getRuntime() > timeout) {
                    setMotors(0, 0);
                    return offset;
                }
                waitOneFullHardwareCycle();
            }

        } else {
            setMotors(-1, 1);
            while (offset < angleInDegrees) {
                double gyroValue = gyroSensor.getRotation() - gyroOffset;
                if (Math.abs(gyroValue) <= 2){
                    gyroValue = 0;
                }

                curTime = System.currentTimeMillis();
                offset += gyroValue * 0.001 * (curTime - prevTime);
                prevTime = curTime;

                if (timeout != -1 && this.getRuntime() > timeout) {
                    setMotors(0, 0);
                    return offset;
                }
                waitOneFullHardwareCycle();
            }
        }
        setMotors(0, 0);
        return offset;
    }

    private void setMotors(double rightPower, double leftPower) {
        rightTop.setPower(rightPower);
        rightBottom.setPower(rightPower);
        leftTop.setPower(leftPower);
        leftBottom.setPower(leftPower);
    }

    private double[] getAvgEncoderValues() {
        //returns average encoder values [right, left]
        return new double [] {(rightTop.getCurrentPosition() + rightBottom.getCurrentPosition())/-2.0,
                                (leftTop.getCurrentPosition() + leftBottom.getCurrentPosition())/-2.0};
    }

    private boolean moveStraight (int encoderClicks) {
        while (getAvgEncoderValues()[0] < encoderClicks && getAvgEncoderValues()[1] < encoderClicks) {
            setMotors(1, 1);
        }
        return true;
    }
}
