package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMatchesFrontActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matches_front);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //INSERT PARSE INIT STUFF HERE

        final Button switchact = (Button) findViewById(R.id.toMenuPage);
        switchact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMenu = new Intent(view.getContext(), MenuActivity.class);
                startActivity(toMenu);
            }
        });

        final Button next = (Button) findViewById(R.id.toTeleopPage);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toTeleop = new Intent(v.getContext(), AddMatchesTeleop.class);
                toTeleop.putExtra("team1", ((EditText)findViewById(R.id.Team1AutoComplete)).getText());
                toTeleop.putExtra("team2", ((EditText)findViewById(R.id.Team2AutoComplete)).getText());
                toTeleop.putExtra("team3", ((EditText)findViewById(R.id.Team3AutoComplete)).getText());
                toTeleop.putExtra("team4", ((EditText)findViewById(R.id.Team4AutoComplete)).getText());
                startActivity(toTeleop);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

}
