package virtualRobot;

/**
 * Created by shant on 10/27/2015.
 */
public class Rotate implements Command {
    private ExitCondition exitCondition;
    private double ROTATIONAL_THRESHOLD = 0.2;
    private double power;
    private double angleInDegrees;
    
    private PIDController pidController;

    public Rotate () {
    	
    	power = 1;
    	
        exitCondition = new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                return false;
            }
        };
        
        //pidController = new PIDController(KP, KI, KD, THRESHOLD);
    }

    public Rotate (double angleInDegrees) {
        this();
        this.angleInDegrees = angleInDegrees;
        
        //pidController.setTarget(target);
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
    	
        while (!exitCondition.isConditionMet() && Math.abs(angleInDegrees - robot.getAngleSensor().getValue()) > ROTATIONAL_THRESHOLD) {
        	
        	double adjustedPower = pidController.getPIDOutput(robot.getAngleSensor().getValue()) * power;
        	
            if (angleInDegrees < 0){

                robot.getDriveLeftMotor().setPower(adjustedPower);
                robot.getDriveRightMotor().setPower(-adjustedPower);
                
            } else {

                robot.getDriveLeftMotor().setPower(-adjustedPower);
                robot.getDriveRightMotor().setPower(adjustedPower);
            }
            
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
