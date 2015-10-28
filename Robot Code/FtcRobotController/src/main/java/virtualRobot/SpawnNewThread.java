package virtualRobot;

/**
 * Created by DOSullivan on 10/28/15.
 */
public class SpawnNewThread implements Command{
 LogicThread logic;
    Thread t;
    public SpawnNewThread(LogicThread l) {
       logic = l;


    }

    @Override
    public void changeRobotState() {
         t = new Thread(logic);
        t.start();


    }
    public Thread getThread() {
        return t;

    }



}
