package virtualRobot;

/**
 * Created by shant on 10/8/2015.
 *
 * A class that reads in values from the sensors, including motors.
 * All sensors and encoders should extend this class
 */
public class Sensor {
    double hardValue;
    double offset;

    //Soft clears a sensor or encoder value
    public void clearValue() {
        offset = hardValue;
    }

    //return the current softValue of the sensor
    public double getValue() {
        return hardValue - offset;
    }

    //allows the UpdateThread to set the HardValue
    public void setRawValue(double hardValue) {
        this.hardValue = hardValue;
    }

}
