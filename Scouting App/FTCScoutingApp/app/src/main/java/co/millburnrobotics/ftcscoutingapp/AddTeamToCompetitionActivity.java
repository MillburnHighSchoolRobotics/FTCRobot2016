package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddTeamToCompetitionActivity extends AppCompatActivity {

    private String selectedCompetition;

    private AdvButton addRemoveButton;
    private AdvButton submitButton;

    private Spinner teamSelector;
    private ListView teamListView;

    private List<Team> teamList;
    private List<Team> oldTeamList;
    private Integer[] teamIDs;
    private ArrayAdapter<Integer> teamIDAdapter;
    private Integer selectedTeamID;

    private List<Integer> teamListIDs;
    private ArrayAdapter<Integer> teamListAdapter;

    private Competition curComp;
    private List<Team> curTeams;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team_to_competition);

        loadIntent();

        addRemoveButton = new AdvButton((ImageButton) findViewById(R.id.add_remove_team), R.drawable.add_remove, R.drawable.add_remove_down);
        teamSelector = (Spinner) findViewById(R.id.team_selector);
        teamListView = (ListView) findViewById(R.id.listView);
        submitButton = new AdvButton((ImageButton) findViewById(R.id.submit), R.drawable.confirm, R.drawable.confirm_down);

        ParseQuery teamQuery = ParseQuery.getQuery(Team.class);
        teamQuery.orderByAscending(Team.NUMBER);

        try {
            teamList = teamQuery.find();
        } catch (ParseException e) {
            Toast.makeText(this, "Parse Query error", Toast.LENGTH_SHORT).show();
            return;
        }

        teamIDs = new Integer[teamList.size()];

        for (int i = 0; i < teamList.size(); i++) {
            teamIDs[i] = teamList.get(i).getNumber();
        }

        teamIDAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, teamIDs);
        teamSelector.setAdapter(teamIDAdapter);

        teamSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTeamID = (Integer) parent.getItemAtPosition(position);
            }


            public void onNothingSelected(AdapterView<?> parent) {
                selectedTeamID = null;
            }
        });

        teamListIDs = new ArrayList<Integer>();

        ParseQuery<Competition> compQuery = ParseQuery.getQuery(Competition.class);
        compQuery.fromLocalDatastore();
        try {
            curComp = compQuery.get(selectedCompetition);
        } catch (ParseException e) {
            Toast.makeText(this, "cannot get competition", Toast.LENGTH_SHORT).show();
            return;
        }

        ParseQuery<Team> tQ = curComp.getTeams().getQuery();
        tQ.orderByAscending(Team.NUMBER);
        tQ.fromLocalDatastore();
        try {
            curTeams = tQ.find();
            oldTeamList = new ArrayList<>(curTeams);
        } catch (ParseException e) {
            Toast.makeText(this, "cannot find current teams", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, Integer.toString(curTeams.size()), Toast.LENGTH_SHORT).show();

        for (int i = 0; i < curTeams.size(); i++) {
            teamListIDs.add(curTeams.get(i).getNumber());
        }

        teamListAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, teamListIDs);
        teamListView.setAdapter(teamListAdapter);

        teamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                teamSelector.setSelection(teamIDAdapter.getPosition((Integer) parent.getItemAtPosition(position)));
                selectedTeamID = (Integer) parent.getItemAtPosition(position);
            }
        });

        addRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAlreadyThere = false;
                int isAlreadyThereIndex = -1;
                for (int i = 0; i < teamListIDs.size(); i++) {
                    if (teamListIDs.get(i).equals(selectedTeamID)) {
                        isAlreadyThere = true;
                        isAlreadyThereIndex = i;
                    }
                }

                if (isAlreadyThere) {
                    teamListIDs.remove(isAlreadyThereIndex);
                } else {
                    teamListIDs.add(selectedTeamID);
                }

                teamListAdapter.notifyDataSetChanged();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContent();
                goToMenuPage();
            }
        });
    }

    private void loadIntent() {
        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra(IntentName.SELECTED_COMPETITION);
    }

    private void saveContent() {

        ParseRelation<Team> teamRelation = curComp.getRelation(Competition.TEAM);

        for (Team team : oldTeamList) {
            teamRelation.remove(team);
        }

        for (Integer i : teamListIDs) {
            Team dummy = new Team();
            dummy.setNumber(i);

            int loc = Collections.binarySearch(teamList, dummy, new Comparator<Team>() {
                @Override
                public int compare(Team a, Team b) {
                    return Integer.compare(a.getNumber(), b.getNumber());
                }
            });

            if (loc >= 0) {
                teamRelation.add(teamList.get(loc));
            }
        }

        try {
            curComp.save();
        } catch (ParseException e) {
            Toast.makeText(this, "cannot save comp", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void goToMenuPage() {
        Intent toMenu = new Intent(this, InnerMenuActivity.class);
        toMenu.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        startActivity(toMenu);
    }

    public void onBackPressed() {
        goToMenuPage();;
    }
}
