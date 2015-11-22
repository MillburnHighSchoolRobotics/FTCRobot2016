package virtualRobot;

/**
 * Created by shant on 10/27/2015.
 */
public class Rotate implements Command {
    private ExitCondition exitCondition;
    private double THRESHOLD = 1;
    private double KP = 0.3;
    private double KI = 0;
    private double KD = 0;
    private double power;
    private double angleInDegrees;
    private RunMode runMode;
    
    private PIDController pidController;

    private static AutonomousRobot robot = Command.AUTO_ROBOT;

    public Rotate () {
    	
    	power = 1;
    	
        exitCondition = new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                return false;
            }
        };
        
        pidController = new PIDController(KP, KI, KD, THRESHOLD);

        runMode = RunMode.WITH_ENCODER;
    }

    public Rotate (double target) {
        this();
        this.angleInDegrees = target;
        
        pidController.setTarget(target);
    }

    public Rotate (double angleInDegrees, double power) {
        this(angleInDegrees);
        this.power = power;
    }


    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getAngleInDegrees() {
        return angleInDegrees;
    }

    public void setAngleInDegrees(double angleInDegrees) {
        this.angleInDegrees = angleInDegrees;
    }


    public void setExitCondition (ExitCondition e) {
        exitCondition = e;
    }


    public ExitCondition getExitCondition () {
        return exitCondition;
    }

    @Override
    public boolean changeRobotState() throws InterruptedException{
    	boolean isInterrupted = false;

        switch (runMode) {
            case WITH_ANGLE_SENSOR:
                while (!exitCondition.isConditionMet() && Math.abs(angleInDegrees - robot.getAngleSensor().getValue()) > THRESHOLD) {

                    double adjustedPower = pidController.getPIDOutput(robot.getAngleSensor().getValue()) * power;

                    robot.getDriveLeftMotor().setPower(adjustedPower);
                    robot.getDriveRightMotor().setPower(-adjustedPower);

                    if (Thread.currentThread().isInterrupted()) {
                        isInterrupted = true;
                        break;
                    }

                    Thread.currentThread().sleep(10);
                }
                break;
            case WITH_ENCODER:
                robot.getDriveLeftMotorEncoder().clearValue();
                robot.getDriveRightMotorEncoder().clearValue();
                while (!exitCondition.isConditionMet() && Math.abs(Math.abs(pidController.getTarget()) - (Math.abs(robot.getDriveLeftMotorEncoder().getValue()) + Math.abs(robot.getDriveRightMotorEncoder().getValue())) / 2) > 20){
                    robot.getDriveLeftMotor().setPower(Math.signum(angleInDegrees)*power);
                    robot.getDriveRightMotor().setPower(-Math.signum(angleInDegrees)*power);

                    if (Thread.currentThread().isInterrupted()) {
                        isInterrupted = true;
                        break;
                    }

                    Thread.currentThread().sleep(10);
                }
                break;
        }

    	robot.getDriveRightMotor().setPower(0);
        robot.getDriveLeftMotor().setPower(0);
        
        return isInterrupted;
        
    }

    public enum RunMode {
        WITH_ANGLE_SENSOR,
        WITH_ENCODER
    }
}
