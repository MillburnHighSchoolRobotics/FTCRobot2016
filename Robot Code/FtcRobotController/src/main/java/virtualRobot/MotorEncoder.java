package virtualRobot;

/**
 * Created by shant on 10/14/2015.
 */
public class MotorEncoder extends Sensor {
    double offset;


    public MotorEncoder () {
        super();
    }

    //Soft clears a sensor or encoder value
    public void clearValue() {
        offset = hardValue;
    }

    //return the current softValue of the sensor
    public double getValue() {
        return hardValue - offset;
    }

}
