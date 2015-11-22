package virtualRobot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shant on 11/22/2015.
 */
public class RedAutonomousLogic extends LogicThread<AutonomousRobot> {
    @Override
    public void loadCommands() {
        commands.add(
                new MoveServo(
                        new Servo[]{
                                robot.getBlockerLeftServo(),
                                robot.getBlockerRightServo(),
                                robot.getRampLift()
                        },
                        new double[]{
                                0.8,
                                0.8,
                                0
                        }
                )
        );

        final MoveMotor moveReaper = new MoveMotor(robot.getReaperMotor(), 1);
        moveReaper.setExitCondition(new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                return false;
            }
        });
        List<LogicThread> moveReaperList = new ArrayList<LogicThread>();
        moveReaperList.add(new LogicThread() {

            @Override
            public void loadCommands() {
                commands.add(moveReaper);
            }
        });
        commands.add(new SpawnNewThread(moveReaperList));
        commands.add(new Translate(4500, Translate.Direction.FORWARD));
        commands.add(new Rotate(-1850, 0.75));
        commands.add(new Translate(11500, Translate.Direction.FORWARD));
        commands.add(new Rotate(1850, 0.75));
        commands.add(new Translate(1900, Translate.Direction.FORWARD));
        commands.add(new Rotate(-2200, -0.75));
        commands.add(new Translate(1800, Translate.Direction.BACKWARD));
        commands.add(
                new MoveServo(
                        new Servo[]{

                                robot.getArmRightServo()
                        },
                        new double[]{

                                0.4
                        }
                )
        );

        commands.add(new Pause(2500));
        commands.add(
                new MoveServo(
                        new Servo[]{

                                robot.getArmRightServo()
                        },
                        new double[]{

                                0.63
                        }
                )
        );
        commands.add(new Pause(2500));
    }
}
