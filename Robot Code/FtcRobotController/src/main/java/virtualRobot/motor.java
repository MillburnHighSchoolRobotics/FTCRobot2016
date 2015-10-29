package virtualRobot;


public class Motor {
    private volatile double rawposition;
    private volatile double softposition;

    public Motor() {

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


    private volatile double power;
    //-1 to 1

    public synchronized double getPower () {
        return power;
    }

    public synchronized void setPower(double newPower) {
        power = newPower;
    }

}