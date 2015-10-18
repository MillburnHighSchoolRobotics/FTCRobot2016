package virtualRobot;

/**
 * Created by shant on 10/8/2015.
 *
 * A class that reads in values from the sensors, including motors.
 * All sensors and encoders should extend this class
 * IMU, Motor Encoders, Color Sensor, etc should use this class
 */
public class Sensor {
    private volatile double hardValue;
    private volatile double offset;


    //Soft clears a sensor or encoder value
    public synchronized void clearValue() {
        offset = hardValue;
    }

    //return the current softValue of the sensor
    public synchronized double getValue() {
        return hardValue - offset;
    }

    //allows the UpdateThread to set the HardValue
    public synchronized void setRawValue(double hardValue) {
        this.hardValue = hardValue;
    }

}
