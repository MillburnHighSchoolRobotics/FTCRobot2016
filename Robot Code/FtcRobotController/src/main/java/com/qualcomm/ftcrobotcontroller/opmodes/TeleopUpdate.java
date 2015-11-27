package com.qualcomm.ftcrobotcontroller.opmodes;

import virtualRobot.TeleopLogic;

/**
 * _____ ______   ___  ___  ________
 * |\   _ \  _   \|\  \|\  \|\   ____\             .-""-.
 * \ \  \\\__\ \  \ \  \\\  \ \  \___|_           /[] _ _\
 * \ \  \\|__| \  \ \   __  \ \_____  \         _|_o_LII|_
 * \ \  \    \ \  \ \  \ \  \|____|\  \       / | ==== | \
 * \ \__\    \ \__\ \__\ \__\____\_\  \      |_| ==== |_|
 * \|__|     \|__|\|__|\|__|\_________\      ||" ||  ||
 * \|_________|      ||LI  o ||
 * ||'----'||
 * /__|    |__\
 * <p>
 * Created by shant on 11/27/2015.
 */
public class TeleopUpdate extends UpdateThread {
    @Override
    public void setLogicThread() {
        logicThread = TeleopLogic.class;
    }

}
