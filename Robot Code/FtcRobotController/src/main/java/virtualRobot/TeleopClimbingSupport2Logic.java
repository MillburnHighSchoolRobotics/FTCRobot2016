package virtualRobot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shant on 11/14/2015.
 */
public class TeleopClimbingSupport2Logic extends LogicThread {

    @Override
    public void loadCommands() {
        final MoveMotor moveLeftArm = new MoveMotor();
        final MoveMotor moveRightArm = new MoveMotor();

        moveLeftArm.setRunMode(Translate.RunMode.CUSTOM);
        moveLeftArm.setPower(0.75);
        moveLeftArm.setMotor(robot.getArmLeftMotor());
        moveLeftArm.setExitCondition(new ExitCondition() {

            @Override
            public boolean isConditionMet() {
                return elapsedTime < 3000;
            }
        });

        moveRightArm.setRunMode(Translate.RunMode.CUSTOM);
        moveRightArm.setPower(0.75);
        moveRightArm.setMotor(robot.getArmLeftMotor());
        moveRightArm.setExitCondition(new ExitCondition() {

            @Override
            public boolean isConditionMet() {
                return elapsedTime > 3000;
            }
        });

        List<LogicThread> list = new ArrayList<LogicThread>();
        list.add(new LogicThread() {

            @Override
            public void loadCommands() {
                commands.add(moveRightArm);
            }
        });

        commands.add(new SpawnNewThread(list));
        commands.add(moveLeftArm);
    }
}
