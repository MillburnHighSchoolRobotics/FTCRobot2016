package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class ViewMatchDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match_data);


        Intent incoming = getIntent();
        final String selectedCompetition = incoming.getStringExtra("SelectedCompetition");

        final Spinner teamNumber = (Spinner) findViewById(R.id.teamnumber);

        ParseQuery<Team> query = ParseQuery.getQuery(Team.class);
        query.orderByAscending(Team.NUMBER);
        List<Team> teamList = null;
        try {
            teamList = query.find();
        } catch (ParseException e) {
            return;
        }

        String[] teamNumberStrings = new String[teamList.size()];
        for (int i = 0; i < teamNumberStrings.length; i++) {
            teamNumberStrings[i] = Integer.toString(teamList.get(i).getNumber());
        }

        final int[] selectedTeamNumber = new int[1];

        teamNumber.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, teamNumberStrings));
        teamNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTeamNumber[0] = Integer.parseInt((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTeamNumber[0] = 0;
            }
        });

        final Button viewByTeam = (Button) findViewById(R.id.findByTeam);
        viewByTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewTeam = new Intent(view.getContext(), ViewMatchDataTeamActivity.class);
                viewTeam.putExtra("TeamNumber", selectedTeamNumber[0]);
                viewTeam.putExtra("SelectedCompetition", selectedCompetition);
                startActivity(viewTeam);
            }
        });

        final Button viewByMatch = (Button) findViewById(R.id.findByMatch);
        viewByMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewMatch = new Intent(view.getContext(), ViewMatchDataMatchActivity.class);
                viewMatch.putExtra("MatchNumber", Integer.parseInt(((EditText) findViewById(R.id.matchnumber)).getText().toString()));
                viewMatch.putExtra("SelectedCompetition", selectedCompetition);
                startActivity(viewMatch);
            }
        });

        final Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMenu = new Intent(view.getContext(), MenuActivity.class);
                toMenu.putExtra("SelectedCompetition", selectedCompetition);
                startActivity(toMenu);
            }
        });
    }

}
