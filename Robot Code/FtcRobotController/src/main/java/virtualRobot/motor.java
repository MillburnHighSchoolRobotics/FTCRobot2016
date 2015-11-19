package virtualRobot;


public class Motor {

    private volatile double power;

    public Motor() {
        power = 0;
    }

    public synchronized double getPower () {
        return power;
    }

    public synchronized void setPower(double newPower) {
        power = newPower;
        if (power > 1) {
            power = 1;
        }

        if (power < -1) {
            power = -1;
        }
    }

}