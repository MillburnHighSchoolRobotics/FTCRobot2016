package virtualRobot;

/**
 * Created by Alex on 10/1/2015.
 */
public class ContinuousRotationServo extends Servo {
    // position is speed for CRS - no need for new variable
    // speed should be from -1 to 1
    public synchronized double getSpeed () {
        return (getPosition() - 90) / 90.0;
    }

    public synchronized void setSpeed (double speed) {
        setPosition((speed + 1) * 180);
    }
}
