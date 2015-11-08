package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.parse.*;
import java.util.*;

public class ViewMatchDataTeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match_data_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        int teamNumber = i.getIntExtra("TeamNumber", -1);
        ((TextView) findViewById(R.id.title)).setText("Showing all matches for Team " + teamNumber);

        ParseQuery<MatchData> mdQuery = ParseQuery.getQuery(MatchData.class);
        mdQuery.whereEqualTo(MatchData.TEAM_NUMBER, teamNumber);
        mdQuery.orderByAscending(MatchData.MATCH_NUMBER);
        List<MatchData> matches = null;
        try {
            matches = mdQuery.find();
        } catch (ParseException e) {
            return;
        }



    }

}
