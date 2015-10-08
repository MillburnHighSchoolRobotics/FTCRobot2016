package virtualRobot;

/**
 * Created by Alex on 10/1/2015.
 */
public class ContinuousRotationServo extends Servo {
    int crsChangeDirection;
    int servoSpeed;
    public int getServoSpeed() {
    servoSpeed = servoPos;
        return getServoSpeed();

    }
    //idk if we will need this
    public int getCrsChangeDirection() {
        crsChangeDirection = -servoSpeed;
        return crsChangeDirection;
    }
}
