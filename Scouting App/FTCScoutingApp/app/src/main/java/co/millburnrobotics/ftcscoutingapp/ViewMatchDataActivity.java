package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class ViewMatchDataActivity extends AppCompatActivity {

    private String selectedCompetition;

    private Competition curComp;
    private List<Team> teams;

    private Spinner teamNumber;
    private Integer[] teamList;
    private ArrayAdapter<Integer> teamAdapter;
    private Integer selectedTeamNumber;

    private Button viewByTeam;
    private Button viewByMatch;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match_data);

        loadIntent();

        teamNumber = (Spinner) findViewById(R.id.teamnumber);

        ParseQuery<Competition> compQuery = ParseQuery.getQuery(Competition.class);
        compQuery.fromLocalDatastore();
        try {
            curComp = compQuery.get(selectedCompetition);
        } catch (ParseException e) {
            Toast.makeText(this, "cannot find competition", Toast.LENGTH_SHORT).show();
            return;
        }

        ParseQuery<Team> query = curComp.getTeams().getQuery();
        query.orderByAscending(Team.NUMBER);
        query.fromLocalDatastore();

        try {
            teams = query.find();
        } catch (ParseException e) {
            return;
        }

        teamList = new Integer[teams.size()];
        for (int i = 0; i < teamList.length; i++) {
            teamList[i] = teams.get(i).getNumber();
        }

        teamAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, teamList);

        teamNumber.setAdapter(teamAdapter);
        teamNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTeamNumber = (Integer) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTeamNumber = null;
            }
        });

        viewByTeam = (Button) findViewById(R.id.findByTeam);
        viewByTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTeams();
            }
        });

        viewByMatch = (Button) findViewById(R.id.findByMatch);
        viewByMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMatches();
            }
        });

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenuPage();
            }
        });
    }

    public void onBackPressed() {
        goToMenuPage();
    }

    private void goToMenuPage() {
        Intent toMenu = new Intent(this, InnerMenuActivity.class);
        toMenu.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        startActivity(toMenu);
    }

    private void goToTeams() {
        Intent viewTeam = new Intent(this, ViewMatchDataTeamActivity.class);
        viewTeam.putExtra(IntentName.TEAM_NUMBER, selectedTeamNumber.intValue());
        viewTeam.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        startActivity(viewTeam);
    }

    private void goToMatches() {
        if (((EditText) findViewById(R.id.matchnumber)).getText().toString().length() == 0) {
            Toast.makeText(this, "no match available", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent viewMatch = new Intent(this, ViewMatchDataMatchActivity.class);
        viewMatch.putExtra(IntentName.MATCH_NUMBER, Integer.parseInt(((EditText) findViewById(R.id.matchnumber)).getText().toString()));
        viewMatch.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        startActivity(viewMatch);
    }

    private void loadIntent() {
        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra(IntentName.SELECTED_COMPETITION);
    }

}
