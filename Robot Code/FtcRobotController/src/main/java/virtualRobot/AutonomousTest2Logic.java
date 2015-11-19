package virtualRobot;

/**
 * Created by Yanjun on 11/10/2015.
 */
public class AutonomousTest2Logic extends LogicThread<AutonomousRobot> {
    @Override
    public void loadCommands() {
        /*commands.add(new Translate(5000, Translate.Direction.FORWARD));
        commands.add(new Rotate(45));
        commands.add(new Translate(10000, Translate.Direction.BACKWARD));
        commands.add(new Rotate(0));

        Translate moveUntilLine = new Translate();
        moveUntilLine.setRunMode(Translate.RunMode.CUSTOM);
        moveUntilLine.setDirection(Translate.Direction.BACKWARD);
        moveUntilLine.setMaxPower(0.3);
        moveUntilLine.setExitCondition(new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                AutonomousRobot robot = Command.robot;

                int hexColor = (int) robot.getColorSensor().getValue();
                int red = (hexColor & 0xff0000) >> 16;
                int blue = (hexColor & 0x00ff00) >> 8;
                int green = (hexColor & 0x0000ff) >> 0;

                if (red > 0x99 && green > 0x99 && blue > 0x99) {
                    return true;
                }

                return false;
            }
        });

        commands.add(moveUntilLine);

        commands.add(new Rotate(90));

        Translate moveUntilClose = new Translate();
        moveUntilClose.setRunMode(Translate.RunMode.CUSTOM);
        moveUntilClose.setDirection(Translate.Direction.BACKWARD);
        moveUntilClose.setMaxPower(0.3);
        moveUntilClose.setExitCondition(new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                AutonomousRobot robot = Command.robot;

                Sensor ultrasound = robot.getUltrasoundSensor();

                if (ultrasound.getValue() < 35) {
                    return true;
                }

                return false;
            }
        });

        commands.add(moveUntilClose);

        commands.add(
                new MoveServo(
                        new Servo[]{
                                robot.getArmLeftServo(),
                                robot.getArmRightServo()
                        },
                        new double[]{
                                0.5,
                                0.5
                        }
                )
        );

        commands.add(new Pause(2500));
        commands.add(
                new MoveServo(
                        new Servo[]{
                                robot.getArmLeftServo(),
                                robot.getArmRightServo()
                        },
                        new double[]{
                                0.5,
                                0.5
                        }
                )
        );
        commands.add(new Pause(2500));
        commands.add(new Translate(700, Translate.Direction.FORWARD));
        commands.add(new Rotate(0));
        commands.add(new Translate(2000, Translate.Direction.BACKWARD));
        commands.add(new Rotate(45));
        commands.add(new Translate(2500, Translate.Direction.BACKWARD));
        commands.add(new Rotate(-45));

        Translate moveUntilTilted = new Translate();
        moveUntilTilted.setRunMode(Translate.RunMode.CUSTOM);
        moveUntilTilted.setDirection(Translate.Direction.BACKWARD);
        moveUntilTilted.setMaxPower(0.3);
        moveUntilTilted.setExitCondition(new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                AutonomousRobot robot = Command.robot;

                Sensor tiltSensor = robot.getTiltSensor();

                if (tiltSensor.getValue() > 18) {
                    return true;
                }

                return false;
            }
        });
        commands.add(moveUntilTilted);

        */
    }
}
