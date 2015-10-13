package com.example.anthony.ftcscoutingappfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
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
    String[] teamone = new String[8];
    String[] teamtwo = new String[8];
    String[] teamthree = new String[8];
    String[] teamfour = new String[8];
    String[] newstring = new String[5];
    JSONArray teamstwo = new JSONArray();
    JSONArray matchteams = new JSONArray();
    String teamstring, matchnumberq, teamnumber1s, test;
    TextView text;
    ParseObject teamlist, matchinfo, teamstats, teamstats2, teamstats3, teamstats4,teamstats5, team1tele;
    String list;
    String[] stringarray = new String[5];
    String data,team1id,team2id,team3id,team4id;
    String value;
    EditText T1Floor, T2Floor, T3Floor, T4Floor, T1Low, T2Low, T3Low, T4Low, T1Mid, T2Mid, T3Mid, T4Mid, T1High, T2High, T3High, T4High;
    EditText T1ClimbersTeleOp, T2ClimbersTeleOp, T3ClimbersTeleOp, T4ClimbersTeleOp, T1ZipLine, T2ZipLine, T3ZipLine, T4ZipLine;
    EditText T1ParkingEndgame, T2ParkingEndgame, T3ParkingEndgame, T4ParkingEndgame;
    EditText T1AllClear, T2AllClear, T3AllClear, T4AllClear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teleop_input_layout);

        Intent extras = getIntent();
        team1id = extras.getStringExtra("team1id");
        team2id = extras.getStringExtra("team2id");
        team3id = extras.getStringExtra("team3id");
        team4id = extras.getStringExtra("team4id");
        data = extras.getStringExtra("matchnumber");
        if(team1id==null){
            Log.i("qqq", "null");
        }
        Log.i("qqq", team1id);


        T1Floor = (EditText) findViewById(R.id.T1Floor);
        T2Floor = (EditText) findViewById(R.id.T2Floor);
        T3Floor = (EditText) findViewById(R.id.T3Floor);
        T4Floor = (EditText) findViewById(R.id.T4Floor);
        T1Low = (EditText) findViewById(R.id.T1Low);
        T2Low = (EditText) findViewById(R.id.T2Low);
        T3Low = (EditText) findViewById(R.id.T3Low);
        T4Low = (EditText) findViewById(R.id.T4Low);
        T1Mid = (EditText) findViewById(R.id.T1Mid);
        T2Mid = (EditText) findViewById(R.id.T2Mid);
        T3Mid = (EditText) findViewById(R.id.T3Mid);
        T4Mid = (EditText) findViewById(R.id.T4Mid);
        T1High = (EditText) findViewById(R.id.T1High);
        T2High = (EditText) findViewById(R.id.T2High);
        T3High = (EditText) findViewById(R.id.T3High);
        T4High = (EditText) findViewById(R.id.T4High);
        T1ClimbersTeleOp = (EditText) findViewById(R.id.T1ClimbersTeleOp);
        T2ClimbersTeleOp = (EditText) findViewById(R.id.T2ClimbersTeleOp);
        T3ClimbersTeleOp = (EditText) findViewById(R.id.T3ClimbersTeleOp);
        T4ClimbersTeleOp = (EditText) findViewById(R.id.T4ClimbersTeleOp);
        T1ZipLine = (EditText) findViewById(R.id.T1ZipLine);
        T2ZipLine = (EditText) findViewById(R.id.T2ZipLine);
        T3ZipLine = (EditText) findViewById(R.id.T3ZipLine);
        T4ZipLine = (EditText) findViewById(R.id.T4ZipLine);
        T1ParkingEndgame = (EditText) findViewById(R.id.T1ParkingEndgame);
        T2ParkingEndgame = (EditText) findViewById(R.id.T2ParkingEndgame);
        T3ParkingEndgame = (EditText) findViewById(R.id.T3ParkingEndgame);
        T4ParkingEndgame = (EditText) findViewById(R.id.T4ParkingEndgame);
        T1AllClear = (EditText) findViewById(R.id.T1AllClear);
        T2AllClear = (EditText) findViewById(R.id.T2AllClear);
        T3AllClear = (EditText) findViewById(R.id.T3AllClear);
        T4AllClear = (EditText) findViewById(R.id.T4AllClear);


       /* String value = getIntent().getStringExtra("matchnumber");
        Intent intent = new Intent(this, SecondPageActivity.class);
        intent.putExtra("matchnumber", value);
        data = getIntent().getStringExtra("matchnumber");*/
        teamstats = new ParseObject("TeamStats");
        teamstats2 = new ParseObject("TeamStats");
        teamstats3 = new ParseObject("TeamStats");
        teamstats4 = new ParseObject("TeamStats");
        team1tele = new ParseObject("TeamStats");
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

                } else {

                    Log.d("Error", e.getMessage());
                }

            }
        });



        Button nextpage = (Button) findViewById(R.id.TeleOpNext);
        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamone[0] = T1Floor.getText().toString();
                teamone[1] = T1Low.getText().toString();
                teamone[2] = T1Mid.getText().toString();
                teamone[3] = T1High.getText().toString();
                teamone[4] = T1ClimbersTeleOp.getText().toString();
                teamone[5] = T1ZipLine.getText().toString();
                teamone[6] = T1ParkingEndgame.getText().toString();
                teamone[7] = T1AllClear.getText().toString();
                teamtwo[0] = T2Floor.getText().toString();
                teamtwo[1] = T2Low.getText().toString();
                teamtwo[2] = T2Mid.getText().toString();
                teamtwo[3] = T2High.getText().toString();
                teamtwo[4] = T2ClimbersTeleOp.getText().toString();
                teamtwo[5] = T2ZipLine.getText().toString();
                teamtwo[6] = T2ParkingEndgame.getText().toString();
                teamtwo[7] = T2AllClear.getText().toString();
                teamthree[0] = T3Floor.getText().toString();
                teamthree[1] = T3Low.getText().toString();
                teamthree[2] = T3Mid.getText().toString();
                teamthree[3] = T3High.getText().toString();
                teamthree[4] = T3ClimbersTeleOp.getText().toString();
                teamthree[5] = T3ZipLine.getText().toString();
                teamthree[6] = T3ParkingEndgame.getText().toString();
                teamthree[7] = T3AllClear.getText().toString();
                teamfour[0] = T4Floor.getText().toString();
                teamfour[1] = T4Low.getText().toString();
                teamfour[2] = T4Mid.getText().toString();
                teamfour[3] = T4High.getText().toString();
                teamfour[4] = T4ClimbersTeleOp.getText().toString();
                teamfour[5] = T4ZipLine.getText().toString();
                teamfour[6] = T4ParkingEndgame.getText().toString();
                teamfour[7] = T4AllClear.getText().toString();
                Log.d("qqq", "floor");


                ParseQuery<ParseObject> query = ParseQuery.getQuery("TeamStats");
                query.getInBackground(team1id, new GetCallback<ParseObject>() {
                            public void done(ParseObject p, ParseException e) {
                                if (e == null) {
                                    p.put("Floor", "floor");
                                    p.put("Low", "low");
                                    p.put("Mid", teamone[2]);
                                    p.put("High", teamone[3]);
                                    p.put("ClimbersTele", teamone[4]);
                                    p.put("Zipline", teamone[5]);
                                    p.put("ParkingEnd", teamone[6]);
                                    p.put("AllClear", teamone[7]);
                                    p.saveInBackground();
                                } else {
                                    Log.d("Error", e.getMessage());
                                }

                            }
                        }
                );
                ParseQuery<ParseObject> query2 = ParseQuery.getQuery("TeamStats");
                query2.getInBackground(team2id, new GetCallback<ParseObject>() {
                            public void done(ParseObject p, ParseException e) {
                                if (e == null) {
                                    p.put("Floor", teamtwo[0]);
                                    p.put("Low", teamtwo[1]);
                                    p.put("Mid", teamtwo[2]);
                                    p.put("High", teamtwo[3]);
                                    p.put("ClimbersTele", teamtwo[4]);
                                    p.put("Zipline", teamtwo[5]);
                                    p.put("ParkingEnd", teamtwo[6]);
                                    p.put("AllClear", teamtwo[7]);
                                    p.saveInBackground();
                                } else {
                                    Log.d("Error", e.getMessage());
                                }

                            }
                        }
                );
                ParseQuery<ParseObject> query3 = ParseQuery.getQuery("TeamStats");
                query3.getInBackground(team3id, new GetCallback<ParseObject>() {
                            public void done(ParseObject p, ParseException e) {
                                if (e == null) {
                                    p.put("Floor", teamthree[0]);
                                    p.put("Low", teamthree[1]);
                                    p.put("Mid", teamthree[2]);
                                    p.put("High", teamthree[3]);
                                    p.put("ClimbersTele", teamthree[4]);
                                    p.put("Zipline", teamthree[5]);
                                    p.put("ParkingEnd", teamthree[6]);
                                    p.put("AllClear", teamthree[7]);
                                    p.saveInBackground();
                                } else {
                                    Log.d("Error", e.getMessage());
                                }

                            }
                        }
                );
                ParseQuery<ParseObject> query4 = ParseQuery.getQuery("TeamStats");
                query4.getInBackground(team4id, new GetCallback<ParseObject>() {
                            public void done(ParseObject p, ParseException e) {
                                if (e == null) {
                                    p.put("Floor", teamfour[0]);
                                    p.put("Low", teamfour[1]);
                                    p.put("Mid", teamfour[2]);
                                    p.put("High", teamfour[3]);
                                    p.put("ClimbersTele", teamfour[4]);
                                    p.put("Zipline", teamfour[5]);
                                    p.put("ParkingEnd", teamfour[6]);
                                    p.put("AllClear", teamfour[7]);
                                    p.saveInBackground();
                                } else {
                                    Log.d("Error", e.getMessage());
                                }

                            }
                        }
                );
                Intent teleoprestart = new Intent(v.getContext(), SecondPageActivity.class);
                startActivity(teleoprestart);
            }




            });

            Button backButton = (Button) findViewById(R.id.TeleOpBack);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent TeleopBack = new Intent(v.getContext(), AutonomousInputLayout.class);
                    startActivity(TeleopBack);
                }
            });



            }
    }



