package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class ViewMatchDataTeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match_data_team);

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
        ListView yourListView = (ListView) findViewById(R.id.listOfMatches);

// get data from the table by the ListAdapter
        //ListAdapter customAdapter = new ListAdapter(this, R.layout.acti, List<yourItem>);

       // yourListView .setAdapter(customAdapter);


    }

}
