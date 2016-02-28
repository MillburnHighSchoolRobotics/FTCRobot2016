package virtualRobot;

/**
 * Created by Yanjun on 11/18/2015.
 */
public interface TeleopRobot extends AutonomousRobot {

    JoystickController getJoystickController1();

    JoystickController getJoystickController2();

    /*double SCOOP_UP = 0.4;
    double SCOOP_FLAT = 0.9;
    double SCOOP_CARRY = 0.7;
    double SCOOP_DOWN = .95;
    double GATE_OPEN = 0.3;
    double GATE_CLOSED = 1;
    double BASKET_UP = 0.35;
    double BASKET_DOWN = 0;*/
}
