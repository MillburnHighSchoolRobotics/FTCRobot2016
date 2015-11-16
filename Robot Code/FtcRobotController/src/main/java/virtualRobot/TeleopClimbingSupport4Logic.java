package virtualRobot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shant on 11/14/2015.
 */
public class TeleopClimbingSupport4Logic extends LogicThread {
    @Override
    public void loadCommands() {
        final MoveMotor moveLeftArm = new MoveMotor();
        final MoveMotor moveRightArm = new MoveMotor();
        final Translate moveBackward = new Translate();

        moveLeftArm.setRunMode(Translate.RunMode.CUSTOM);
        moveLeftArm.setPower(-0.75);
        moveLeftArm.setMotor(robot.getArmLeftMotor());
        moveLeftArm.setExitCondition(new ExitCondition() {

            @Override
            public boolean isConditionMet() {
                return elapsedTime > 3000;
            }
        });

        moveRightArm.setRunMode(Translate.RunMode.CUSTOM);
        moveRightArm.setPower(-0.75);
        moveRightArm.setMotor(robot.getArmLeftMotor());
        moveRightArm.setExitCondition(new ExitCondition() {

            @Override
            public boolean isConditionMet() {
                return elapsedTime > 3000;
            }
        });

        List<LogicThread> logicList1 = new ArrayList<LogicThread>();
        List<LogicThread> logicList2 = new ArrayList<LogicThread>();

        logicList1.add(new LogicThread() {

            @Override
            public void loadCommands() {
                commands.add(moveLeftArm);
            }
        });

        logicList2.add(new LogicThread() {

            @Override
            public void loadCommands() {
                commands.add(moveRightArm);
            }
        });

        moveBackward.setRunMode(Translate.RunMode.CUSTOM);
        moveBackward.setDirection(Translate.Direction.BACKWARD);
        moveBackward.setMaxPower(0.75);
        moveBackward.setExitCondition(new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                return elapsedTime > 3000;
            }
        });

        commands.add(new SpawnNewThread(logicList1));
        commands.add(new SpawnNewThread(logicList2));
        commands.add(moveBackward);
    }
}
