package virtualRobot;

/**
 * Created by shant on 10/27/2015.
 */
public class Rotate implements Command {
    private ExitCondition exitCondition;
    private double ROTATION_TOLERANCE = 0.2;
    private double power;
    private double angleInDegrees;

    public Rotate () {
        exitCondition = new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                return false;
            }
        };
    }

    public Rotate (double angleInDegrees) {
        this();
        this.angleInDegrees = angleInDegrees;
        power = 1;
    }

    public Rotate (double angleInDegrees, double power) {
        this();
        this.angleInDegrees = angleInDegrees;
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
    public void changeRobotState() {
        while (!Thread.currentThread().isInterrupted() && !exitCondition.isConditionMet()) {


            double offset = 0;
            double curTime, prevTime;
            double adjustedPower;
            double prevRot = 0, curRot;
            // pidData;
            float pidOutput;

            if (angleInDegrees < 0) {

                offset = 0;
                curTime = System.currentTimeMillis();
                prevTime = System.currentTimeMillis();

                while (Math.abs(offset - angleInDegrees) > ROTATION_TOLERANCE) {

                    curTime = System.currentTimeMillis() - prevTime;
                    curRot = robot.getAngleSensor().getValue();
                    if (Math.abs(curRot) < 2) {
                        curRot = 0;
                    }
                    offset += 0.5 * (curRot + prevRot) * 0.001 * (curTime - prevTime);
                    prevTime = curTime;
                    prevRot = curRot;

                    //pidOutput = GetPIDOutput(&pidData, abs(offset));
                    //adjustedPower = pidOutput;

                    adjustedPower = power;

                    robot.getLeftMotor().setPower(adjustedPower);

                    robot.getRightMotor().setPower(-adjustedPower);
                }
            } else {
                offset = 0;
                curTime = System.currentTimeMillis();
                prevTime = System.currentTimeMillis();

                while (Math.abs(offset - angleInDegrees) < ROTATION_TOLERANCE) {

                    curTime = System.currentTimeMillis() - prevTime;
                    curRot = robot.getAngleSensor().getValue();
                    if (Math.abs(curRot) < 2) {
                        curRot = 0;
                    }
                    offset += 0.5 * (curRot + prevRot) * 0.001 * (curTime - prevTime);
                    prevTime = curTime;
                    prevRot = curRot;

                    //pidOutput = GetPIDOutput(&pidData, abs(offset));
                    //adjustedPower = pidOutput;

                    adjustedPower = power;

                    robot.getLeftMotor().setPower(-adjustedPower);

                    robot.getRightMotor().setPower(adjustedPower);
                }
            }

            robot.getRightMotor().setPower(0);
            robot.getLeftMotor().setPower(0);


        }


        
    }
}
