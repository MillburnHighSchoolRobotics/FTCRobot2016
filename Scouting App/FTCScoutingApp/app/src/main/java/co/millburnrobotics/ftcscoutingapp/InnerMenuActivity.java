package co.millburnrobotics.ftcscoutingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class InnerMenuActivity extends AppCompatActivity {

    private String selectedCompetition;
    private AdvButton addTeamToCompetition;
    private AdvButton enterMatches;
    private AdvButton viewMatches;
    private AdvButton selectCompetition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decor = getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_inner_menu);

        loadIntent();


        addTeamToCompetition = new AdvButton((ImageButton) findViewById(R.id.addTeamToCompetition), R.drawable.add_remove_team, R.drawable.add_remove_team_down);
        addTeamToCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(AddTeamToCompetitionActivity.class);
            }
        });

        selectCompetition = new AdvButton((ImageButton) findViewById(R.id.selectCompetition), R.drawable.select_comp, R.drawable.select_comp_down);
        selectCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(SelectCompetitionActivity.class);
            }
        });

        enterMatches = new AdvButton((ImageButton) findViewById(R.id.enterMatches), R.drawable.add_team, R.drawable.add_team_down);
        enterMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(AddMatchesFrontActivity.class);
            }
        });

        viewMatches = new AdvButton((ImageButton) findViewById(R.id.viewMatches), R.drawable.view_match, R.drawable.view_match_down);
        viewMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(ViewMatchDataActivity.class);
            }
        });
    }

    public void onBackPressed() {
        goToPage(OuterMenuActivity.class);
    }

    private void loadIntent() {
        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra(IntentName.SELECTED_COMPETITION);
    }

    private void goToPage(Class<? extends Context> dest) {
        Intent toPage = new Intent(this, dest);
        toPage.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        startActivity(toPage);
    }

}
