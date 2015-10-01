package com.example.anthony.ftcscoutingappfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.util.List;

public class TeleopInputLayout extends Activity {
    EditText teamnumber, matchnumber, teamnumber1, teamnumber2, teamnumber3, teamnumber4;
    Boolean loaded = false;
    String[] teams = new String[5];
    String[] teams2 = new String[5];
    String[] teams3 = new String[5];
    String[] secpageteams = new String[4];
    String[] turtlestwo = new String[5];
    String[] turtles = new String[5];
    String[] secondarray = new String[4];
    String[] teamone = new String[3];
    String[] teamtwo = new String[3];
    String[] teamthree = new String[3];
    String[] teamfour = new String[3];
    String[] newstring = new String[5];
    JSONArray teamstwo = new JSONArray();
    JSONArray matchteams = new JSONArray();
    String teamstring, matchnumberq, teamnumber1s, test;
    TextView text;
    ParseObject teamlist, matchinfo, teamstats, teamstats2, teamstats3, teamstats4;
    String list;
    String[] stringarray = new String[5];
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teleop_input_layout);

        EditText T1Floor,T2Floor,T3Floor,T4Floor,T1Low,T2Low,T3Low,T4Low,T1Mid,T2Mid,T3Mid,T4Mid, T1High,T2High,T3High,T4High;
        EditText T1ClimbersTeleOp,T2ClimbersTeleOp,T3ClimbersTeleOp,T4ClimbersTeleOp,T1ZipLine,T2ZipLine,T3ZipLine,T4ZipLine;
        EditText T1ParkingEndgame,T2ParkingEndgame,T3ParkingEndgame,T4ParkingEndgame;
        EditText T1AllClear,T2AllClear,T3AllClear,T4AllClear;
        T1Floor = (EditText)findViewById(R.id.T1Floor);
        T2Floor = (EditText)findViewById(R.id.T2Floor);
        T3Floor = (EditText)findViewById(R.id.T3Floor);
        T4Floor = (EditText)findViewById(R.id.T4Floor);
        T1Low = (EditText)findViewById(R.id.T1Low);
        T2Low = (EditText)findViewById(R.id.T2Low);
        T3Low = (EditText)findViewById(R.id.T3Low);
        T4Low = (EditText)findViewById(R.id.T4Low);
        T1Mid = (EditText)findViewById(R.id.T1Mid);
        T2Mid = (EditText)findViewById(R.id.T2Mid);
        T3Mid = (EditText)findViewById(R.id.T3Mid);
        T4Mid = (EditText)findViewById(R.id.T4Mid);
        T1High = (EditText)findViewById(R.id.T1High);
        T2High = (EditText)findViewById(R.id.T2High);
        T3High = (EditText)findViewById(R.id.T3High);
        T4High = (EditText)findViewById(R.id.T4High);
        T1ClimbersTeleOp = (EditText)findViewById(R.id.T1ClimbersTeleOp);
        T2ClimbersTeleOp = (EditText)findViewById(R.id.T2ClimbersTeleOp);
        T3ClimbersTeleOp = (EditText)findViewById(R.id.T3ClimbersTeleOp);
        T4ClimbersTeleOp = (EditText)findViewById(R.id.T4ClimbersTeleOp);
        T1ZipLine = (EditText) findViewById(R.id.T1ZipLine);
        T2ZipLine = (EditText) findViewById(R.id.T2ZipLine);
        T3ZipLine = (EditText) findViewById(R.id.T3ZipLine);
        T4ZipLine = (EditText) findViewById(R.id.T4ZipLine);
        T1ParkingEndgame = (EditText)findViewById(R.id.T1ParkingEndgame);
        T2ParkingEndgame = (EditText)findViewById(R.id.T2ParkingEndgame);
        T3ParkingEndgame = (EditText)findViewById(R.id.T3ParkingEndgame);
        T4ParkingEndgame = (EditText)findViewById(R.id.T4ParkingEndgame);
        T1AllClear = (EditText)findViewById(R.id.T1AllClear);
        T2AllClear = (EditText)findViewById(R.id.T2AllClear);
        T3AllClear = (EditText)findViewById(R.id.T3AllClear);
        T4AllClear = (EditText)findViewById(R.id.T4AllClear);

        String value= getIntent().getStringExtra("matchnumber");
        Intent intent = new Intent(this, SecondPageActivity.class);
        intent.putExtra("matchnumber", value);
        data = getIntent().getStringExtra("matchnumber");

        final TextView team1 = (TextView) findViewById(R.id.T1TXT);
        final TextView team2 = (TextView) findViewById(R.id.T2TXT);
        final TextView team3 = (TextView) findViewById(R.id.T3TXT);
        final TextView team4 = (TextView) findViewById(R.id.T4TXT);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Matchinformation");
        query.whereEqualTo("MatchNumber", data);
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> l, ParseException e) {

                if (e == null) {

                    for (int i = 0; i < l.size(); i++) {
                        matchteams = (l.get(i).getJSONArray("Teams"));
                        test = matchteams.toString();
                        secondarray = test.split(",");
                        for (int d = 0; d < 4; d++) {
                            teams3[d] = secondarray[d].replaceAll("[^\\d.]", "");
                        }
                        team1.setText(teams3[0]);
                        team2.setText(teams3[1]);
                        team3.setText(teams3[2]);
                        team4.setText(teams3[3]);
                    }

                }
                else {

                    Log.d("Error", e.getMessage());
                }

            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TeamStats");
        query.whereEqualTo("TeamNumber", );
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> l, ParseException e) {

                if (e == null) {

                    for (int i = 0; i < l.size(); i++) {
                        matchteams = (l.get(i).getJSONArray("Teams"));
                        test = matchteams.toString();
                        secondarray = test.split(",");
                        for (int d = 0; d < 4; d++) {
                            teams3[d] = secondarray[d].replaceAll("[^\\d.]", "");
                        }
                        teamnumber1.setText(teams3[0]);
                        teamnumber2.setText(teams3[1]);
                        teamnumber3.setText(teams3[2]);
                        teamnumber4.setText(teams3[3]);

                    }
                    Intent intent = new Intent(getBaseContext(), AutonomousInputLayout.class);
                    intent.putExtra("matchnumber", matchnumber.getText().toString());
                    startActivity(intent);
                } else {
                    Log.d("Error", e.getMessage());
                }

            }
        });
    }


}
