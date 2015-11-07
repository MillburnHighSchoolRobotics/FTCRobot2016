package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //INSERT PARSE INIT STUFF HERE

        final Button addTeam = (Button) findViewById(R.id.addTeam);
        addTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAddTeam = new Intent(view.getContext(), MenuActivity.class);
                startActivity(toAddTeam);
            }
        });

        final Button addCompetition = (Button) findViewById(R.id.addCompetition);
        addCompetition.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toAddCompetition = new Intent(v.getContext(), AddCompetitionActivity.class);
                startActivity(toAddCompetition);
            }
        });

        final Button pickCompetition = (Button) findViewById(R.id.pickCompetition);
        pickCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSelectCompetition = new Intent(v.getContext(), SelectCompetitionActivity.class);
                startActivity(toSelectCompetition);
            }
        });

        final Button enterMatches = (Button) findViewById(R.id.enterMatches);
        enterMatches.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toAddMatches = new Intent(v.getContext(), AddMatchesActivity.class);
                startActivity(toAddMatches);
            }
        });

        final Button viewMatches = (Button) findViewById(R.id.viewMatches);
        viewMatches.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toViewMatchData = new Intent(v.getContext(), ViewMatchDataActivity.class);
                startActivity(toViewMatchData);
            }
        });

    }

}
