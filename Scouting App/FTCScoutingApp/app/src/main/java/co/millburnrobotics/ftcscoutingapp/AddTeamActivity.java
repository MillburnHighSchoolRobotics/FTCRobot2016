package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

public class AddTeamActivity extends AppCompatActivity {

    private String selectedCompetition;

    private AdvButton submit;

    private EditText teamNumber;
    private EditText teamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        loadIntent();

        teamNumber = (EditText) findViewById(R.id.TeamEnterEditText);
        teamName = (EditText) findViewById(R.id.teamname);

        submit = new AdvButton((ImageButton) findViewById(R.id.EnterTeamButton), R.drawable.confirm, R.drawable.confirm_down);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContent();
                clearContent();            }
        });
    }

    private void saveContent() {
        String szTeamNumber = teamName.getText().toString();
        if(szTeamNumber.length() == 0){
            Toast.makeText(this, "No team available", Toast.LENGTH_SHORT).show();
            return;
        }
        int nTeamNumber = Integer.parseInt(teamNumber.getText().toString());

        Team team = new Team();

        team.setName(szTeamNumber);
        team.setNumber(nTeamNumber);

        team.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(AddTeamActivity.this, "Team Saved on Parse", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddTeamActivity.this, "Team Not Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        team.pinInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(AddTeamActivity.this, "Team Pinned", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddTeamActivity.this, "Team Not Pinned", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearContent() {
        teamName.setText("");
        teamNumber.setText("");
    }

    private void loadIntent() {
        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra(IntentName.SELECTED_COMPETITION);
    }

    private void goToMenuPage() {
        Intent toMenuPage = new Intent(this, OuterMenuActivity.class);
        toMenuPage.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        startActivity(toMenuPage);
    }

    public void onBackPressed() {
        goToMenuPage();
    }

}
