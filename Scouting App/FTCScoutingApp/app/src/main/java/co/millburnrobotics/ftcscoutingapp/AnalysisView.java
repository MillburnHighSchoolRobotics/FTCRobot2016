package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AnalysisView extends AppCompatActivity {
    private String selectedCompetition;
    private int matchNumber;
    private List<Team> teamList;
    private List<String> teamliststring;
    private Competition curComp;
    private Match curMatch;
    private List<MatchData> matchDataz;
    private MatchData[] alldata;
    private ArrayList<Integer> alliances;
    private Button back;
    private void loadIntent() {
        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra(IntentName.SELECTED_COMPETITION);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysis_view);
        loadIntent();

        Button enter = (Button)(findViewById(R.id.btnEnterTeam));
        final EditText teamnumber = (EditText)(findViewById(R.id.teamnumber));

        ListView list = (ListView)(findViewById(R.id.listView3));
        //////////////////////////////////////////////////////////////////////////////
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


        //////////////////////////////////////////////////////////////////////////
        // find average for all teams
       int intteamlist = teamList.size();
       alldata = new MatchData[intteamlist];
        for(int i =0; i < teamList.size();i++){
           alldata[i]= Analysis.getAverageResults(curComp, teamList.get(i));
        }

        final ArrayAdapter<Integer> teamListAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, alliances);
        list.setAdapter(teamListAdapter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int teamNumber = Integer.parseInt(teamnumber.getText().toString());
                Team theTeam = null;
                for (Team team : teamList) {
                    if (teamNumber == team.getNumber()) {
                        theTeam = team;
                        break;
                    }
                }

                alliances = (ArrayList<Integer>) Analysis.findOptimalAlliance(alldata, theTeam);
                teamListAdapter.notifyDataSetChanged();

            }
        });
        back = (Button) findViewById(R.id.BackAlliance);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenuPage();
            }
        });

    } private void goToMenuPage() {
        Intent toMenu = new Intent(this, InnerMenuActivity.class);
        toMenu.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        startActivity(toMenu);
    }    }

