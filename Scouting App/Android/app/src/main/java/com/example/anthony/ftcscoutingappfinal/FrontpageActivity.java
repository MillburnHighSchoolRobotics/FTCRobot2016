package com.example.anthony.ftcscoutingappfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.json.JSONArray;

import java.text.SimpleDateFormat;

public class FrontpageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontpage);

        Button enterteam = (Button) findViewById(R.id.EnterTeamButton);

        Intent skip = getIntent();

        if (skip.getBooleanExtra("SkipInit", false) == false) {
            Parse.enableLocalDatastore(this);
            ParseObject.registerSubclass(Competition.class);
            ParseObject.registerSubclass(Match.class);
            ParseObject.registerSubclass(MatchData.class);
            ParseObject.registerSubclass(Team.class);
            Parse.initialize(this, "SlG9zvrlCyjen53XU3WUaf3HAYoZQpra08iCLQNC", "vyRgs4rAN6Ukj6qPfm2fzKNXlTbV8n3ALVringOF");
        }

        enterteam.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                EditText teamNumber = (EditText) findViewById(R.id.TeamEnterEditText);
                EditText teamName = (EditText) findViewById(R.id.teamname);

                String name = teamName.getText().toString();
                int number = Integer.parseInt(teamNumber.getText().toString());

                teamNumber.setText("");
                teamName.setText("");

                Log.i("qqq", name);
                Log.i("qqq", Integer.toString(number));

                Team team = new Team();

                team.setName(name);
                team.setNumber(number);

                team.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Toast toast = Toast.makeText(v.getContext(), "Team not saved", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            Toast toast = Toast.makeText(v.getContext(), "Team saved", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
            }
        });

        final EditText competitionDate = (EditText)findViewById(R.id.date);
        final EditText competitionTeam = (EditText)findViewById(R.id.teamname);
        final EditText competitionType = (EditText)findViewById(R.id.type);
        final EditText competitionName = (EditText) findViewById(R.id.compname);
        final Button competitions = (Button)findViewById(R.id.competition);
        final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        competitions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Competition comp = new Competition();

                comp.setHostingTeamID(Integer.parseInt(competitionTeam.getText().toString()));

                try {
                    comp.setDate(sdf.parse(competitionDate.getText().toString()));
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                comp.setType(competitionType.getText().toString());
                comp.setName(competitionName.getText().toString());

                comp.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(v.getContext(), "Competition saved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), "Competiton not saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
