package virtualRobot;

<<<<<<< HEAD
public class motor {
    private volatile double rawposition;
    private volatile double softposition;

    public motor() {

        rawposition = 0; softposition = 0;
    }
    public synchronized void updatePosition(double newPos) {
        rawposition = newPos;
        softposition= newPos;
    }
    public synchronized void addPosition(double addPos){
        rawposition+= addPos;
        softposition+= addPos;
    }
    public double getInches(){
        //calcinches using softpos and return
        double inches = 0;
        return inches;
    }
    public synchronized void resetPos() {
        softposition=0;
    }
    public synchronized void resetPos(double startPos){
        softposition=startPos;
    }
=======
public class Motor {
    private volatile double speed;
    //-1 to 1

    public synchronized double getSpeed () {
        return speed;
    }

    public synchronized void setSpeed(double newSpeed) {
        speed = newSpeed;
    }

>>>>>>> bae0c566d25540dddae9abeb92a4cb3a123fd744
}