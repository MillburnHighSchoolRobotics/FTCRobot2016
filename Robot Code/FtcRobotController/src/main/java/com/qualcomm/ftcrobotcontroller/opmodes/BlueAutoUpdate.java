package com.qualcomm.ftcrobotcontroller.opmodes;

import virtualRobot.BlueAutonomousLogic;

/**
 * Created by Yanjun on 11/28/2015.
 */
public class BlueAutoUpdate extends UpdateThread {
    @Override
    public void setLogicThread() {
        logicThread = BlueAutonomousLogic.class;
    }
}
