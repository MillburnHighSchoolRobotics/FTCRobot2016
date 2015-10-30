package com.example.anthony.ftcscoutingappfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;

import org.json.JSONArray;

public class FrontpageActivity extends Activity {
    EditText teamnumber,teamname,competitiondate, competitiontype, competitionteam;
    Boolean loaded = false;
    String competitiondates;
    String[] teams = new String[5];
    JSONArray teamstwo = new JSONArray();
    String teamstring,teamnames,competitionteams,competitiontypes;
    ParseObject teamlist, competition;
    String list;
    String[] stringarray = new String[5];
    String teamnamestring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontpage);

        Button enterteam = (Button) findViewById(R.id.EnterTeamButton);



        Intent skip = getIntent();
        if (skip.getBooleanExtra("SkipInit", false) == false) {
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "SlG9zvrlCyjen53XU3WUaf3HAYoZQpra08iCLQNC", "vyRgs4rAN6Ukj6qPfm2fzKNXlTbV8n3ALVringOF");
        }
        teamlist = new ParseObject("Teams");
        enterteam.setOnClickListener(new View.OnClickListener() {

            int a = 0;

            public void onClick(View v) {
                teamnumber = (EditText) findViewById(R.id.TeamEnterEditText);
                teamname = (EditText)findViewById(R.id.teamname);
                teamstring = teamnumber.getText().toString();
                teamnames = teamname.getText().toString();
                teams[a] = teamstring;
                teamnamestring = teamnames;
                teamnumber.setText("");
                teamname.setText("");
                Log.i("qqq", teams[a]);
                Log.i("qqq", teamnamestring);
                teamlist.add("teamnumber", teams[a]);
                teamlist.put("teamname", teamnamestring);
                try {
                   teamlist.save();
                } catch (ParseException e) {
                    Log.d("qqq", String.valueOf(e));
                }
                a++;
                if (a > 24) {
                    teamnumber.setText("Max");
                }
            }
        });
        competition = new ParseObject("Competition");
        competitiondate = (EditText)findViewById(R.id.date);
        competitionteam = (EditText)findViewById(R.id.teamname);
        competitiontype = (EditText)findViewById(R.id.type);
        competitiondates = competitiondate.getText().toString();
        competitionteams = competitionteam.getText().toString();
        competitiontypes = competitiontype.getText().toString();
        final Button competitions = (Button)findViewById(R.id.competition);
        competitions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                competition.put("Date", competitiondates);


                competition.put("Team", competitionteams);
                competition.put("Type", competitiontypes);

            }
        });


        final Button switchact = (Button) findViewById(R.id.done);
        switchact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act2 = new Intent(view.getContext(), SecondPageActivity.class);
                startActivity(act2);

            }
        });
    }


}
