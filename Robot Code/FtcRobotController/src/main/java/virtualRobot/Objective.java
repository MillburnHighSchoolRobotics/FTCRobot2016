package virtualRobot;

/**
 * Created by Yanjun on 2/16/2016.
 */
public class Objective {
    private final double score;
    private boolean completed;

    public Objective() {
        score = 0;
        completed = false;
    }

    public Objective(double score) {
        this.score = score;
        completed = false;
    }

    public double getScore() {
        return score;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
