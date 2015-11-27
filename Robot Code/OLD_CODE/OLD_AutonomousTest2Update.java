package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Yanjun on 11/10/2015.
 */
public class OLD_AutonomousTest2Update extends UpdateThread {
    @Override
    public void setLogicThread() {
        logicThread = AutonomousTest2Logic.class;
    }

}
