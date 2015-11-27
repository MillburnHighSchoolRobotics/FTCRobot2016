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
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.List;

public class AddMatchesTeleop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matches_teleop);


        Intent incoming = getIntent();

        final String selectedCompetition = incoming.getStringExtra("SelectedCompetition");
        final String selectedMatch = incoming.getStringExtra("SelectedMatch");

        ParseQuery<Match> matchQuery = ParseQuery.getQuery(Match.class);
        Match match = null;
        try {
            match = matchQuery.get(selectedMatch);
        } catch (ParseException e) {
            return;
        }

        ParseRelation<MatchData> matchDataz = match.getMatchDataz();

        ParseQuery<MatchData> md1Query = matchDataz.getQuery();
        ParseQuery<MatchData> md2Query = matchDataz.getQuery();
        ParseQuery<MatchData> md3Query = matchDataz.getQuery();
        ParseQuery<MatchData> md4Query = matchDataz.getQuery();

        md1Query.whereEqualTo(MatchData.ALLIANCE_COLOR, MatchData.RED_1);
        md2Query.whereEqualTo(MatchData.ALLIANCE_COLOR, MatchData.RED_2);
        md3Query.whereEqualTo(MatchData.ALLIANCE_COLOR, MatchData.BLUE_1);
        md4Query.whereEqualTo(MatchData.ALLIANCE_COLOR, MatchData.BLUE_2);

        final MatchData[] md = new MatchData[4];

        try {
            md[0] = md1Query.find().get(0);
            md[1] = md2Query.find().get(0);
            md[2] = md3Query.find().get(0);
            md[3] = md4Query.find().get(0);
        } catch (ParseException e) {
            return;
        }

        final TextView[] teamNumbers = new TextView[4];

        teamNumbers[0] = (TextView) findViewById(R.id.T1TXT);
        teamNumbers[1] = (TextView) findViewById(R.id.T2TXT);
        teamNumbers[2] = (TextView) findViewById(R.id.T3TXT);
        teamNumbers[3] = (TextView) findViewById(R.id.T4TXT);

        for (int i = 0; i < teamNumbers.length; i++) {
            teamNumbers[i].setText(Integer.toString(md[i].getTeamNumber()));
        }

        final EditText[] floorGoals = new EditText[4];
        final EditText[] lowGoals = new EditText[4];
        final EditText[] midGoals = new EditText[4];
        final EditText[] highGoals = new EditText[4];

        final Spinner[] climbers = new Spinner[4];
        final Spinner[] zipLines = new Spinner[4];
        final Spinner[] parkings = new Spinner[4];
        final Spinner[] allClears = new Spinner[4];

        floorGoals[0] = (EditText) findViewById(R.id.T1Floor);
        floorGoals[1] = (EditText) findViewById(R.id.T2Floor);
        floorGoals[2] = (EditText) findViewById(R.id.T3Floor);
        floorGoals[3] = (EditText) findViewById(R.id.T4Floor);

        lowGoals[0] = (EditText) findViewById(R.id.T1Low);
        lowGoals[1] = (EditText) findViewById(R.id.T2Low);
        lowGoals[2] = (EditText) findViewById(R.id.T3Low);
        lowGoals[3] = (EditText) findViewById(R.id.T4Low);

        midGoals[0] = (EditText) findViewById(R.id.T1Mid);
        midGoals[1] = (EditText) findViewById(R.id.T2Mid);
        midGoals[2] = (EditText) findViewById(R.id.T3Mid);
        midGoals[3] = (EditText) findViewById(R.id.T4Mid);

        highGoals[0] = (EditText) findViewById(R.id.T1High);
        highGoals[1] = (EditText) findViewById(R.id.T2High);
        highGoals[2] = (EditText) findViewById(R.id.T3High);
        highGoals[3] = (EditText) findViewById(R.id.T4High);

        climbers[0] = (Spinner) findViewById(R.id.T1ClimbersTeleOp);
        climbers[1] = (Spinner) findViewById(R.id.T2ClimbersTeleOp);
        climbers[2] = (Spinner) findViewById(R.id.T3ClimbersTeleOp);
        climbers[3] = (Spinner) findViewById(R.id.T4ClimbersTeleOp);

        zipLines[0] = (Spinner) findViewById(R.id.T1ZipLine);
        zipLines[1] = (Spinner) findViewById(R.id.T2ZipLine);
        zipLines[2] = (Spinner) findViewById(R.id.T3ZipLine);
        zipLines[3] = (Spinner) findViewById(R.id.T4ZipLine);

        parkings[0] = (Spinner) findViewById(R.id.T1ParkingEndgame);
        parkings[1] = (Spinner) findViewById(R.id.T2ParkingEndgame);
        parkings[2] = (Spinner) findViewById(R.id.T3ParkingEndgame);
        parkings[3] = (Spinner) findViewById(R.id.T4ParkingEndgame);

        allClears[0] = (Spinner) findViewById(R.id.T1AllClear);
        allClears[1] = (Spinner) findViewById(R.id.T2AllClear);
        allClears[2] = (Spinner) findViewById(R.id.T3AllClear);
        allClears[3] = (Spinner) findViewById(R.id.T4AllClear);

        List<String> climbersList = new ArrayList<String>();
        List<String> zipLinesList = new ArrayList<String>();
        List<String> parkingsList = new ArrayList<String>();
        List<String> allClearsList = new ArrayList<String>();


        climbersList.add("0");
        climbersList.add("1");
        climbersList.add("2");

        zipLinesList.add("0");
        zipLinesList.add("1");
        zipLinesList.add("2");
        zipLinesList.add("3");

        parkingsList.add(MatchData.FLOOR);
        parkingsList.add(MatchData.LOW);
        parkingsList.add(MatchData.MID);
        parkingsList.add(MatchData.HIGH);
        parkingsList.add(MatchData.PULL_UP);
        allClearsList.add("No");
        allClearsList.add("Yes");


        ArrayAdapter<String> climbersAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, climbersList);
        ArrayAdapter<String> zipLinesAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, zipLinesList);
        ArrayAdapter<String> parkingsAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, parkingsList);
        ArrayAdapter<String> allClearsAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, allClearsList);

        final String[] climbersSelected = {"", "", "", ""};
        final String[] zipLinesSelected = {"", "", "", ""};
        final String[] parkingsSelected = {"", "", "", ""};
        final String[] allClearsSelected = {"", "", "", ""};

        final int[] i = new int[1];

        for (i[0] = 0; i[0] < 4; i[0]++) {
            climbers[i[0]].setAdapter(climbersAdapter);
            zipLines[i[0]].setAdapter(zipLinesAdapter);
            parkings[i[0]].setAdapter(parkingsAdapter);
            allClears[i[0]].setAdapter(allClearsAdapter);

            climbers[i[0]].setOnItemSelectedListener(new MyOnItemSelectedListener(i[0]) {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    climbersSelected[myID] = (String) parent.getItemAtPosition(position);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    climbersSelected[myID] = "";
                }
            });

            zipLines[i[0]].setOnItemSelectedListener(new MyOnItemSelectedListener(i[0]) {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    zipLinesSelected[myID] = (String) parent.getItemAtPosition(position);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    zipLinesSelected[myID] = "";
                }
            });

            parkings[i[0]].setOnItemSelectedListener(new MyOnItemSelectedListener(i[0]) {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    parkingsSelected[myID] = (String) parent.getItemAtPosition(position);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    parkingsSelected[myID] = "";
                }
            });

            allClears[i[0]].setOnItemSelectedListener(new MyOnItemSelectedListener(i[0]) {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    allClearsSelected[myID] = (String) parent.getItemAtPosition(position);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    allClearsSelected[myID] = "";
                }
            });
        }

        final Button back = (Button) findViewById(R.id.TeleOpBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAuto = new Intent(view.getContext(), AddMatchesAutonomousActivity.class);
                toAuto.putExtra("SelectedCompetition", selectedCompetition);
                toAuto.putExtra("SelectedMatch", selectedMatch);
                startActivity(toAuto);
            }
        });

        final Button next = (Button) findViewById(R.id.TeleOpNext);
        next.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = 0;
                int c = 0;
                int b = 0;
                int d = 0;
                while (a < 4) {

                    if (floorGoals[a] != null && floorGoals[a].length() == 0) {
                        floorGoals[a].setText("0");
                    }
                    a++;
                }
                while (b < 4) {

                    if (lowGoals[b] != null && lowGoals[b].length() == 0) {
                        lowGoals[b].setText("0");
                    }
                    b++;
                }
                while (c < 4) {

                    if (midGoals[c] != null && midGoals[c].length() == 0) {
                        midGoals[c].setText("0");
                    }
                    c++;
                }
                while (d < 4) {

                    if (highGoals[d] != null && highGoals[d].length() == 0) {
                        highGoals[d].setText("0");
                    }
                    d++;
                }
                for (int i = 0; i < 4; i++) {


                    md[i].setTeleopFloorGoal(Integer.parseInt(floorGoals[i].getText().toString()));
                    md[i].setTeleopLowGoal(Integer.parseInt(lowGoals[i].getText().toString()));
                    md[i].setTeleopMidGoal(Integer.parseInt(midGoals[i].getText().toString()));
                    md[i].setTeleopHighGoal(Integer.parseInt(highGoals[i].getText().toString()));

                    md[i].setTeleopClimberInShelter(Integer.parseInt(climbersSelected[i]));
                    md[i].setTeleopClimberZipLine(Integer.parseInt(zipLinesSelected[i]));
                    md[i].setTeleopParking(parkingsSelected[i]);

                    if (allClearsSelected[i].equals("Yes")) {
                        md[i].setTeleopAllClear(true);
                    } else {
                        md[i].setTeleopAllClear(false);
                    }

                    try {
                        md[i].save();
                    } catch (ParseException e) {
                        return;
                    }
                }

                Intent toFront = new Intent(v.getContext(), AddMatchesFrontActivity.class);
                toFront.putExtra("SelectedCompetition", selectedCompetition);
                startActivity(toFront);
            }
        }));

    }

}
