package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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

    String selectedCompetition = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matches_front);


        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra("SelectedCompetition");
        //INSERT PARSE INIT STUFF HERE

        final Button backButton = (Button) findViewById(R.id.toMenuPage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final EditText matchNumber = (EditText) findViewById(R.id.matchnumber);
        final Spinner team1 = (Spinner) findViewById(R.id.team1);
        final Spinner team2 = (Spinner) findViewById(R.id.team2);
        final Spinner team3 = (Spinner) findViewById(R.id.team3);
        final Spinner team4 = (Spinner) findViewById(R.id.team4);

        ParseQuery<Team> query = ParseQuery.getQuery(Team.class);
        query.orderByAscending(Team.NUMBER);
        List<Team> teamList = null;
        try {
            teamList = query.find();
        } catch (ParseException e) {
            Toast.makeText(this, "Could Not Download Teams", Toast.LENGTH_SHORT).show();
        }

        List<String> teamNumbers = new ArrayList<String>();
        final Map<String, Team> teamMap = new HashMap<String, Team>();

        for (Team t: teamList) {
            teamNumbers.add(Integer.toString(t.getNumber()));
            teamMap.put(Integer.toString(t.getNumber()), t);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, teamNumbers);

        team1.setAdapter(adapter);
        team2.setAdapter(adapter);
        team3.setAdapter(adapter);
        team4.setAdapter(adapter);

        final String[] selectedIDs = {"", "", "", ""};

        team1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedIDs[0] = (String) parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView<?> parent) {
                selectedIDs[0] = "";
            }
        });

        team2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedIDs[1] = (String) parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView<?> parent) {
                selectedIDs[1] = "";
            }
        });

        team3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedIDs[2] = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                selectedIDs[2] = "";
            }
        });

        team4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedIDs[3] = (String) parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView<?> parent) {
                selectedIDs[3] = "";
            }
        });

        final Button next = (Button) findViewById(R.id.toTeleopPage);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                Match match = new Match();
                match.setMatchNumber(Integer.parseInt(matchNumber.getText().toString()));

                ParseQuery<Competition> query = ParseQuery.getQuery(Competition.class);
                Competition competition = null;
                try {
                    competition = query.get(selectedCompetition);
                } catch (ParseException e) {
                    return;
                }

                match.setCompetitionName(competition.getName());
                match.setCompetitionDate(competition.getDate());

                MatchData md1 = new MatchData();
                MatchData md2 = new MatchData();
                MatchData md3 = new MatchData();
                MatchData md4 = new MatchData();

                md1.setCompetitionName(competition.getName());
                md2.setCompetitionName(competition.getName());
                md3.setCompetitionName(competition.getName());
                md4.setCompetitionName(competition.getName());

                md1.setCompetitionDate(competition.getDate());
                md2.setCompetitionDate(competition.getDate());
                md3.setCompetitionDate(competition.getDate());
                md4.setCompetitionDate(competition.getDate());

                md1.setTeamNumber(teamMap.get(selectedIDs[0]).getNumber());
                md2.setTeamNumber(teamMap.get(selectedIDs[1]).getNumber());
                md3.setTeamNumber(teamMap.get(selectedIDs[2]).getNumber());
                md4.setTeamNumber(teamMap.get(selectedIDs[3]).getNumber());

                md1.setAllianceColor(MatchData.RED_1);
                md2.setAllianceColor(MatchData.RED_2);
                md3.setAllianceColor(MatchData.BLUE_1);
                md4.setAllianceColor(MatchData.BLUE_2);

                try {
                    md1.save();
                    md2.save();
                    md3.save();
                    md4.save();
                } catch (ParseException e) {
                    return;
                }

                match.addMatchData(md1);
                match.addMatchData(md2);
                match.addMatchData(md3);
                match.addMatchData(md4);

                try {
                    match.save();
                } catch (ParseException e) {
                    return;
                }

                Intent toAutonomous = new Intent(v.getContext(), AddMatchesAutonomousActivity.class);
                toAutonomous.putExtra("SelectedCompetition", selectedCompetition);
                toAutonomous.putExtra("SelectedMatch", match.getObjectId());
                startActivity(toAutonomous);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent toMenu = new Intent(this, MenuActivity.class);
        toMenu.putExtra("SelectedCompetition", selectedCompetition);
        startActivity(toMenu);
    }

}
