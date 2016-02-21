
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
import virtualRobot.commands.Translate;
import virtualRobot.components.Servo;

/**
 * Created by shant on 11/28/2015.
 */


public class BlueGetToBeacon extends LogicThread<AutonomousRobot> {
    double maxPower = 0.7;
    int sonarCap = 13;
    int whiteTape = 30;
    int blueTape = 1;
    double accurateRotatePower = 0.65;
    final double BUTTON_PUSHER_RIGHT = 0.05;
    final double BUTTON_PUSHER_LEFT = 0.45;
    final double slowSpeed = 0.3;
    AtomicBoolean redisLeft;

    public BlueGetToBeacon(AtomicBoolean redisLeft) {
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

        commands.add(new Rotate(50, maxPower, "Rotated #1"));

        commands.add(new Pause(100));




        //Move into corner

        commands.add(new Translate(9000, Translate.Direction.FORWARD, maxPower, 50, "moved into corner"));


        commands.add(new Pause(500));
        //Turn to face backwards
        commands.add(new Rotate(0, maxPower, "face backwards"));


        commands.add(new Pause(500));

        //commands.add(new AccurateRotate(0, accurateRotatePower, "accurate rotate"));

        //commands.add(new Translate(3000, Translate.Direction.BACKWARD, 0.3, 0, "sweep shit", 4000));


        //commands.add(new Pause(500));

        //commands.add(new AccurateRotate(0, accurateRotatePower, "accurate rotate"));

        Translate moveToLine = new Translate(5000, Translate.Direction.BACKWARD, 0.15, 0, "move To Line");
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

        //commands.add(new Translate(100, Translate.Direction.FORWARD, slowSpeed, 0, "go more forward"));



        commands.add(new Pause(500));

        commands.add(new Rotate(90, maxPower, "turn to dump people"));
        //TODO change this back
        // commands.add(new AccurateRotate(90, accurateRotatePower, "Accurate Rotate"));

        //commands.add(new Translate(50, Translate.Direction.FORWARD, maxPower, 90, "back up to take picture"));
        //TakePicture takePicture = new TakePicture(redisLeft);

        //commands.add(takePicture);

        //commands.add(new Pause(500));
        /*Translate moveDump = new Translate(2500, Translate.Direction.FORWARD, slowSpeed, 90, "move till to dump");
        moveDump.setRunMode(Translate.RunMode.CUSTOM);
        moveDump.setExitCondition(new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                if (robot.getUltrasoundSensor().getValue() < 13) {
                    return true;
                }
                return false;
            }
        });

        commands.add(moveDump);
        */
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

        //commands.add (new Translate (2500, Translate.Direction.BACKWARD, maxPower, "CHARGEE"));

    }
}
