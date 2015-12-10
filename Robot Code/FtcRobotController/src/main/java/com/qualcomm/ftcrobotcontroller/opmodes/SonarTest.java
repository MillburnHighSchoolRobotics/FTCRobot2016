package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;

/**
 * Created by Yanjun on 12/9/2015.
 */
public class SonarTest extends OpMode {

    AnalogInput sonar;

    @Override
    public void init() {
        sonar = hardwareMap.analogInput.get("sonar");
    }

    @Override
    public void loop() {
        telemetry.addData("Sonar: ", sonar.getValue());
    }
}
