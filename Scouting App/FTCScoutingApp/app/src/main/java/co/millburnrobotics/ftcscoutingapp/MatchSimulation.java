package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MatchSimulation extends AppCompatActivity {
    private EditText Blue1;
    private EditText Blue2;
    private EditText Red1;
    private EditText Red2;
    private Button Enter;
    private TextView WinningTeam;
    private Button backpage;


    private String selectedCompetition;
    private List<Team> teamList;
    private List<String> teamliststring;
    private Competition curComp;
    private Match curMatch;
    private List<MatchData> matchDataz;
    private MatchData[] alldata;
    private ArrayList<Integer> alliances;

    private void loadIntent() {
        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra(IntentName.SELECTED_COMPETITION);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_simulation);
        Blue1 = (EditText)findViewById(R.id.blue1team);
        Blue2 = (EditText)findViewById(R.id.blue2team);
        Red1 = (EditText)findViewById(R.id.red1team);
        Red2 = (EditText)findViewById(R.id.red2team);
        Enter = (Button) findViewById(R.id.enterteams);
        WinningTeam = (TextView) findViewById(R.id.winningalliance);
        backpage = (Button)findViewById(R.id.backsimlulation);
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

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int blueteam1 = Integer.parseInt(Blue1.getText().toString());
                int blueteam2 = Integer.parseInt(Blue2.getText().toString());
                int redteam1 = Integer.parseInt(Red1.getText().toString());
                int redteam2 = Integer.parseInt(Red2.getText().toString());

                Team theBlue1 = null;
                Team theBlue2 = null;
                Team theRed1 = null;
                Team theRed2 = null;
                for (Team team : teamList) {
                    if (blueteam1 == team.getNumber()) {
                        theBlue1 = team;
                        break;
                    }

                }
                for (Team team : teamList){
                    if(blueteam2 == team.getNumber()){
                        theBlue2 = team;
                        break;
                    }
                }
                for (Team team : teamList){
                    if(redteam1 == team.getNumber()){
                        theRed1 = team;
                        break;
                    }
                }
                for (Team team : teamList){
                    if(redteam2 == team.getNumber()){
                        theRed2 = team;
                        break;
                    }
                }

                MatchData blue1avg = Analysis.getAverageResults(curComp,theBlue1);
                MatchData blue2avg = Analysis.getAverageResults(curComp, theBlue2);
                MatchData red1avg = Analysis.getAverageResults(curComp, theRed1);
                MatchData red2avg = Analysis.getAverageResults(curComp,theRed2);

                MatchData[] alliance1 = new MatchData[2];
                alliance1[0] = Analysis.getAverageResults(curComp,theBlue1);
                alliance1[1] = Analysis.getAverageResults(curComp, theBlue2);
                MatchData[] alliance2 = new MatchData[2];
                alliance2[0] = Analysis.getAverageResults(curComp,theRed1);
                alliance2[1] = Analysis.getAverageResults(curComp, theRed2);


                Analysis.SimulationResult matchwinner = new Analysis.SimulationResult();
                matchwinner = Analysis.simulateRound(alliance1,alliance2);

            }
        });

        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToMenuPage();
            }
        });
    }
    private void goToMenuPage() {
        Intent toMenu = new Intent(this, InnerMenuActivity.class);
        toMenu.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        startActivity(toMenu);
    }




}
