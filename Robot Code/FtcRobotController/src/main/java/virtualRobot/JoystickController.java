package virtualRobot;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by Yanjun on 11/12/2015.
 */
public class JoystickController {
    boolean[] down, pressed, released;
    double[] stickValues;
    boolean dpad_up, dpad_down, dpad_left, dpad_right;

    public JoystickController() {
        down = new boolean[12];
        pressed = new boolean[12];
        released = new boolean[12];

        stickValues = new double[8];

        dpad_up = false;
        dpad_down = false;
        dpad_left = false;
        dpad_right = false;
    }

    public void copyStates(Gamepad gamepad) throws RobotCoreException {
        boolean[] curStates = new boolean[12];

        curStates[BUTTON_X] = gamepad.x;
        curStates[BUTTON_A] = gamepad.a;
        curStates[BUTTON_Y] = gamepad.y;
        curStates[BUTTON_B] = gamepad.b;

        curStates[BUTTON_RB] = gamepad.right_bumper;
        curStates[BUTTON_LB] = gamepad.left_bumper;

        curStates[BUTTON_RT] = (gamepad.right_trigger > 0.7);
        curStates[BUTTON_LT] = (gamepad.left_trigger > 0.7);

        curStates[BUTTON_START] = gamepad.start;
        curStates[BUTTON_BACK] = gamepad.back;
        curStates[BUTTON_LEFT_STICK] = gamepad.left_stick_button;
        curStates[BUTTON_RIGHT_STICK] = gamepad.right_stick_button;

        for (int i = 0; i < 12; i++) {
            pressed[i] = !down[i] && curStates[i];
            released[i] = down[i] && !curStates[i];
            down[i] = curStates[i];
        }

        double x1 = gamepad.left_stick_x;
        double y1 = -gamepad.left_stick_y;
        double x2 = gamepad.right_stick_x;
        double y2 = -gamepad.right_stick_y;

        double SQRT_2 = Math.sqrt(2);
        double radius1 = Math.sqrt(x1*x1+y1*y1);
        double angle1 = Math.atan2(y1, x1);

        double radius2 = Math.sqrt(x2 * x2 + y2 * y2);
        double angle2 = Math.atan2(y2, x2);

        if (x1 == 1 || x1 == -1 || y1 == 1 || y1 == -1) {
            radius1 = SQRT_2;

            if (x1 == 1) {
                angle1 = Math.asin(y1/radius1);
            } else if (x1 == -1) {
                angle1 = Math.PI - Math.asin(y1/radius1);
            } else if (y1 == 1) {
                angle1 = Math.acos(x1/radius1);
            } else if (y1 == -1) {
                angle1 = 2*Math.PI - Math.acos(x1/radius1);
            }
        }

        x1 = radius1 * Math.cos(angle1) * SQRT_2 * 0.5;
        y1 = radius1 * Math.sin(angle1) * SQRT_2 * 0.5;

        if (x2 == 1 || x2 == -1 || y2 == 1 || y2 == -1) {
            radius2 = SQRT_2;

            if (x2 == 1) {
                angle2 = Math.asin(y2/radius2);
            } else if (x2 == -1) {
                angle2 = Math.PI - Math.asin(y2/radius2);
            } else if (y2 == 1) {
                angle2 = Math.acos(x2/radius2);
            } else if (y2 == -1) {
                angle2 = 2*Math.PI - Math.acos(x2/radius2);
            }
        }

        x2 = radius2 * Math.cos(angle2) * SQRT_2 * 0.5;
        y2 = radius1 * Math.sin(angle2) * SQRT_2 * 0.5;

        stickValues[X_1] = x1;
        stickValues[Y_1] = y1;
        stickValues[R_1] = radius1;
        stickValues[THETA_1] = angle1;

        stickValues[X_2] = x2;
        stickValues[Y_2] = y2;
        stickValues[R_2] = radius2;
        stickValues[THETA_2] = angle2;

        dpad_down = gamepad.dpad_down;
        dpad_up = gamepad.dpad_up;
        dpad_left = gamepad.dpad_left;
        dpad_right = gamepad.dpad_right;

    }

    public synchronized boolean isDown(int buttonID) {
        return down[buttonID];
    }

    public synchronized boolean isPressed(int buttonID) {
        return pressed[buttonID];
    }

    public synchronized boolean isReleased(int buttonID) {
        return released[buttonID];
    }

    public synchronized double getValue(int ID) {
        return stickValues[ID];
    }

    public static int BUTTON_X = 0;
    public static int BUTTON_A = 1;
    public static int BUTTON_Y = 2;
    public static int BUTTON_B = 3;
    public static int BUTTON_LB = 4;
    public static int BUTTON_RB = 5;
    public static int BUTTON_LT = 6;
    public static int BUTTON_RT = 7;
    public static int BUTTON_BACK = 8;
    public static int BUTTON_START = 9;
    public static int BUTTON_LEFT_STICK = 10;
    public static int BUTTON_RIGHT_STICK = 11;

    public static int X_1 = 0;
    public static int Y_1 = 1;
    public static int R_1 = 2;
    public static int THETA_1 = 3;
    public static int X_2 = 4;
    public static int Y_2 = 5;
    public static int R_2 = 6;
    public static int THETA_2 = 7;
}
