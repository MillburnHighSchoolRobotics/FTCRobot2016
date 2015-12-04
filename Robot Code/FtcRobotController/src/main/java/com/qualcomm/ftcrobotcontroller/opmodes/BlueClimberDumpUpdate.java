package com.qualcomm.ftcrobotcontroller.opmodes;

import virtualRobot.BlueClimberDumpLogic;

/**
 * Created by Yanjun on 11/28/2015.
 */
public class BlueClimberDumpUpdate extends UpdateThread {
    @Override
    public void setLogicThread() {
        logicThread = BlueClimberDumpLogic.class;
    }
}
