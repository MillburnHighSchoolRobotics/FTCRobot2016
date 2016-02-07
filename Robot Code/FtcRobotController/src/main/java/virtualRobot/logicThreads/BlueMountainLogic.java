package virtualRobot.logicThreads;

import java.util.concurrent.atomic.AtomicBoolean;

import virtualRobot.AutonomousRobot;
import virtualRobot.ExitCondition;
import virtualRobot.LogicThread;
import virtualRobot.commands.MoveServo;
import virtualRobot.commands.Pause;
import virtualRobot.commands.Rotate;
import virtualRobot.commands.Translate;
import virtualRobot.components.Servo;

/**
 * Created by shant on 12/2/2015.
 */
public class BlueMountainLogic extends LogicThread<AutonomousRobot> {

    final double BUTTON_PUSHER_LEFT = 0.05;
    final double BUTTON_PUSHER_RIGHT = 0.45;

    final AtomicBoolean redIsLeft = new AtomicBoolean(true);


    @Override
    public void loadCommands() {
        robot.getProgress().clear();
        Rotate.setGlobalMaxPower(0.6);
        Translate.setGlobalMaxPower(0.6);
        commands.add(
                new MoveServo(
                        new Servo[]{
                                robot.getFrontShieldServo(),
                                robot.getBackShieldServo(),
                                robot.getFlipperLeftServo(),
                                robot.getFlipperRightServo(),
                                robot.getButtonPusherServo()
                        },
                        new double[]{
                                0.99,
                                0.0,
                                0.5,
                                0.5,
                                0.25
                        }

                )
        );

        //robot.addToCommands("Servos Moved");

        commands.add(new Pause(1500));

        commands.add(new Translate(500, Translate.Direction.FORWARD));
        //robot.addToCommands("Moved Forward");


        commands.add(new Pause(1500));

        commands.add(new Rotate(45));
        //robot.addToCommands("Rotated");

        commands.add(new Pause(1500));

        commands.add(new Translate(5500, Translate.Direction.FORWARD));
       // robot.addToCommands("Moved Forward");

        commands.add(new Pause(1500));

        commands.add(new Translate(500, Translate.Direction.BACKWARD));

        commands.add(new Pause(1500));

        commands.add(new Rotate(-45));
        //robot.addToCommands("Rotated");

        commands.add(new Pause(1500));

        commands.add(new Translate(2500, Translate.Direction.FORWARD));
       // robot.addToCommands("Moved into center");

        commands.add(new Pause(1500));

        commands.add(new Rotate(45));
       //robot.addToCommands("rotated");

        commands.add(new Pause(1500));

        commands.add(new Translate(5000, Translate.Direction.FORWARD));
       // robot.addToCommands("Moved into corner");

        commands.add(new Pause(1500));

        commands.add(new Rotate(0));
       // robot.addToCommands("turned");

        commands.add(new Pause(1500));

        commands.add(new Translate(3000, Translate.Direction.BACKWARD));
        //robot.addToCommands("Moved to clear shit");

        commands.add(new Pause(1500));

        commands.add(new Translate(1000, Translate.Direction.FORWARD));
       // robot.addToCommands("moved to dump people");

        commands.add(new Pause(1500));

        commands.add(new Rotate(90));
       // robot.addToCommands("rotated to dump people");

        commands.add(new Pause(1500));

        commands.add(new Translate(300, Translate.Direction.FORWARD));
       // robot.addToCommands("moved closer to beacon");

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

        commands.add (new Translate (50, Translate.Direction.FORWARD));

      //  robot.addToCommands("dumped people");

       // TakePicture takePicture = new TakePicture(redIsLeft);

       // commands.add (takePicture);

        commands.add (new Pause(1500));


        commands.add (new MoveServo(
            new Servo[] {
                    robot.getButtonPusherServo()
            },
            new double[] {
                     BUTTON_PUSHER_RIGHT
            },
                new ExitCondition() {
                    public boolean isConditionMet() {
                        return redIsLeft.get();
                    }
                }
        ));
        commands.add (new MoveServo(
                new Servo[] {
                        robot.getButtonPusherServo()
                },
                new double[] {
                        BUTTON_PUSHER_LEFT
                },
                new ExitCondition() {
                    public boolean isConditionMet() {
                        return !redIsLeft.get();
                    }
                }
        ));

      //  robot.addToCommands("pushed button");


        commands.add (new Translate(1000, Translate.Direction.BACKWARD));
      //  robot.addToCommands("moved to center");

        commands.add(new Rotate(45));

        commands.add(new Translate(3000, Translate.Direction.BACKWARD));

        commands.add(new Rotate(135));

        commands.add (new MoveServo(
                new Servo[] {
                        robot.getFrontShieldServo(),
                        robot.getBackShieldServo()
                },
                new double[] {
                        0.4,
                        1
                }
        ));

        commands.add (new Translate (4500, Translate.Direction.FORWARD, 1.0));



    }
}
