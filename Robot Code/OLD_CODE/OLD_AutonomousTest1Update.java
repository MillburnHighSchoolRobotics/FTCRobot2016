package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Yanjun on 11/5/2015.
 */
public class OLD_AutonomousTest1Update extends UpdateThread {

    @Override
    public void setLogicThread() {
        logicThread = AutonomousTest1Logic.class;
    }
}
