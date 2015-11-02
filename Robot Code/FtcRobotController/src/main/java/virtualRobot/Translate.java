package virtualRobot;

/**
 * Created by shant on 10/14/2015.
 */
public class Translate implements Command {
    private ExitCondition exitCondition;
    static final int MAX = 100;
    private RunMode runMode;
    private Direction direction;

    private PIDController translateController;
    private PIDController headingController;

    private double maxSpeed;
    private double currentValue;
    private double multiplier;

    public Translate() {
        exitCondition = new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                return false;
            }
        };

        runMode = RunMode.WITH_PID;

        translateController = new PIDController(KP, KI, KD, THRESHOLD);
        headingController = new PIDController(HEADING_KP, HEADING_KI, HEADING_KD, HEADING_THRESHOLD);

        maxSpeed = 1;
        currentValue = 0;
        direction = Direction.FORWARD;
        multiplier = 1;
    }

    public Translate(double target) {
        this();

        translateController.setTarget(target);
    }

    public Translate(double target, Direction direction, double maxSpeed) {
        this(target, direction);

        this.maxSpeed = maxSpeed;
    }
    
    public Translate(double target, Direction direction) {
    	this(target);
    	
    	this.direction = direction;
    	
    	multiplier = (direction == Direction.FORWARD ? 1 : -1);
    }

    public void setExitCondition (ExitCondition e) {
        exitCondition = e;
    }

    public ExitCondition getExitCondition () {
        return exitCondition;
    }

    @Override
    public boolean changeRobotState() {
    	
    	boolean isInterrupted = false;
    	
        switch (runMode) {
            case CUSTOM:
            	
            	robot.getLeftMotor().setPower(maxPower * multiplier);
            	robot.getRightMotor().setPower(maxPower * multiplier);

                while (!exitCondition.isConditionMet()) {
                	
                	if (Thread.currentThread().isInterrupted()) {
                		isInterrupted = true;
                		break;
                	}
                	
                	Thread.currentThread().sleep(25);
                }
                
                break;
            case WITH_ENCODERS:
            	
            	robot.getLeftMotorEncoder().clearValue();
            	robot.getRightMotorEncoder().clearValue();
            	
            	robot.getLeftMotor().setPower(maxPower * multiplier);
            	robot.getRightMotor().setPower(maxPower * multiplier);
            	
            	while (!exitCondition.isConditionMet() && currentValue < translateController.getTarget()) {
            		
            		currentValue = Math.abs((robot.getLeftMotorEncoder().getValue() + robot.getRightMotorEncoder.getValue()) / 2);
            		
            		if (Thread.currentThread().isInterrupted()) {
            			isInterrupted = true;
            			break;
            		}
            		
            		Thread.currentThread().sleep(25);
            	}
            	
                break;
            case WITH_PID:

            	robot.getLeftMotorEncoder().clearValue();
            	robot.getRightMotorEncoder().clearValue();

                while (!Thread.currentThread().isInterrupted() && !exitCondition.isConditionMet() && Math.abs(currentValue - translateController.getTarget()) > TOLERANCE) {
                   
                    double left = robot.getLeftMotorEncoder().getValue();
                    double right = robot.getRightMotorEncoder().getValue();
                    
                    currentValue = Math.abs((left + right) / 2);

                    double pidOutput = translateController.getPIDOutput(currentValue);

                    robot.getRightMotor().setPower(pidOutput * multiplier);
                    robot.getLeftMotor().setPower(pidOutput * multiplier);
                    
                    if (Thread.currentThread().isInterrupted()) {
                    	isInterrupted = true;
                    	break;
                    }
                    
                    Thread.currentThread().sleep(25);
                    
                }

                break;
            default:
                break;
        }
        
        robot.getLeftMotor().setPower(0);
        robot.getRightMotor().setPower(0);
        
        return isInterrupted;

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
    
    public void setDirection(Direction direction) {
    	this.direction = direction;
    	this.multiplier = (direction == Direction.FORWARD ? 1 : -1);
    }

    public enum RunMode {
        CUSTOM,
        WITH_ENCODERS,
        WITH_PID
    }
    
    public enum Direction {
    	FORWARD,
    	BACKWARD
    }

    public static int KP = 0;
    public static int KI = 0;
    public static int KD = 0;
    public static int THRESHOLD = 0;

    public static int HEADING_KP = 0;
    public static int HEADING_KI = 0;
    public static int HEADING_KD = 0;
    public static int HEADING_THRESHOLD = 0;
    
    public static int TOLERANCE = 20;
}
