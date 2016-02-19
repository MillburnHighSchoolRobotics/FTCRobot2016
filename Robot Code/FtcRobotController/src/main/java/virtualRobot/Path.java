package virtualRobot;

/**
 * Created by Yanjun on 2/16/2016.
 */
public class Path {
    private Waypoint start;
    private Waypoint end;
    private double time;
    private LogicThread<AutonomousRobot> logicThread;

    public Path() {
        start = new Waypoint();
        end = new Waypoint();
        time = 0;
        logicThread = new LogicThread<AutonomousRobot>() {
            @Override
            public void loadCommands() {

            }
        };
    }

    public Path(Waypoint start, Waypoint end, double time) {
        this();
        this.start = start;
        this.end = end;
        this.time = time;
    }

    public Path(Waypoint start, Waypoint end, double time, LogicThread logicThread) {
        this(start, end, time);
        this.logicThread = logicThread;
    }

    public Waypoint getStart() {
        return start;
    }

    public Waypoint getEnd() {
        return end;
    }

    public double getTime() {
        return time;
    }

    public LogicThread<AutonomousRobot> getLogicThread() {
        return logicThread;
    }
}
