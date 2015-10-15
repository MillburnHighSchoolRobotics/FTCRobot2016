package virtualRobot;

/**
 * Created by shant on 10/14/2015.
 */
public class Translate implements Command {
    private ExitCondition exitCondition;
    private Motor[] motors;
    static final int MAX = 100;

    public Translate () {
        exitCondition = new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                return false;
            }
        };
        motors = AutonomousRobot.driveMotors;
    }

    public void setExitCondition (ExitCondition e) {
        exitCondition = e;
    }

    public ExitCondition getExitCondition () {
        return exitCondition;
    }

    private double rotateRaw(double angleInDegrees, double timeout) {
        double offset;
        double prevTime, curTime;

        offset = 0;
        prevTime = System.currentTimeMillis();
        curTime = prevTime;

        if (angleInDegrees < 0) {
            motors[0].setSpeed(100);
            motors[1].setSpeed(100);
            motors[2].setSpeed(-100);
            motors[3].setSpeed(-100);

            while (offset > angleInDegrees) {
                //update offset in here once sensors are in
                curTime = System.currentTimeMillis() - prevTime;
                if (curTime > timeout) {
                    motors[0].setSpeed(0);
                    motors[1].setSpeed(0);
                    motors[2].setSpeed(0);
                    motors[3].setSpeed(0);

                    return offset;
                }
            }
        }

        else {
            motors[0].setSpeed(-100);
            motors[1].setSpeed(-100);
            motors[2].setSpeed(100);
            motors[3].setSpeed(100);

            while (offset < angleInDegrees) {
                curTime = System.currentTimeMillis() - prevTime;
                //update offset in here once sensors are connected
                if (curTime > timeout) {
                    motors[0].setSpeed(0);
                    motors[1].setSpeed(0);
                    motors[2].setSpeed(0);
                    motors[3].setSpeed(0);

                    return offset;
                }
            }
        }

        motors[0].setSpeed(0);
        motors[1].setSpeed(0);
        motors[2].setSpeed(0);
        motors[3].setSpeed(0);


        return offset;
    }

    private double

    @Override
    public void changeRobotState() {

    }
}
