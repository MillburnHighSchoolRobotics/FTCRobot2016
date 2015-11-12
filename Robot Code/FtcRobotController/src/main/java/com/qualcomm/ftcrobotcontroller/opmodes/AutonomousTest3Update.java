package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Yanjun on 11/12/2015.
 */

import virtualRobot.*;


public class AutonomousTest3Update extends UpdateThread {
    @Override
    public void setLogicThread() {
        logicThread = AutonomousTest3Logic.class;
    }
}
