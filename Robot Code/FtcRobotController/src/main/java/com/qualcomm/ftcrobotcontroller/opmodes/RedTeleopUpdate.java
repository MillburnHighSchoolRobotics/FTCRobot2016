package com.qualcomm.ftcrobotcontroller.opmodes;

import virtualRobot.godThreads.TeleopGodThread;

/**
 * Created by shant on 2/26/2016.
 */
public class RedTeleopUpdate extends UpdateThread {
    @Override
    public void setGodThread() {
        godThread = TeleopGodThread.class;
    }
}
