package virtualRobot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yanjun on 2/16/2016.
 */
public class FieldMap {
    private List<Waypoint> waypoints;

    public FieldMap() {
        waypoints = new ArrayList<Waypoint>();
    }

    public FieldMap(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }
}
