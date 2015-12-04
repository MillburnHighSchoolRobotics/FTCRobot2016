package virtualRobot;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by shant on 11/28/2015.
 */
public class BlueClimberDumpLogic extends LogicThread <AutonomousRobot> {
    double maxPower = 0.7;
    final double BUTTON_PUSHER_LEFT = 0.05;
    final double BUTTON_PUSHER_RIGHT = 0.45;
    final AtomicBoolean redIsLeft = new AtomicBoolean(true);
    @Override
    public void loadCommands() {


        robot.getProgress().clear();
        Rotate.setGlobalMaxPower(0.6);
        Translate.setGlobalMaxPower(0.6);

        robot.addToProgress("Servos Moved");
        robot.addToProgress("Moved Forward");
        robot.addToProgress("Turned 45 Degrees");
        robot.addToProgress("Moved into corner");
        robot.addToProgress("Rotated");
        robot.addToProgress("moved backward");
        robot.addToProgress("moved forward");
        robot.addToProgress("rotated");
        robot.addToProgress("dumped people");
        robot.addToProgress("pushed button");


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

        commands.add(new Translate(2400, Translate.Direction.FORWARD, maxPower));



        commands.add(new Pause(1500));

        commands.add(new Rotate(45, maxPower));


        commands.add(new Pause(1500));


        //Move into corner
        commands.add(new Translate(9000, Translate.Direction.FORWARD, maxPower));


        commands.add(new Pause(1500));
        //Turn to face backwards
        commands.add(new Rotate(0, maxPower));


        commands.add(new Pause(1500));

        commands.add(new Translate(3000, Translate.Direction.BACKWARD, maxPower));


        commands.add(new Pause(1500));

        commands.add(new Translate(1450, Translate.Direction.FORWARD, maxPower));


        commands.add(new Pause(1500));

        commands.add(new Rotate(90, maxPower));


        commands.add(new Translate(500, Translate.Direction.FORWARD, maxPower));


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

        );

        //commands.add(new Translate(100, Translate.Direction.FORWARD));

        //TakePicture takePicture = new TakePicture(redIsLeft);

        //commands.add (takePicture);


        commands.add (new MoveServo(
                new Servo[] {
                        robot.getButtonPusherServo()
                },
                new double[] {
                        BUTTON_PUSHER_RIGHT
                }
                /*new ExitCondition() {
                    public boolean isConditionMet() {
                        return redIsLeft.get();
                    }
                }*/
        ));
        /*commands.add(new MoveServo(
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
*/

        commands.add(new Pause(1500));


        /*
        commands.add (new Translate (
                500, Translate.Direction.BACKWARD));

        commands.add (new Rotate (135, maxPower));e

        commands.add (new Translate (2000, Translate.Direction.FORWARD));
        */

    }
}
