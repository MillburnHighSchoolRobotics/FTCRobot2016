package virtualRobot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

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

    public List<Path> findOptimalRoute(Waypoint start, Waypoint end) {
        List<Path> optimal = new ArrayList<Path>();

        if (!waypoints.contains(start) || !waypoints.contains(end)) {
            return optimal;
        }

        PriorityQueue<PriorityQueueDataPoint> pq = new PriorityQueue<PriorityQueueDataPoint>();
        pq.offer(new PriorityQueueDataPoint(start, 0));

        Map<Waypoint, Double> distances = new HashMap<Waypoint, Double>();
        Set<Waypoint> checked = new HashSet<Waypoint>();
        Map<Waypoint, Waypoint> previous = new HashMap<Waypoint, Waypoint>();

        while (!pq.isEmpty()) {
            PriorityQueueDataPoint cur = pq.poll();

            if (checked.contains(cur.target)) {
                continue;
            }

            checked.add(cur.target);

            if (cur.target == end) {
                break;
            }

            List<Path> nextStops = cur.target.getPaths();
            for (Path path : nextStops) {
                if (!distances.containsKey(path.getEnd()) || distances.get(path.getEnd()) > cur.weight + path.getTime()) {
                    distances.put(path.getEnd(), cur.weight + path.getTime());
                    previous.put(path.getEnd(), cur.target);
                    pq.offer(new PriorityQueueDataPoint(path.getEnd(), cur.weight + path.getTime()));
                }
            }
        }

        Waypoint cur = end;
        while (cur != start) {
            optimal.add(0, previous.get(cur).getPathTo(cur));
            cur = previous.get(cur);
        }

        return optimal;
    }

    private class PriorityQueueDataPoint implements Comparable<PriorityQueueDataPoint> {
        public Waypoint target;
        public double weight;

        public PriorityQueueDataPoint(Waypoint target, double weight) {
            this.target = target;
            this.weight = weight;
        }

        @Override
        public int compareTo(PriorityQueueDataPoint another) {
            return Double.compare(this.weight, another.weight);
        }
    }
}
