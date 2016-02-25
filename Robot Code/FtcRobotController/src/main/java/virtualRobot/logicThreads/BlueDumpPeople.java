
package virtualRobot.logicThreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import virtualRobot.AutonomousRobot;
import virtualRobot.ExitCondition;
import virtualRobot.LogicThread;
import virtualRobot.commands.MoveMotor;
import virtualRobot.commands.MoveServo;
import virtualRobot.commands.Pause;
import virtualRobot.commands.Rotate;
import virtualRobot.commands.SpawnNewThread;
import virtualRobot.commands.TakePicture;
import virtualRobot.commands.Translate;
import virtualRobot.components.Servo;

/**
 * Created by shant on 11/28/2015.
 */

/**
 * STARTS ALIGNED WITH WALL
 * MOVES TOWARDS CENTER
 * TURNS AND MOVES TOWARDS CORNER
 * MOVE IN FRONT OF THE BEACON
 * (TAKE A PICTURE)
 * DUMP THE PEOPLE
 * ALIGN TO PUSH THE BUTTON
 */

public class BlueDumpPeople extends LogicThread<AutonomousRobot> {
    double maxPower = 0.7;
    int sonarCap = 13;
    int whiteTape = 30;
    int blueTape = 1;
    double accurateRotatePower = 0.65;
    final double BUTTON_PUSHER_RIGHT = 0.05;
    final double BUTTON_PUSHER_LEFT = 0.45;
    final double slowSpeed = 0.15;
    AtomicBoolean redisLeft;

    public BlueDumpPeople(AtomicBoolean redisLeft) {
        super();
        this.redisLeft = redisLeft;
    }

    @Override
    public void loadCommands() {


        robot.getProgress().clear();
        Rotate.setGlobalMaxPower(1);
        Translate.setGlobalMaxPower(1);


        commands.add(
                new MoveServo(
                        new Servo[]{

                                robot.getBackShieldServo(),
                                robot.getFlipperLeftServo(),
                                robot.getFlipperRightServo(),
                                robot.getScoopServo(),
                                robot.getTapeMeasureServo(),
                                robot.getDumperServo()
                        },
                        new double[]{
                                0.0,
                                0.5,
                                0.5,
                                0.75,
                                0.25,
                                1.0
                        }

                )
        );

        LogicThread<AutonomousRobot> spinReaper = new LogicThread<AutonomousRobot>() {
            @Override
            public void loadCommands() {
                commands.add(new MoveMotor(robot.getReaperMotor(), 1));
            }
        };
        List<LogicThread> threads = new ArrayList<LogicThread>();
        threads.add(spinReaper);

        commands.add(new SpawnNewThread(threads));

        commands.add(new Pause(500));

        commands.add(new Translate(2000, Translate.Direction.FORWARD, maxPower, 0, "moving towards center"));



        commands.add(new Pause(500));

        commands.add(new Rotate(47.5, maxPower, "Rotated #1"));

        commands.add(new Pause(100));




        //Move into corner

        commands.add(new Translate(8500, Translate.Direction.FORWARD, maxPower, 47.5, "moved into corner"));


        commands.add(new Pause(500));
        //Turn to face backwards
        commands.add(new Rotate(0, maxPower, "face backwards"));


        commands.add(new Pause(500));

        Translate moveToLine = new Translate(5000, Translate.Direction.BACKWARD, slowSpeed, 0, "move To Line");
        moveToLine.setExitCondition(new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                if (robot.getColorSensor().getRed() >= whiteTape && robot.getColorSensor().getBlue() >= whiteTape && robot.getColorSensor().getGreen() >= whiteTape) {
                    return true;
                }
                return false;
            }
        });



        commands.add(moveToLine);
        commands.add(new Translate(100, Translate.Direction.FORWARD, slowSpeed, 0, "fudge"));


        commands.add(new Pause(500));



        commands.add(new Rotate(90, maxPower, "turn to dump people"));

        commands.add(new Pause(500));

        Translate moveToDumpForward = new Translate(500, Translate.Direction.FORWARD, slowSpeed, 90, "Dump People");

        moveToDumpForward.setExitCondition(new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                if (robot.getUltrasoundSensor2().getValue() <= 21) {
                    return true;
                }
                return false;
            }
        });

        commands.add(moveToDumpForward);

        commands.add(new Pause(500));

        Translate moveToDumpBackward = new Translate(500, Translate.Direction.BACKWARD, slowSpeed, 90, "Dump People");

        moveToDumpBackward.setExitCondition(new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                if (robot.getUltrasoundSensor2().getValue() >= 22) {
                    return true;
                }
                return false;
            }
        });



        commands.add(moveToDumpBackward);

        commands.add(
                new MoveServo(
                        new Servo[]{
                                robot.getDumperServo()
                        },
                        new double[]{
                                0
                        }
                )
        );

        commands.add(new Pause(1500));
        commands.add(new MoveServo(
                        new Servo[]{
                                robot.getDumperServo()
                        },
                        new double[]{
                                1
                        }

                )

        );
        commands.add(new Pause(1500));

        Translate moveToPic = new Translate(1000, Translate.Direction.BACKWARD, slowSpeed, 90, "Move back to take pic");
        moveToPic.setExitCondition(new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                if (robot.getUltrasoundSensor2().getValue() >= 34) {
                    return true;
                }
                return false;
            }
        });

        commands.add(moveToPic);

        commands.add(new Rotate(0));



        TakePicture takePicture = new TakePicture(redisLeft);
        commands.add(takePicture);
/*
        moveToLine = new Translate(5000, Translate.Direction.FORWARD, slowSpeed, 0, "move To Line");
        moveToLine.setExitCondition(new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                if (robot.getColorSensor().getRed() >= whiteTape && robot.getColorSensor().getBlue() >= whiteTape && robot.getColorSensor().getGreen() >= whiteTape) {
                    return true;
                }
                return false;
            }
        });

        commands.add(moveToLine);
*/
        commands.add(new Rotate(-90));

    }
}