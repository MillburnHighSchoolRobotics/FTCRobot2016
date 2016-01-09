package com.qualcomm.ftcrobotcontroller.opmodes;

import virtualRobot.logicThreads.BlueMountainLogic;

/**
 * Created by shant on 12/2/2015.
 */
public class BlueMountainUpdate extends UpdateThread {
    @Override
    public void setLogicThread() {
        logicThread = BlueMountainLogic.class;
    }
}
