package com.example.anthony.ftcscoutingappfinal;

import android.app.Activity;
import android.os.Bundle;

public class TeleopInputLayout extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teleop_input_layout);
    }


}
