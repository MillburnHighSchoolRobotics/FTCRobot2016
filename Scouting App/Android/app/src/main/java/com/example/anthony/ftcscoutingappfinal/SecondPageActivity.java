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


public class SecondPageActivity extends Activity {
    Button addmatch;

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
    String teamstring, matchnumberq, teamnumber1s, teamnumber2s,teamnumber3s,teamnumber4s,test;
    TextView text;
    ParseObject teamlist, matchinfo, teamstats, teamstats2, teamstats3, teamstats4;
    String list;
    String[] teamnumbers = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_page);


        teamnumber1 = (EditText) findViewById(R.id.Team1AutoComplete);
        teamnumber2 = (EditText) findViewById(R.id.Team2AutoComplete);
        teamnumber3 = (EditText) findViewById(R.id.Team3AutoComplete);
        teamnumber4 = (EditText) findViewById(R.id.Team4AutoComplete);
        text = (TextView) findViewById(R.id.text);
        matchnumber = (EditText) findViewById(R.id.matchnumber);
        matchinfo = new ParseObject("Matchinformation");



        final Button confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Matchinformation");
                query.whereEqualTo("MatchNumber", matchnumber.getText().toString());
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

                        } else {
                            Log.d("Error", e.getMessage());
                        }

                    }
                });


            }
        });
        addmatch = (Button) findViewById(R.id.addmatch);
        addmatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                teamnumbers[0] = teamnumber1.getText().toString();
                teamnumbers[1] = teamnumber2.getText().toString();
                teamnumbers[2] = teamnumber3.getText().toString();
                teamnumbers[3] = teamnumber4.getText().toString();
                String matchnumbers = matchnumber.getText().toString();
                Log.i("qqq", teamnumbers[0]);
                Log.i("qqq", matchnumbers);
                matchinfo.add("Teams", teamnumbers[0]);
                matchinfo.add("Teams", teamnumbers[1]);
                matchinfo.add("Teams", teamnumbers[2]);
                matchinfo.add("Teams", teamnumbers[3]);
                matchinfo.put("MatchNumber", matchnumbers);


                matchinfo.saveEventually();


            }
        });
        Button clear = (Button)findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchnumber.setText("");
                teamnumber1.setText("");
                teamnumber2.setText("");
                teamnumber3.setText("");
                teamnumber4.setText("");

            }
        });

                            Intent intent = new Intent(getBaseContext(), AutonomousInputLayout.class);
                            intent.putExtra("matchnumber", matchnumber.getText().toString());
                            //startActivity(intent);
                            //Intent intent2 = new Intent(getBaseContext(), TeleopInputLayout.class);
                            //intent2.putExtra("matchnumber", matchnumber.getText().toString());
                            //startActivity(intent2);

        final Button backpage = (Button)findViewById(R.id.secondback);
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act2 = new Intent(view.getContext(), FrontpageActivity.class);
                act2.putExtra("SkipInit", true);
                startActivity(act2);
            }
        });

        final Button switchact =(Button)findViewById(R.id.secondnext);
        switchact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent act1 = new Intent(view.getContext(), AutonomousInputLayout.class);
                act1.putExtra("matchnumber", matchnumber.getText().toString());
                startActivity(act1);

            }
        });
    }


}
