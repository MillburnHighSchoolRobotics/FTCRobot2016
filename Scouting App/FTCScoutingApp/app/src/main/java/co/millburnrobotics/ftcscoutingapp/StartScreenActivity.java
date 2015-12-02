package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.Timer;
import java.util.TimerTask;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        //INSERT PARSE INIT STUFF HERE

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(StartScreenActivity.this, MenuActivity.class));
            }
        }, 1500);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

}
