package com.qualcomm.ftcrobotcontroller.opmodes;

import virtualRobot.RedAutonomousLogic;

/**
 * Created by Yanjun on 11/28/2015.
 */
public class RedAutoUpdate extends UpdateThread {
    @Override
    public void setLogicThread() {
        logicThread = RedAutonomousLogic.class;
    }
}
