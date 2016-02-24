package virtualRobot;

import java.util.List;

/**
 * Created by Yanjun on 2/16/2016.
 */
public class Waypoint {
    private Location location;
    private List<Path> paths;

    public Waypoint() {
        location = new Location();
    }

    public Waypoint(Location location) {
        this.location = location;
    }

    public void addPath(Path path) {
        paths.add(path);
    }

    public List<Path> getPaths() {
        return paths;
    }

    public Location getLocation() {
        return location;
    }

    public Path getPathTo(Waypoint end) {
        for (Path path : paths) {
            if (path.getEnd() == end) {
                return path;
            }
        }

        return null;
    }
}
