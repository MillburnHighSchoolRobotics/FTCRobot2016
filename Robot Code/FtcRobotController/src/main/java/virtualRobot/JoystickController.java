package virtualRobot;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Created by Yanjun on 11/12/2015.
 */
public class JoystickController {
    AtomicReferenceArray<Boolean> down, pressed, released;
    AtomicReferenceArray<Double> stickValues;
    AtomicBoolean dpad_up, dpad_down, dpad_left, dpad_right;

    public JoystickController() {
        down = new AtomicReferenceArray<Boolean>(12);
        pressed = new AtomicReferenceArray<Boolean>(12);
        released = new AtomicReferenceArray<Boolean>(12);

        for (int i = 0; i < 12; i++) {
            down.set(i, false);
            pressed.set(i, false);
            released.set(i, false);
        }

        stickValues = new AtomicReferenceArray<Double>(8);

        for (int i = 0; i < 8; i++) {
            stickValues.set(i, 0.0);
        }

        dpad_up = new AtomicBoolean();
        dpad_down = new AtomicBoolean();
        dpad_left = new AtomicBoolean();
        dpad_right = new AtomicBoolean();

        dpad_up.set(false);
        dpad_down.set(false);
        dpad_left.set(false);
        dpad_right.set(false);
    }

    public synchronized void copyStates(Gamepad gamepad) throws RobotCoreException {
        boolean[] curStates = new boolean[12];

        synchronized (gamepad) {
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
        }

        for (int i = 0; i < 12; i++) {
            pressed.set(i, !down.get(i) && curStates[i]);
            released.set(i, down.get(i) && !curStates[i]);
            down.set(i, curStates[i]);
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

        stickValues.set(X_1, x1);
        stickValues.set(Y_1, y1);
        stickValues.set(R_1, radius1);
        stickValues.set(THETA_1, angle1);

        stickValues.set(X_2, x2);
        stickValues.set(Y_2, y2);
        stickValues.set(R_2, radius2);
        stickValues.set(THETA_2, angle2);

        dpad_down.set(gamepad.dpad_down);
        dpad_up.set(gamepad.dpad_up);
        dpad_left.set(gamepad.dpad_left);
        dpad_right.set(gamepad.dpad_right);

    }

    public synchronized boolean isDown(int buttonID) {
        return down.get(buttonID);
    }

    public synchronized boolean isPressed(int buttonID) {
        return pressed.get(buttonID);
    }

    public synchronized boolean isReleased(int buttonID) {
        return released.get(buttonID);
    }

    public synchronized double getValue(int ID) {
        return stickValues.get(ID);
    }

    public synchronized boolean isDpadUp() {
        return dpad_up.get();
    }

    public synchronized boolean isDpadDown() {
        return dpad_down.get();
    }

    public synchronized boolean isDpadLeft() {
        return dpad_left.get();
    }

    public synchronized boolean isDpadRight() {
        return dpad_right.get();
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
