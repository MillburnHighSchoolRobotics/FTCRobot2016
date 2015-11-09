package virtualRobot;

/**
 * Created by shant on 11/3/2015.
 */
public class AutonomousTest1Logic extends LogicThread {

    @Override
    public void loadCommands() {
        Translate temp = new Translate(15000, Translate.Direction.BACKWARD);
        //temp.setRunMode(Translate.RunMode.WITH_ENCODERS);
        commands.add(temp);
        //commands.add(new Translate(1500, Translate.Direction.FORWARD));
        commands.add(new Rotate(45));

        temp = new Translate(3000, Translate.Direction.FORWARD);
        temp.setRunMode(Translate.RunMode.WITH_ENCODERS);
        commands.add(temp);
    }
}
