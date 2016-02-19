package virtualRobot;

import java.util.ArrayList;
import java.util.List;

import virtualRobot.commands.Command;
import virtualRobot.commands.Rotate;
import virtualRobot.commands.Translate;

/**
 * Created by Yanjun on 2/16/2016.
 */
public class LogicThreadFactory {
    public static LogicThread<AutonomousRobot> createLogicThread(Location curLoc, Waypoint target) {
        final List<Command> moveCommands = new ArrayList<Command>();

        Location targetLoc = target.getLocation();
        double diffY = targetLoc.getY() - curLoc.getY();
        double diffX = targetLoc.getX() - curLoc.getX();
        double newRefAngle = Math.atan2(diffY, diffX) * 180 / Math.PI;

        double newAngle = getConvertedAngle(newRefAngle, Command.AUTO_ROBOT.getHeadingSensor().getValue());

        moveCommands.add(new Rotate(newAngle));

        double displacement = Math.sqrt(diffX * diffX + diffY * diffY);

        moveCommands.add(new Translate(displacement, Translate.Direction.FORWARD));

        LogicThread<AutonomousRobot> newThread = new LogicThread<AutonomousRobot>() {
            @Override
            public void loadCommands() {
                for (int i = 0; i < moveCommands.size(); i++) {
                    commands.add(moveCommands.get(i));
                }
            }
        };

        return newThread;
    }

    public static double getConvertedAngle(double refAngle, double curAngle) {
        double lowerBound = -180;
        while (curAngle - lowerBound < 0) {
            lowerBound -= 360;
        }

        while (curAngle - lowerBound > 360) {
            lowerBound += 360;
        }

        lowerBound += 180;
        refAngle += lowerBound;

        return refAngle;
    }
}
