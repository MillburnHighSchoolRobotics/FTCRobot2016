package co.millburnrobotics.ftcscoutingapp;

import android.widget.AdapterView;

/**
 * Created by shant on 11/13/2015.
 */
public abstract class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    protected int myID;

    public MyOnItemSelectedListener(int id) {
        this.myID = id;
    }
}
