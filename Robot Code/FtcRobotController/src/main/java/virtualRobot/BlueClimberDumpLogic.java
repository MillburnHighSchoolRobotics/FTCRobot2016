package virtualRobot;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by shant on 11/28/2015.
 */
public class BlueClimberDumpLogic extends LogicThread <AutonomousRobot> {
    double maxPower = 0.7;
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

        commands.add (new Pause(1500));

        commands.add (new Translate (1200, Translate.Direction.FORWARD, maxPower));

        robot.addToProgress("Moved Forward");

        commands.add (new Pause (1500));

        commands.add (new Rotate(45, maxPower));

        robot.addToProgress("Turned 45 Degrees");

        commands.add (new Pause(1500));

        //Move into corner
        commands.add(new Translate(4500, Translate.Direction.FORWARD, maxPower));

        robot.addToProgress("Moved into corner");

        commands.add(new Pause(1500));
        //Turn to face backwards
        commands.add (new Rotate (0, maxPower));

        robot.addToProgress("Rotated");

        commands.add (new Pause (1500));

        commands.add(new Translate(1500, Translate.Direction.BACKWARD, maxPower));

        robot.addToProgress("moved backward");

        commands.add(new Pause(1500));

        commands.add (new Translate (600, Translate.Direction.FORWARD, maxPower));

        robot.addToProgress("moved forward");

        commands.add (new Pause (1500));

        commands.add(new Rotate(90, maxPower));

        robot.addToProgress("rotated");

        commands.add(new Translate(275, Translate.Direction.FORWARD, maxPower));


        commands.add (
                new MoveServo (
                        new Servo[] {
                                robot.getDumperServo()
                        },
                        new double[] {
                                1
                        }
                )
        );

        commands.add(new Pause(1500));
        commands.add (new MoveServo (
                        new Servo[]{
                                robot.getDumperServo()
                        },
                        new double[] {
                                0
                        }

                )

        );
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

        commands.add(new Pause(1500));
        /*
        commands.add (new Translate (
                500, Translate.Direction.BACKWARD));

        commands.add (new Rotate (135, maxPower));

        commands.add (new Translate (2000, Translate.Direction.FORWARD));
        */

    }
}
