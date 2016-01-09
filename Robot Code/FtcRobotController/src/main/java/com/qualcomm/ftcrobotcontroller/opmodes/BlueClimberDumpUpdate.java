package com.qualcomm.ftcrobotcontroller.opmodes;

import virtualRobot.godThreads.BlueAutoGodThread;

/**
 * Created by Yanjun on 11/28/2015.
 */
public class BlueClimberDumpUpdate extends UpdateThread {
    @Override
    public void setGodThread() {
        godThread = BlueAutoGodThread.class;
    }
}
