package com.qualcomm.ftcrobotcontroller.opmodes.opmodes;

import virtualRobot.AutonomousTest1Logic;

/**
 * Created by Yanjun on 11/5/2015.
 */
public class AutonomousTest1Update extends UpdateThread {

    @Override
    public void setLogicThread() {
        logicThread = AutonomousTest1Logic.class;
    }
}
