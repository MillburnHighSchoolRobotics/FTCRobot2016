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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMatchesFrontActivity extends AppCompatActivity {

    private String selectedCompetition;
    private boolean doLoad;

    private Button back;
    private Button next;

    private EditText matchNumber;
    private Spinner[] teams;
    private Competition curComp;

    private List<Team> teamList;
    private List<Integer> teamNumbers;
    private Map<Integer, Team> teamMap;
    private ArrayAdapter<Integer> teamAdapter;
    private Integer[] selectedIDs;

    private Match curMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matches_front);

        loadIntent();

        back = (Button) findViewById(R.id.toMenuPage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenuPage();
            }
        });

        matchNumber = (EditText) findViewById(R.id.matchnumber);
        teams = new Spinner[4];

        teams[0] = (Spinner) findViewById(R.id.team1);
        teams[1] = (Spinner) findViewById(R.id.team2);
        teams[2] = (Spinner) findViewById(R.id.team3);
        teams[3] = (Spinner) findViewById(R.id.team4);

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
            teamList = query.find();
        } catch (ParseException e) {
            Toast.makeText(this, "Could Not Download Teams", Toast.LENGTH_SHORT).show();
            return;
        }

        teamNumbers = new ArrayList<>();
        teamMap = new HashMap<>();

        for (Team t : teamList) {
            teamNumbers.add(t.getNumber());
            teamMap.put(t.getNumber(), t);
        }

        teamAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, teamNumbers);

        for (Spinner teamSpinner : teams) {
            teamSpinner.setAdapter(teamAdapter);
        }

        selectedIDs = new Integer[4];

        for (int i = 0; i < 4; i++) {
            teams[i].setOnItemSelectedListener(new MyOnItemSelectedListener(i) {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedIDs[myID] = (Integer) parent.getItemAtPosition(position);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    selectedIDs[myID] = null;
                }
            });
        }

        next = (Button) findViewById(R.id.toTeleopPage);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContent();
                goToAutonomous();
            }
        });
    }

    private void loadIntent() {
        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra(IntentName.SELECTED_COMPETITION);
    }

    private void saveContent() {

        int matchID = Integer.parseInt(matchNumber.getText().toString());
        ParseQuery<Match> matchQuery = curComp.getMatches().getQuery();
        matchQuery.whereEqualTo(Match.MATCH_NUMBER, matchID);
        List<Match> tempList = null;
        try {
            tempList = matchQuery.find();
        } catch (ParseException e) {
            return;
        }

        if (tempList.size() != 0) {
            doLoad = true;
            curMatch = tempList.get(0);
            try {
                MatchData.pinAll(curMatch.getMatchDataz().getQuery().find());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            doLoad = false;
            curMatch = new Match();
            if (matchNumber.getText().toString().length() == 0) {
                Toast.makeText(this, "no match available", Toast.LENGTH_SHORT).show();
                return;
            }
            curMatch.setMatchNumber(matchID);

            curMatch.setCompetitionName(curComp.getName());
            curMatch.setCompetitionDate(curComp.getDate());

            MatchData md[] = new MatchData[4];
            for (int i = 0; i < 4; i++) {
                md[i] = new MatchData();
                md[i].setCompetitionName(curComp.getName());
                md[i].setCompetitionDate(curComp.getDate());
                md[i].setMatchNumber(curMatch.getMatchNumber());

                md[i].setTeamNumber(teamMap.get(selectedIDs[i]).getNumber());
            }

            md[0].setAllianceColor(MatchData.RED_1);
            md[1].setAllianceColor(MatchData.RED_2);
            md[2].setAllianceColor(MatchData.BLUE_1);
            md[3].setAllianceColor(MatchData.BLUE_2);

            try {
                for (MatchData m : md) {
                    m.save();
                    m.pin();
                    curMatch.addMatchData(m);
                }
            } catch (ParseException e) {
                return;
            }

            try {
                curMatch.save();
                curMatch.pin();
                MatchData.pinAll(curMatch.getMatchDataz().getQuery().find());
            } catch (ParseException e) {
                return;
            }

            curComp.getMatches().add(curMatch);

            try {
                curComp.save();
            } catch (ParseException e) {
                return;
            }
        }
    }

    private void goToAutonomous() {
        Intent toAutonomous = new Intent(this, AddMatchesAutonomousActivity.class);
        toAutonomous.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        toAutonomous.putExtra(IntentName.SELECTED_MATCH, curMatch.getObjectId());
        toAutonomous.putExtra(IntentName.DO_LOAD, doLoad);
        startActivity(toAutonomous);
    }

    private void goToMenuPage() {
        Intent toMenu = new Intent(this, InnerMenuActivity.class);
        toMenu.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        startActivity(toMenu);
    }

    public void onBackPressed() {
        goToMenuPage();
    }
}
