package virtualRobot;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by shant on 11/28/2015.
 */
public class BlueClimberDumpLogic extends LogicThread <AutonomousRobot> {
    double maxPower = 0.7;
<<<<<<< HEAD
    commands.add (new Translate (4500, Translate.Direction.FORWARD, maxPower));

=======
    double accurateRotatePower = 0.65;
    final double BUTTON_PUSHER_LEFT = 0.05;
    final double BUTTON_PUSHER_RIGHT = 0.45;
    final AtomicBoolean redIsLeft = new AtomicBoolean(true);
>>>>>>> 7320157799a433b20925ddf9c2a4899423cd955e
    @Override
    public void loadCommands() {

        /*
        robot.getProgress().clear();
        Rotate.setGlobalMaxPower(0.6);
        Translate.setGlobalMaxPower(0.5);


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



        commands.add(new Pause(1500));

        commands.add(new Translate(2400, Translate.Direction.FORWARD, maxPower, "moving towards center"));

        //robot.addToProgress("moved back");

        commands.add(new Pause(1500));

        commands.add(new Rotate(45, maxPower, "Rotated #1"));

        commands.add(new Pause(1500));

        commands.add(new AccurateRotate(45, accurateRotatePower, "Accurate Rotate"));


        //Move into corner
<<<<<<< HEAD

        commands.add(new Translate(4500, Translate.Direction.FORWARD, maxPower));
=======
        commands.add(new Translate(9000, Translate.Direction.FORWARD, maxPower, "moved into corner"));
>>>>>>> 7320157799a433b20925ddf9c2a4899423cd955e


        commands.add(new Pause(1500));
        //Turn to face backwards
        commands.add(new Rotate(0, maxPower, "face backwards"));


        commands.add(new Pause(1500));

        commands.add(new AccurateRotate(0, accurateRotatePower, "accurate rotate"));

        commands.add(new Translate(3000, Translate.Direction.BACKWARD, 0.3, "clear beacon area"));


        commands.add(new Pause(1500));

        commands.add (new AccurateRotate(0, accurateRotatePower, "accurate rotate"));
        commands.add(new Translate(1300, Translate.Direction.FORWARD, 0.3, "come back to dump people"));
        //TODO adjust to get into the right place

        commands.add(new Pause(1500));

        commands.add(new Rotate(90, maxPower, "turn to dump people"));

        commands.add(new AccurateRotate(90, accurateRotatePower, "Accurate Rotate"));


        commands.add(new Translate(350, Translate.Direction.FORWARD, maxPower, "Move towards beacon"));
        //TODO adjust for a good length

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
        commands.add(new MoveServo(
                        new Servo[]{
                                robot.getDumperServo()
                        },
                        new double[]{
                                0
                        }

                )

        );*/

        commands.add (new Pause (1500));

        commands.add(new Translate(75, Translate.Direction.FORWARD));
        //TODO adjust this

        TakePicture takePicture = new TakePicture(redIsLeft);

        commands.add (takePicture);


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
        commands.add(new MoveServo(
                new Servo[]{
                        robot.getButtonPusherServo()
                },
                new double[]{
                        BUTTON_PUSHER_LEFT
                },
                new ExitCondition() {
                    public boolean isConditionMet() {
                        return !redIsLeft.get();
                    }
                }
        ));


        commands.add(new Pause(1500));


    }
}
