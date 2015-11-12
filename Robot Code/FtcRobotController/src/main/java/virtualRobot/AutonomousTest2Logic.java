package virtualRobot;

/**
 * Created by Yanjun on 11/10/2015.
 */
public class AutonomousTest2Logic extends LogicThread {
    @Override
    public void loadCommands() {
        commands.add(new Command() {
            @Override
            public boolean changeRobotState() {

                boolean isInterrupted = false;

                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        isInterrupted = true;
                        break;
                    }

                    try {
                        Thread.currentThread().sleep(100);
                    } catch (InterruptedException e) {
                        isInterrupted = true;
                        break;
                    }
                }

                return isInterrupted;
            }
        });
    }
}
