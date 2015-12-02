package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.parse.*;
import java.util.*;

public class AddTeamToCompetitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team_to_competition);

        Intent incoming = getIntent();
        final String selectedCompetition = incoming.getStringExtra("SelectedCompetition");

        final Button addRemoveButton = (Button) findViewById(R.id.add_remove_team);
        final Spinner teamSelector = (Spinner) findViewById(R.id.team_selector);
        final ListView teamList = (ListView) findViewById(R.id.listView);
        final Button submitButton = (Button) findViewById(R.id.submit);

        ParseQuery teamQuery = ParseQuery.getQuery(Team.class);
        teamQuery.orderByAscending(Team.NUMBER);
        List<Team> teams = null;
        try {
            teams = teamQuery.find();
        } catch (ParseException e) {
            Toast.makeText(this, "Parse Query error", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] teamIDs = new String[teams.size()];
        for (int i = 0; i < teams.size(); i++) {
            teamIDs[i] = Integer.toString(teams.get(i).getNumber());
        }

        final ArrayAdapter<String> teamIDAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, teamIDs);
        teamSelector.setAdapter(teamIDAdapter);

        final String[] selectedTeamID = new String[1];
        teamSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTeamID[0] = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTeamID[0] = "";
            }
        });

        final List<String> teamListIDs = new ArrayList<String>();

        ParseQuery<Competition> compQuery = ParseQuery.getQuery(Competition.class);
        Competition comp = null;
        try {
            comp = compQuery.get(selectedCompetition);
        } catch (ParseException e) {
            Toast.makeText(this, "cannot get competition", Toast.LENGTH_SHORT).show();
            return;
        }

        ParseRelation<Team> teamRel = comp.getTeams();
        List<Team> curTeams = null;
        try {
            ParseQuery<Team> tQ = teamRel.getQuery();
            tQ.orderByAscending(Team.NUMBER);
            curTeams = tQ.find();
        } catch (ParseException e) {
            Toast.makeText(this, "cannot find current teams", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < curTeams.size(); i++) {
            teamListIDs.add(Integer.toString(curTeams.get(i).getNumber()));
        }

        final ArrayAdapter<String> teamListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teamListIDs);
        teamList.setAdapter(teamListAdapter);

        teamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                teamSelector.setSelection(teamIDAdapter.getPosition((String) parent.getItemAtPosition(position)));
                selectedTeamID[0] = (String) parent.getItemAtPosition(position);
            }
        });

        addRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAlreadyThere = false;
                int isAlreadyThereIndex = -1;
                for (int i = 0; i < teamListIDs.size(); i++) {
                    if (teamListIDs.get(i).equals(selectedTeamID[0])) {
                        isAlreadyThere = true;
                        isAlreadyThereIndex = i;
                    }
                }

                if (isAlreadyThere) {
                    teamListIDs.remove(isAlreadyThereIndex);
                } else {
                    teamListIDs.add(selectedTeamID[0]);
                    Log.e("Adding Team", selectedTeamID[0]);
                }

                teamListAdapter.notifyDataSetChanged();

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<Competition> compQuery = ParseQuery.getQuery(Competition.class);
                Competition comp = null;
                try {
                    comp = compQuery.get(selectedCompetition);
                } catch (ParseException e) {
                    Toast.makeText(v.getContext(), "cannot get competition", Toast.LENGTH_SHORT).show();
                    return;
                }

                ParseRelation<Team> teamRelation = comp.getRelation(Competition.TEAM);
                ParseQuery teamRelationQuery = teamRelation.getQuery();
                List<Team> teamRelationList = null;

                try {
                    teamRelationList = teamRelationQuery.find();
                } catch (ParseException e) {
                    Toast.makeText(v.getContext(), "cannot find relation", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = 0; i < teamRelationList.size(); i++) {
                    teamRelation.remove(teamRelationList.get(i));
                }

                List<Integer> teamParseQueryOrs = new ArrayList<Integer>();

                for (int i = 0; i < teamListIDs.size(); i++) {
                    teamParseQueryOrs.add(Integer.parseInt(teamListIDs.get(i)));
                }

                ParseQuery<Team> teamParseQuery = ParseQuery.getQuery(Team.class);
                teamParseQuery.whereContainedIn(Team.NUMBER, teamParseQueryOrs);
                teamParseQuery.setLimit(150);

                List<Team> teamRelationListToAdd = null;
                try {
                    teamRelationListToAdd = teamParseQuery.find();
                    Toast.makeText(v.getContext(), Integer.toString(teamRelationListToAdd.size()), Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    Toast.makeText(v.getContext(), "cannot find teams", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = 0; i < teamRelationListToAdd.size(); i++) {
                    teamRelation.add(teamRelationListToAdd.get(i));
                }

                try {
                    comp.save();
                } catch (ParseException e) {
                    Toast.makeText(v.getContext(), "cannot save comp", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
