package virtualRobot;

public class Motor {
    private volatile double speed;
    //-1 to 1

    public synchronized double getSpeed () {
        return speed;
    }

    public synchronized void setSpeed(double newSpeed) {
        speed = newSpeed;
    }

}