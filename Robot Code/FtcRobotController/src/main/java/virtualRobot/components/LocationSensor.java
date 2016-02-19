package virtualRobot.components;

import virtualRobot.*;

/**
 * Created by shant on 2/8/2016.
 */
public class LocationSensor extends Sensor {

    private Location location;

    public LocationSensor() {
        location = new Location();
    }

    public synchronized double getX () {
        synchronized (this) {
            return location.getX();
        }
    }

    public synchronized void setX (double newX) {
        synchronized (this) {
            location.setX(newX);
        }
    }

    public synchronized double getY() {
        synchronized (this) {
            return location.getY();
        }
    }

    public synchronized void setY (double newY) {
        synchronized (this) {
            location.setY(newY);
        }
    }

    public synchronized double getAngle() {
        synchronized (this) {
            return location.getAngle();
        }
    }

    public synchronized void setAngle (double newAngle) {
        synchronized (this) {
            location.setAngle(newAngle);
        }
    }
}
