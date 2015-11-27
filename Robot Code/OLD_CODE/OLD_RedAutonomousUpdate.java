package com.qualcomm.ftcrobotcontroller.opmodes;

import virtualRobot.RedAutonomousLogic;

/**
 * Created by shant on 11/22/2015.
 */
public class RedAutonomousUpdate extends UpdateThread {
    @Override
    public void setLogicThread() {
        logicThread = RedAutonomousLogic.class;
    }
}
