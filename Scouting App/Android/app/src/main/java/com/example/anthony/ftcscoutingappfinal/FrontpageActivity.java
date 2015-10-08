package com.example.anthony.ftcscoutingappfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;

public class FrontpageActivity extends Activity {
    EditText teamnumber;
    Boolean loaded = false;
    String[] teams = new String[5];
    JSONArray teamstwo = new JSONArray();
    String teamstring;
    ParseObject teamlist;
    String list;
    String[] stringarray = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontpage);

        Button enterteam = (Button) findViewById(R.id.EnterTeamButton);
        teamlist = new ParseObject("TestObject3");


        Intent skip = getIntent();
        if (skip.getBooleanExtra("SkipInit", false) == false) {
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "SlG9zvrlCyjen53XU3WUaf3HAYoZQpra08iCLQNC", "vyRgs4rAN6Ukj6qPfm2fzKNXlTbV8n3ALVringOF");
        }

        enterteam.setOnClickListener(new View.OnClickListener() {

            int a = 0;

            public void onClick(View v) {
                teamnumber = (EditText) findViewById(R.id.TeamEnterEditText);
                teamstring = teamnumber.getText().toString();
                teams[a] = teamstring;
                teamnumber.setText("");
                teamlist.add("team", teams[a]);
                teamlist.saveInBackground();
                a++;
                if (a > 4) {
                    teamnumber.setText("Max");
                }
            }
        });

        Button update = (Button) findViewById(R.id.LoadButton);
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject3");
                query.getInBackground("u1pHVHgYOU", new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {

                            teamstwo = object.getJSONArray("team");
                            list = teamstwo.toString();
                            stringarray = list.split(",");
                            for (int i = 0; i < 5; i++) {

                                teams[i] = stringarray[i].replaceAll("[^\\d.]", "");
                            }

                            loaded = true;
                        } else {

                            Log.i("qqq", String.valueOf(e));
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
