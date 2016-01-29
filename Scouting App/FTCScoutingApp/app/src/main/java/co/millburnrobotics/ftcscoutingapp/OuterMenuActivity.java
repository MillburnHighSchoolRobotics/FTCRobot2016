package co.millburnrobotics.ftcscoutingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OuterMenuActivity extends AppCompatActivity {

    private AdvButton addTeam;
    private AdvButton addCompetition;
    private AdvButton selectCompetition;
    private AdvButton refresh;

    private OnSwipeTouchListener detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_outer_menu);

        addTeam = new AdvButton((ImageButton) findViewById(R.id.addTeam), R.drawable.new_team, R.drawable.new_team_down);
        addTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(AddTeamActivity.class);
            }
        });

        addCompetition = new AdvButton((ImageButton) findViewById(R.id.addCompetition), R.drawable.new_comp, R.drawable.new_comp_down);
        addCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(AddCompetitionActivity.class);
            }
        });

        selectCompetition = new AdvButton((ImageButton) findViewById(R.id.selectCompetition), R.drawable.select_comp, R.drawable.select_comp_down);
        selectCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(SelectCompetitionActivity.class);
            }
        });

        refresh = new AdvButton((ImageButton) findViewById(R.id.refresh), R.drawable.refresh, R.drawable.refresh_down);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(StartScreenActivity.class);
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

    private void goToPage(Class<? extends Context> dest) {
        Intent toPage = new Intent(this, dest);
        startActivity(toPage);
    }
}
