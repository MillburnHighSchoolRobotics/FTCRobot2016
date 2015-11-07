package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddMatchesAutonomousActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matches_autonomous);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        //setting the team number values
        ((TextView) findViewById(R.id.T1TXT)).setText(i.getStringExtra("team1"));
        ((TextView) findViewById(R.id.T2TXT)).setText(i.getStringExtra("team2"));
        ((TextView) findViewById(R.id.T3TXT)).setText(i.getStringExtra("team3"));
        ((TextView) findViewById(R.id.T4TXT)).setText(i.getStringExtra("team4"));
        //INSERT PARSE INIT STUFF HERE

        final Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toFront = new Intent(view.getContext(), AddMatchesFrontActivity.class);
                startActivity(toFront);
            }
        });

        final Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTeleop = new Intent (v.getContext(), AddMatchesTeleop.class);
                toTeleop.putExtra("team1", ((TextView)findViewById(R.id.T1TXT)).getText());
                toTeleop.putExtra("team2", ((TextView)findViewById(R.id.T2TXT)).getText());
                toTeleop.putExtra("team3", ((TextView)findViewById(R.id.T3TXT)).getText());
                toTeleop.putExtra("team4", ((TextView)findViewById(R.id.T4TXT)).getText());
                startActivity(toTeleop);
            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

}
