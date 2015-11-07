package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddMatchesTeleop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matches_teleop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        ((TextView) findViewById(R.id.T1TXT)).setText(i.getStringExtra("team1"));
        ((TextView) findViewById(R.id.T2TXT)).setText(i.getStringExtra("team2"));
        ((TextView) findViewById(R.id.T3TXT)).setText(i.getStringExtra("team3"));
        ((TextView) findViewById(R.id.T4TXT)).setText(i.getStringExtra("team4"));

        final Button back = (Button) findViewById(R.id.TeleOpBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAuto = new Intent(view.getContext(), AddMatchesAutonomousActivity.class);
                startActivity(toAuto);
            }
        });

        final Button next = (Button) findViewById(R.id.TeleOpNext);
        next.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toFront = new Intent (v.getContext(), AddMatchesFrontActivity.class);
                startActivity(toFront);
            }
        }));

    }

}
