package virtualRobot;

import android.util.Log;

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

    private double maxPower;
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

        maxPower = 1;
        currentValue = 0;
        direction = Direction.FORWARD;
        multiplier = 1;
    }

    public Translate(double target) {
        this();

        translateController.setTarget(target);
    }

    public Translate(double target, Direction direction, double maxPower) {
        this(target, direction);

        this.maxPower = maxPower;
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
    public boolean changeRobotState() throws InterruptedException {
    	
    	boolean isInterrupted = false;
    	
        switch (runMode) {
            case CUSTOM:
            	
            	robot.getDriveLeftMotor().setPower(maxPower * multiplier);
            	robot.getDriveRightMotor().setPower(maxPower * multiplier);

                while (!exitCondition.isConditionMet()) {
                	
                	if (Thread.currentThread().isInterrupted()) {
                		isInterrupted = true;
                		break;
                	}
                	
                	try {
                		Thread.currentThread().sleep(25);
                	} catch (InterruptedException e) {
                		isInterrupted = true;
                		break;
                	}
                }
                
                break;
            case WITH_ENCODERS:
            	
            	robot.getDriveLeftMotorEncoder().clearValue();
            	robot.getDriveRightMotorEncoder().clearValue();
            	
            	robot.getDriveLeftMotor().setPower(maxPower * multiplier);
            	robot.getDriveRightMotor().setPower(maxPower * multiplier);
            	
            	while (!exitCondition.isConditionMet() && currentValue < translateController.getTarget()) {
            		
            		currentValue = Math.abs((Math.abs(robot.getDriveLeftMotorEncoder().getValue()) + Math.abs(robot.getDriveRightMotorEncoder().getValue())) / 2);
            		
            		if (Thread.currentThread().isInterrupted()) {
            			isInterrupted = true;
            			break;
            		}
            		
            		try {
                		Thread.currentThread().sleep(25);
                	} catch (InterruptedException e) {
                		isInterrupted = true;
                		break;
                	}
            	}
            	
                break;
            case WITH_PID:

            	robot.getDriveLeftMotorEncoder().clearValue();
            	robot.getDriveRightMotorEncoder().clearValue();

                while (!Thread.currentThread().isInterrupted() && !exitCondition.isConditionMet() && Math.abs(currentValue - translateController.getTarget()) > TOLERANCE) {
                   
                    double left = Math.abs(robot.getDriveLeftMotorEncoder().getValue());
                    double right = Math.abs(robot.getDriveRightMotorEncoder().getValue());

                    currentValue = Math.abs((left + right) / 2);

                    double pidOutput = translateController.getPIDOutput(currentValue);

                    Log.d("pidoutput", Double.toString(pidOutput));

                    robot.getDriveRightMotor().setPower(pidOutput * multiplier);
                    robot.getDriveLeftMotor().setPower(pidOutput * multiplier);
                    
                    if (Thread.currentThread().isInterrupted()) {
                    	isInterrupted = true;
                    	break;
                    }
                    
                    try {
                		Thread.currentThread().sleep(10);
                	} catch (InterruptedException e) {
                		isInterrupted = true;
                		break;
                	}
                    
                }

                break;
            default:
                break;
        }
        
        robot.getDriveLeftMotor().setPower(0);
        robot.getDriveRightMotor().setPower(0);
        
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

    public void setMaxPower(double maxPower) {
        this.maxPower = maxPower;
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

    public static double KP = 0.0045;
    public static double KI = 0.0000;
    public static double KD = 0.010125;
    public static double THRESHOLD = 1000;

    public static int HEADING_KP = 0;
    public static int HEADING_KI = 0;
    public static int HEADING_KD = 0;
    public static int HEADING_THRESHOLD = 0;
    
    public static int TOLERANCE = 20;
}
