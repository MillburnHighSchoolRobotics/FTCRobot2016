package virtualRobot;

/**
 * Created by shant on 11/3/2015.
 */
public class AutonomousTest1 extends LogicThread {
    public AutonomousTest1 () {
        super();
        commands.add(new Translate(150, Translate.Direction.FORWARD));
        commands.add(new Rotate(45));
        commands.add(new Translate(300, Translate.Direction.FORWARD));
    }
}
