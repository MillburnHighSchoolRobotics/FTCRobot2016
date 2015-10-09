package virtualRobot;

/**
 * Created by shant on 10/8/2015.
 *
 * A class that reads in values from the sensors, including motors.
 * All sensors and encoders should extend this class
 */
public abstract class Sensor {
    double hardValue;
    double softValue;

    //Soft clears a sensor or encoder value
    public abstract void clearValue();

    //return the current softValue of the sensor
    public abstract double getRawSoftValue();

    /*
     * Returns a calculated version of the raw value in case it is needed
     * If it is not needed to manipulate the raw value,
     * just call getRawSoftValue() if needed
     */
    public abstract double getCalculatedValue();

}
