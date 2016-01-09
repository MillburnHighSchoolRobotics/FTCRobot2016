package virtualRobot.logicThreads;

import virtualRobot.AutonomousRobot;
import virtualRobot.ExitCondition;
import virtualRobot.LogicThread;
import virtualRobot.commands.AccurateRotate;
import virtualRobot.commands.MoveServo;
import virtualRobot.commands.Pause;
import virtualRobot.commands.Rotate;
import virtualRobot.commands.Translate;
import virtualRobot.components.Servo;

/**
 * Created by shant on 11/28/2015.
 */
public class RedAutonomousLogic extends LogicThread<AutonomousRobot> {
    double maxPower = 0.7;
    int sonarCap = 13;
    int whiteTape = 5;
    int blueTape = 1;
    double accurateRotatePower = 0.65;
    final double BUTTON_PUSHER_RIGHT = 0.05;
    final double BUTTON_PUSHER_LEFT = 0.45;
    final double slowSpeed = 0.2;
    final boolean[] redIsLeft = new boolean[] {true};

    @Override
    public void loadCommands() {


        robot.getProgress().clear();
        Rotate.setGlobalMaxPower(0.4);
        Translate.setGlobalMaxPower(0.5);


        commands.add(
                new MoveServo(
                        new Servo[]{
                                robot.getFrontShieldServo(),
                                robot.getBackShieldServo(),
                                robot.getFlipperLeftServo(),
                                robot.getFlipperRightServo(),
                                robot.getButtonPusherServo(),
                                robot.getTapeMeasureServo(),
                                robot.getDumperServo()
                        },
                        new double[]{
                                0.85,
                                0.0,
                                0.5,
                                0.5,
                                0.25,
                                0.485,
                                0.0
                        }

                )
        );


        commands.add(new Pause(500));

        commands.add(new Translate(2300, Translate.Direction.FORWARD, maxPower, "moving towards center"));

        //robot.addToProgress("moved back");

        commands.add(new Pause(500));

        commands.add(new Rotate(-45, maxPower, "Rotated #1"));

        commands.add(new Pause(500));

        commands.add(new AccurateRotate(-45, accurateRotatePower, "Accurate Rotate"));


        //Move into corner

        commands.add(new Translate(9000, Translate.Direction.FORWARD, maxPower, "moved into corner"));


        commands.add(new Pause(500));
        //Turn to face backwards
        commands.add(new Rotate(0, maxPower, "face backwards"));


        commands.add(new Pause(500));

        commands.add(new AccurateRotate(0, accurateRotatePower, "accurate rotate"));

        commands.add(new Translate(3000, Translate.Direction.BACKWARD, 0.3, 4000));


        commands.add(new Pause(500));

        commands.add(new AccurateRotate(0, accurateRotatePower, "accurate rotate"));

        Translate moveToLine = new Translate(5000, Translate.Direction.FORWARD, slowSpeed, "move To Line");
        moveToLine.setExitCondition(new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                if (robot.getColorSensor().getRed() >= whiteTape && robot.getColorSensor().getBlue() >= whiteTape && robot.getColorSensor().getGreen() >= whiteTape) {
                    return true;
                }
                return false;
            }
        });

        commands.add(new Translate(100, Translate.Direction.FORWARD, slowSpeed, "go more forward"));

        commands.add(moveToLine);

        commands.add(new Pause(500));

        commands.add(new Rotate(-90, maxPower, "turn to dump people"));
        //TODO change this back
        // commands.add(new AccurateRotate(90, accurateRotatePower, "Accurate Rotate"));

        commands.add(new Translate(50, Translate.Direction.FORWARD, maxPower, "back up to take picture"));

        /*TakePicture takePicture = new TakePicture(commands, "red");

        commands.add(takePicture);

        commands.add (new Translate (0, Translate.Direction.FORWARD, slowSpeed, "Red is Left: " + Arrays.toString(redIsLeft)));
*/
        commands.add(new Pause(500));
        Translate moveDump = new Translate(2500, Translate.Direction.FORWARD, slowSpeed, "move till to dump RedisLeft: " + redIsLeft.toString());
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

        commands.add(
                new MoveServo(
                        new Servo[]{
                                robot.getDumperServo()
                        },
                        new double[]{
                                1
                        }
                )
        );

        commands.add(new Pause(1500));
        commands.add(new MoveServo(
                        new Servo[]{
                                robot.getDumperServo()
                        },
                        new double[]{
                                0
                        }

                )

        );

        commands.add(new Pause(1500));

       // commands.add(new Translate (2500, Translate.Direction.BACKWARD, maxPower, "CHARGEE!!!"));
/*
        Translate movePress = new Translate(2500, Translate.Direction.FORWARD, slowSpeed, "move till pressing");
        movePress.setRunMode(Translate.RunMode.CUSTOM);
        movePress.setExitCondition(new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                if (robot.getUltrasoundSensor().getValue() < 11) {
                    return true;
                }
                return false;
            }
        });

        commands.add(movePress);


        commands.add(new MoveServo(
                new Servo[]{
                        robot.getButtonPusherServo()
                },
                new double[]{
                        BUTTON_PUSHER_RIGHT
                },
                new ExitCondition() {
                    public boolean isConditionMet() {
                        return !redIsLeft[0];
                    }
                }
        ));

        commands.add(new Pause(3000));

        commands.add(new MoveServo(
                new Servo[]{
                        robot.getButtonPusherServo()
                },
                new double[]{
                        BUTTON_PUSHER_LEFT
                  },
                new ExitCondition() {
                    public boolean isConditionMet() {
                        return redIsLeft[0];
                    }
                }
        ));


        commands.add(new Pause(500));

        //commands.add(new Translate(50, Translate.Direction.FORWARD, maxPower, "CHARGEEE!!"));

*/
    }
}
