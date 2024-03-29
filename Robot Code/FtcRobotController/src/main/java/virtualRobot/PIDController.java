package virtualRobot;

/**
 * Created by Yanjun on 10/14/2015.
 */
public class PIDController {
    private double kP;
    private double kI;
    private double kD;
    private double threshold;

    private double P;
    private double I;
    private double D;

    private double target;

    public PIDController() {
        kP = 0;
        kI = 0;
        kD = 0;
        threshold = 0;

        P = 0;
        I = 0;
        D = 0;
    }

    public PIDController(double kP, double kI, double kD) {

        this();

        this.kP = kP;
        this.kI = kI;
        this.kD = kD;

        threshold = 0;
    }

    public PIDController(double kP, double kI, double kD, double threshold) {
        this(kP, kI, kD);

        this.threshold = threshold;
    }

    public PIDController(double kP, double kI, double kD, double threshold, double target) {
        this(kP, kI, kD, threshold);

        this.target = target;
    }

    public double getPIDOutput(double currentValue) {
        D = (target-currentValue) - P;
        P = target - currentValue;

        if (Math.abs(currentValue - target) < threshold) {
            I = P + I;
        } else {
            I = 0;
        }

        return kP * P + kI * I + kD * D;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public double getTarget() {
        return target;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public void setKP(double kP) {
        this.kP = kP;
    }

    public void setKI(double kI) {
        this.kI = kI;
    }

    public void setKD(double kD) {
        this.kD = kD;
    }
}
