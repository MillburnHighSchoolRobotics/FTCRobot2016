package com.qualcomm.ftcrobotcontroller.opmodes;

import virtualRobot.Teleop1Logic;

/**
 * Created by shant on 11/12/2015.
 */
public class Teleop1Update extends UpdateThread {
    @Override
    public void setLogicThread() {
        logicThread = Teleop1Logic.class;
    }
}
