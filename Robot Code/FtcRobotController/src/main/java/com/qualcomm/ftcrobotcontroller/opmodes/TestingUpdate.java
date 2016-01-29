package com.qualcomm.ftcrobotcontroller.opmodes;

import virtualRobot.godThreads.TestingGodThread;

/**
 * Created by shant on 1/15/2016.
 */
public class TestingUpdate extends UpdateThread {
    @Override
    public void setGodThread() {
        godThread = TestingGodThread.class;
    }
}
