package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

public class AddTeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //INSERT PARSE INIT STUFF HERE

        final Button switchact = (Button) findViewById(R.id.toMenuPage);
        switchact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMenu = new Intent(view.getContext(), MenuActivity.class);
                startActivity(toMenu);
            }
        });

        final EditText teamNumber = (EditText) findViewById(R.id.TeamEnterEditText);
        final EditText teamName = (EditText) findViewById(R.id.teamname);

        final Button submitButton = (Button) findViewById(R.id.EnterTeamButton);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                String szTeamNumber = teamName.getText().toString();
                int nTeamNumber = Integer.parseInt(teamNumber.getText().toString());

                Team team = new Team();

                team.setName(szTeamNumber);
                team.setNumber(nTeamNumber);

                team.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(v.getContext(), "Team Saved on Parse", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), "Team Not Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                teamName.setText("");
                teamNumber.setText("");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        Intent toMenu = new Intent(this, MenuActivity.class);
        startActivity(toMenu);
    }

}
