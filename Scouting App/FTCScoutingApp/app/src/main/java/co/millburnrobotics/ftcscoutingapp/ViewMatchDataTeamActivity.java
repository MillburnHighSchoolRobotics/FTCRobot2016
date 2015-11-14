package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class ViewMatchDataTeamActivity extends AppCompatActivity {
ListView mainListView;
ArrayAdapter<String> listAdapter;
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



    }

}
