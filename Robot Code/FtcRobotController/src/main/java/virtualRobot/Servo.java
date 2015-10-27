package virtualRobot;
//com.qualcomm.robotcore.hardware.Servo;import com.qualcomm.robotcore.hardware.Servo;


/**
 * Created by Alex on 9/30/2015.
 */
public class Servo {

    int servoPos;

    private volatile double position;
    //0-180


    public synchronized double getPosition() {

        return position;
    }


    public synchronized void setPosition(double position) {

        this.position = position;
    }

}
