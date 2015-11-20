package virtualRobot;

/**
 * Created by shant on 10/27/2015.
 */
public class Rotate implements Command {
    private ExitCondition exitCondition;
    private double THRESHOLD = 0.2;
    private double KP = 0.1;
    private double KI = 0;
    private double KD = 0;
    private double power;
    private double angleInDegrees;
    
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

    	robot.getDriveRightMotor().setPower(0);
        robot.getDriveLeftMotor().setPower(0);
        
        return isInterrupted;
        
    }
}
