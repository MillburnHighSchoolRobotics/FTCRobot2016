package com.qualcomm.ftcrobotcontroller.opmodes;

import virtualRobot.PIDTester;

/**
 * Created by Yanjun on 11/28/2015.
 */
public class PIDTesterUpdate extends UpdateThread {
    @Override
    public void setLogicThread() {
        logicThread = PIDTester.class;
    }
}
