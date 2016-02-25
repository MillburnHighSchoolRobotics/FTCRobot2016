package co.millburnrobotics.ftcscoutingapp;

import android.content.Context;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MenuActivity extends AppCompatActivity {

    private String selectedCompetition;
    
    private Button addTeam;
    private Button addCompetition;
    private Button addTeamToCompetition;
    private Button pickCompetition;
    private Button enterMatches;
    private Button viewMatches;
    private Button OptimalAlliance;
    private OnSwipeTouchListener detector;
    private GestureOverlayView gov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        loadIntent();
        OptimalAlliance = (Button) findViewById(R.id.OptimalAlliance);
        OptimalAlliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(AnalysisView.class);

            }
        });
        addTeam = (Button) findViewById(R.id.addTeam);
        addTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(AddTeamActivity.class);
            }
        });

        addCompetition = (Button) findViewById(R.id.addCompetition);
        addCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(AddCompetitionActivity.class);
            }
        });

        addTeamToCompetition = (Button) findViewById(R.id.addTeamToCompeition);
        addTeamToCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(AddTeamToCompetitionActivity.class);
            }
        });

        pickCompetition = (Button) findViewById(R.id.pickCompetition);
        pickCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(SelectCompetitionActivity.class);
            }
        });

        enterMatches = (Button) findViewById(R.id.enterMatches);
        enterMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(AddMatchesFrontActivity.class);
            }
        });

        viewMatches = (Button) findViewById(R.id.viewMatches);
        viewMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(ViewMatchDataActivity.class);
            }
        });

        detector = new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeUp() {
                goToPage(StartScreenActivity.class);
            }
        };

        //gov = (GestureOverlayView) findViewById(R.id.gestureOverlayView);
        //gov.setOnTouchListener(detector);
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
