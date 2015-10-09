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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.util.List;

public class AutonomousInputLayout extends Activity {
    EditText teamnumber, matchnumber, teamnumber1, teamnumber2, teamnumber3, teamnumber4;
    Boolean loaded = false;
    String id;
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
    String data,idteam1,idteam2,idteam3,idteam4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autonomous_input_layout);

        String value= getIntent().getStringExtra("matchnumber");
        Intent intent = new Intent(this, SecondPageActivity.class);
        intent.putExtra("matchnumber", value);

        data = getIntent().getStringExtra("matchnumber");

        final TextView team1 = (TextView) findViewById(R.id.T1TXT);
        final TextView team2 = (TextView) findViewById(R.id.T2TXT);
        final TextView team3 = (TextView) findViewById(R.id.T3TXT);
        final TextView team4 = (TextView) findViewById(R.id.T4TXT);

        // replace with whatever is queried from match info

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
       // team1.setText(data[0]);
        //team2.setText(data[1]);
        //team3.setText(data[2]);
        //team4.setText(data[3]);


        teamstats = new ParseObject("TeamStats");
        teamstats2 = new ParseObject("TeamStats");
        teamstats3 = new ParseObject("TeamStats");
        teamstats4 = new ParseObject("TeamStats");


        final Button switchact =(Button)findViewById(R.id.back);
        switchact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent act1 = new Intent(view.getContext(), SecondPageActivity.class);
                startActivity(act1);

            }
        });

        final Button switchact2 =(Button)findViewById(R.id.next);
        switchact2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText T1RB, T1CL, T1PK, T2RB, T2CL, T2PK, T3RB, T3CL, T3PK, T4RB, T4CL, T4PK;
                T1RB = (EditText) findViewById(R.id.T1Beacon);
                T2RB = (EditText) findViewById(R.id.T2Beacon);
                T3RB = (EditText) findViewById(R.id.T3Beacon);
                T4RB = (EditText) findViewById(R.id.T4Beacon);
                T1CL = (EditText) findViewById(R.id.T1ClimbersAuto);
                T2CL = (EditText) findViewById(R.id.T2ClimbersAuto);
                T3CL = (EditText) findViewById(R.id.T3ClimbersAuto);
                T4CL = (EditText) findViewById(R.id.T4ClimbersAuto);
                T1PK = (EditText) findViewById(R.id.T1ParkingAuto);
                T2PK = (EditText) findViewById(R.id.T2ParkingAuto);
                T3PK = (EditText) findViewById(R.id.T3ParkingAuto);
                T4PK = (EditText) findViewById(R.id.T4ParkingAuto);
                String team1s = team1.getText().toString();
                String team2s = team2.getText().toString();
                String team3s = team3.getText().toString();
                String team4s = team4.getText().toString();
                teamone[0] = T1RB.getText().toString();
                teamone[1] = T1CL.getText().toString();
                teamone[2] = T1PK.getText().toString();
                teamtwo[0] = T2RB.getText().toString();
                teamtwo[1] = T2CL.getText().toString();
                teamtwo[2] = T2PK.getText().toString();
                teamthree[0] = T3RB.getText().toString();
                teamthree[1] = T3CL.getText().toString();
                teamthree[2] = T3PK.getText().toString();
                teamfour[0] = T4RB.getText().toString();
                teamfour[1] = T4CL.getText().toString();
                teamfour[2] = T4PK.getText().toString();


                teamstats.put("TeamNumber", team1s);
                teamstats.put("Beacon", teamone[0]);
                teamstats.put("ClimberAuto", teamone[1]);
                teamstats.put("ParkingAuto", teamone[2]);

                teamstats2.put("TeamNumber", team2s);
                teamstats2.put("Beacon", teamtwo[0]);
                teamstats2.put("ClimberAuto", teamtwo[1]);
                teamstats2.put("ParkingAuto", teamtwo[2]);

                teamstats3.put("TeamNumber", team3s);
                teamstats3.put("Beacon", teamthree[0]);
                teamstats3.put("ClimberAuto", teamthree[1]);
                teamstats3.put("ParkingAuto", teamthree[2]);

                teamstats4.put("TeamNumber", team4s);
                teamstats4.put("Beacon", teamfour[0]);
                teamstats4.put("ClimberAuto", teamfour[1]);
                teamstats4.put("ParkingAuto", teamfour[2]);


                try {
                    teamstats.save();
                    teamstats2.save();
                    teamstats3.save();
                    teamstats4.save();
                } catch (ParseException e) {
                    Log.d("qqq", String.valueOf(e));
                }
                idteam1 = teamstats.getObjectId();
                idteam2 = teamstats2.getObjectId();
                idteam3 = teamstats3.getObjectId();
                idteam4 = teamstats4.getObjectId();
                Log.d("qqq", "The object id is: " + idteam1);



                Intent i = new Intent(view.getContext(), TeleopInputLayout.class);
                i.putExtra("team1id",idteam1);
                i.putExtra("team2id",idteam2);
                i.putExtra("team3id",idteam3);
                i.putExtra("team4id",idteam4);
                i.putExtra("matchnumber", data);
                startActivity(i);


                //Intent act2 = new Intent(view.getContext(), TeleopInputLayout.class);
                //startActivity(act2);

            }
        });
    }
}
