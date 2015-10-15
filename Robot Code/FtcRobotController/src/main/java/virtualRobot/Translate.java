package virtualRobot;

/**
 * Created by shant on 10/14/2015.
 */
public class Translate implements Command {
    private ExitCondition exitCondition;
    private Motor[] motors;
    private Sensor[] encoders;
    private RunMode runMode;

    private PIDController translateController;
    private PIDController headingController;

    private double maxSpeed;
    private double currentValue;

    public Translate() {
        exitCondition = new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                return false;
            }
        };
        motors = AutonomousRobot.driveMotors;

        runMode = RunMode.WITH_PID;

        translateController = new PIDController(KP, KI, KD, THRESHOLD);
        headingController = new PIDController(HEADING_KP, HEADING_KI, HEADING_KD, HEADING_THRESHOLD);

        maxSpeed = 1;
        currentValue = 0;
    }

    public Translate(double target) {
        this();

        translateController.setTarget(target);
    }

    public Translate(double target, double maxSpeed) {
        this(target);

        this.maxSpeed = maxSpeed;
    }

    public void setExitCondition (ExitCondition e) {
        exitCondition = e;
    }

    public ExitCondition getExitCondition() {
        return exitCondition;
    }

    @Override
    public void changeRobotState() {
        switch (runMode) {
            case CUSTOM:

                for (Sensor s : encoders) {
                    s.clearValue();
                }

                for (Motor m : motors) {
                    m.setSpeed(maxSpeed);
                }

                while (!Thread.currentThread().isInterrupted() && !exitCondition.isConditionMet()) {

                }
                break;
            case WITH_ENCODERS:

                for (Sensor s : encoders) {
                    s.clearValue();
                }

                for (Motor m : motors) {
                    m.setSpeed(maxSpeed);
                }

                while (!Thread.currentThread().isInterrupted() && !exitCondition.isConditionMet() && currentValue < translateController.getTarget()) {
                    currentValue = 0;
                    for (Sensor s : encoders) {
                        currentValue += s.getValue();
                    }

                    currentValue /= encoders.length;
                }
                break;
            case WITH_PID:

                for (Sensor s : encoders) {
                    s.clearValue();
                }

                while (!Thread.currentThread().isInterrupted() && !exitCondition.isConditionMet() && currentValue < translateController.getTarget()) {
                    currentValue = 0;
                    for (Sensor s : encoders) {
                        currentValue += s.getValue();
                    }

                    currentValue /= encoders.length;

                    double pidOutput = translateController.getPIDOutput(currentValue);

                    for (Motor m : motors) {
                        m.setSpeed(pidOutput);
                    }
                }

                break;
            default:
                break;
        }

    }

    public void setRunMode(RunMode runMode) {
        this.runMode = runMode;
    }

    public RunMode getRunMode() {
        return runMode;
    }

    public void setTarget(double target) {
        translateController.setTarget(target);
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }


    public enum RunMode {
        CUSTOM,
        WITH_ENCODERS,
        WITH_PID
    }

    public static int KP = 0;
    public static int KI = 0;
    public static int KD = 0;
    public static int THRESHOLD = 0;

    public static int HEADING_KP = 0;
    public static int HEADING_KI = 0;
    public static int HEADING_KD = 0;
    public static int HEADING_THRESHOLD = 0;
}
