package com.qualcomm.ftcrobotcontroller.opmodes;

import virtualRobot.godThreads.PIDTesterGodThread;

/**
 * Created by Yanjun on 11/28/2015.
 */
public class PIDTesterUpdate extends UpdateThread {
    @Override
    public void setGodThread() {
        godThread = PIDTesterGodThread.class;
    }
}
