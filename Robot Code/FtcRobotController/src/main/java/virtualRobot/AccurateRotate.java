package virtualRobot;

/**
 * Created by shant on 12/6/2015.
 */
public class AccurateRotate extends Rotate {

    public AccurateRotate (double angleInDegrees, double power, String name) {
        super(angleInDegrees, power, name);
        setTHRESHOLD(0.3);
        setTimeLimit(1500);
    }
}
