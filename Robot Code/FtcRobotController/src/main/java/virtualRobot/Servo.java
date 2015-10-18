package virtualRobot;
//com.qualcomm.robotcore.hardware.Servo;import com.qualcomm.robotcore.hardware.Servo;


/**
 * Created by Alex on 9/30/2015.
 */
public class Servo {
<<<<<<< HEAD
    int servoPos;
=======
    private volatile double position;
    //0-180
>>>>>>> bae0c566d25540dddae9abeb92a4cb3a123fd744

    public synchronized double getPosition() {
        return position;
    }

<<<<<<< HEAD
=======
    public synchronized void setPosition(double position) {
        this.position = position;
    }

>>>>>>> bae0c566d25540dddae9abeb92a4cb3a123fd744
}
