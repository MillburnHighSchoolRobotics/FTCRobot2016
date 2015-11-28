package virtualRobot;

/**
 * Created by shant on 11/28/2015.
 */
public class RedAutonomousLogic extends LogicThread<AutonomousRobot> {
    @Override
    public void loadCommands() {
        //put shields down
        commands.add (
                new MoveServo (
                        new Servo [] {
                                robot.getFrontShieldServo(),
                                robot.getBackShieldServo()
                        },
                        new double [] {
                                1D,

                        }

                )
        );

        commands.add (new Translate (1000, Translate.Direction.FORWARD));

        commands.add (new Rotate(-45, 0.75));

        //Move into corner
        commands.add (new Translate (4500, Translate.Direction.FORWARD));
        //Turn to face backwards
        commands.add (new Rotate (0, 0.75));

        commands.add (new Translate (2500, Translate.Direction.BACKWARD));

        commands.add (new Translate (1000, Translate.Direction.FORWARD));

        commands.add (new Rotate (-90, 0.75));

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

        commands.add (new MoveServo (
                        new Servo[]{
                                robot.getDumperServo()
                        },
                        new double[] {
                                0
                        }

                )

        );

        commands.add (new Translate (1500, Translate.Direction.BACKWARD));

        commands.add (new Rotate (-135, 0.75));

        commands.add (new Translate (2000, Translate.Direction.FORWARD));


    }
}
