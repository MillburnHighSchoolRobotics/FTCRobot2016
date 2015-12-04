package virtualRobot;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by shant on 12/2/2015.
 */
public class BlueMountainLogic extends LogicThread<AutonomousRobot> {

    final double BUTTON_PUSHER_LEFT = 0.05;
    final double BUTTON_PUSHER_RIGHT = 0.45;


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

        robot.addToProgress("Servos Moved");

        commands.add(new Pause(1500));

        commands.add(new Translate(500, Translate.Direction.FORWARD));
        robot.addToProgress("Moved Forward");


        commands.add(new Pause(1500));

        commands.add(new Rotate(45));
        robot.addToProgress("Rotated");

        commands.add(new Pause(1500));

        commands.add(new Translate(5500, Translate.Direction.FORWARD));
        robot.addToProgress("Moved Forward");

        commands.add(new Pause(1500));

        commands.add(new Translate(500, Translate.Direction.BACKWARD));

        commands.add(new Pause(1500));

        commands.add(new Rotate(-45));
        robot.addToProgress("Rotated");

        commands.add(new Pause(1500));

        commands.add(new Translate(2500, Translate.Direction.FORWARD));
        robot.addToProgress("Moved into center");

        commands.add(new Pause(1500));

        commands.add(new Rotate(45));
        robot.addToProgress("rotated");

        commands.add(new Pause(1500));

        commands.add(new Translate(5000, Translate.Direction.FORWARD));
        robot.addToProgress("Moved into corner");

        commands.add(new Pause(1500));

        commands.add(new Rotate(0));
        robot.addToProgress("turned");

        commands.add(new Pause(1500));

        commands.add(new Translate(3000, Translate.Direction.BACKWARD));
        robot.addToProgress("Moved to clear shit");

        commands.add(new Pause(1500));

        commands.add(new Translate(1000, Translate.Direction.FORWARD));
        robot.addToProgress("moved to dump people");

        commands.add(new Pause(1500));

        commands.add(new Rotate(90));
        robot.addToProgress("rotated to dump people");

        commands.add(new Pause(1500));

        commands.add(new Translate(400, Translate.Direction.FORWARD));
        robot.addToProgress("moved closer to beacon");

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

        robot.addToProgress("dumped people");

        AtomicBoolean redIsLeft = new AtomicBoolean(true);
        TakePicture takePicture = new TakePicture(redIsLeft);

        commands.add (takePicture);


        commands.add (new MoveServo(
            new Servo[] {
                    robot.getButtonPusherServo()
            },
            new double[] {
                    redIsLeft.get() ? BUTTON_PUSHER_RIGHT : BUTTON_PUSHER_LEFT
            }
        ));

        robot.addToProgress("pushed button");


        commands.add (new Translate(1000, Translate.Direction.BACKWARD));
        robot.addToProgress("moved to center");

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
