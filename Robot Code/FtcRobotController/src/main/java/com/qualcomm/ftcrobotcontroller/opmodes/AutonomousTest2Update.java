package com.qualcomm.ftcrobotcontroller.opmodes;

import virtualRobot.*;

/**
 * Created by Yanjun on 11/10/2015.
 */
public class AutonomousTest2Update extends UpdateThread {
    @Override
    public void setLogicThread() {
        logicThread = AutonomousTest2Logic.class;
    }

}
