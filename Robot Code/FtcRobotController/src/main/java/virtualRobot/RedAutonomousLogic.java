package virtualRobot;

/**
 * Created by shant on 11/28/2015.
 */
public class RedAutonomousLogic extends LogicThread<AutonomousRobot> {
    double maxPower = 0.6;
    @Override
    public void loadCommands() {
        //put shields down
        commands.add(
                new MoveServo(
                        new Servo[]{
                                robot.getFrontShieldServo(),
                                robot.getBackShieldServo()
                        },
                        new double[]{
                                0.99,
                                0
                        }

                )
        );

        commands.add(new Pause(1500));

        commands.add(new Translate(1200, Translate.Direction.FORWARD, maxPower));

        commands.add(new Rotate(-45, maxPower));

        //Move into corner
        commands.add(new Translate(4500, Translate.Direction.FORWARD, maxPower));
        //Turn to face backwards
        commands.add(new Rotate(0, maxPower));

        commands.add(new Translate(1500, Translate.Direction.BACKWARD, maxPower));

        commands.add(new Translate(600, Translate.Direction.FORWARD, maxPower));

        commands.add(new Rotate(-90, maxPower));

        commands.add(new Translate(275, Translate.Direction.FORWARD, maxPower));


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
    }
}
