package com.qualcomm.ftcrobotcontroller.opmodes;

import virtualRobot.*;

/**
 * Created by Yanjun on 11/5/2015.
 */
public class AutonomousTest1Update extends UpdateThread {

    @Override
    public void setLogicThread() {
        logicThread = AutonomousTest1Logic.class;
    }
}
