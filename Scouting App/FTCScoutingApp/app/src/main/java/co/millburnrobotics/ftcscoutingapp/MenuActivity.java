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
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //INSERT PARSE INIT STUFF HERE

        Intent incoming = getIntent();
        final String selectedCompetition = incoming.getStringExtra("SelectedCompetition");

        final Button addTeam = (Button) findViewById(R.id.addTeam);
        addTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAddTeam = new Intent(view.getContext(), AddTeamActivity.class);
                toAddTeam.putExtra("SelectedCompetition", selectedCompetition);
                startActivity(toAddTeam);
            }
        });

        final Button addCompetition = (Button) findViewById(R.id.addCompetition);
        addCompetition.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toAddCompetition = new Intent(v.getContext(), AddCompetitionActivity.class);
                toAddCompetition.putExtra("SelectedCompetition", selectedCompetition);
                startActivity(toAddCompetition);
            }
        });

        final Button pickCompetition = (Button) findViewById(R.id.pickCompetition);
        pickCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSelectCompetition = new Intent(v.getContext(), SelectCompetitionActivity.class);
                toSelectCompetition.putExtra("SelectedCompetition", selectedCompetition);
                startActivity(toSelectCompetition);
            }
        });

        final Button enterMatches = (Button) findViewById(R.id.enterMatches);
        enterMatches.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toAddMatches = new Intent(v.getContext(), AddMatchesFrontActivity.class);
                toAddMatches.putExtra("SelectedCompetition", selectedCompetition);
                startActivity(toAddMatches);
            }
        });

        final Button viewMatches = (Button) findViewById(R.id.viewMatches);
        viewMatches.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toViewMatchData = new Intent(v.getContext(), ViewMatchDataActivity.class);
                toViewMatchData.putExtra("SelectedCompetition", selectedCompetition);
                startActivity(toViewMatchData);
            }
        });

    }

}
