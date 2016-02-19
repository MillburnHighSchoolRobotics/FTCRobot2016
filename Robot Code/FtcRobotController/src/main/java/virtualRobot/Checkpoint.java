package virtualRobot;

/**
 * Created by Yanjun on 2/16/2016.
 */
public class Checkpoint extends Waypoint {
    private Objective objective;

    public Checkpoint() {
        super();
        objective = new Objective();
    }

    public Checkpoint(Location location) {
        super(location);
        objective = new Objective();
    }

    public Checkpoint(Location location, Objective objective) {
        super(location);
        this.objective = objective;
    }

    public Objective getObjective() {
        return objective;
    }

    public boolean getObjectiveStatus() {
        return objective.isCompleted();
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }
}
