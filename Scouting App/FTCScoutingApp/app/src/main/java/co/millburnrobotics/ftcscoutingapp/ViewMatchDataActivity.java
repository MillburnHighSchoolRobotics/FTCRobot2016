package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ViewMatchDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent incoming = getIntent();
        final String selectedCompetition = incoming.getStringExtra("SelectedCompetition");

        final Button viewByTeam = (Button) findViewById(R.id.findByTeam);
        viewByTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewTeam = new Intent(view.getContext(), MenuActivity.class);
                viewTeam.putExtra("TeamNumber", Integer.parseInt(((EditText) findViewById(R.id.teamnumber)).getText().toString()));
                viewTeam.putExtra("SelectedCompetition", selectedCompetition);
                startActivity(viewTeam);
            }
        });

        final Button viewByMatch = (Button) findViewById(R.id.findByMatch);
        viewByMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewMatch = new Intent(view.getContext(), MenuActivity.class);
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
