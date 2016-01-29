package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.SaveCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddCompetitionActivity extends AppCompatActivity {

    private String selectedCompetition;

    private EditText date;
    private EditText teamNumber;
    private EditText compName;

    private AdvButton submit;

    private Spinner compType;

    private String[] types;
    private ArrayAdapter<String> typesAdapter;
    private String selectedType;

    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_competition);

        loadIntent();

        date = (EditText) findViewById(R.id.date);
        teamNumber = (EditText) findViewById(R.id.teamnumber);
        compType = (Spinner) findViewById(R.id.type_spinner);
        compName = (EditText) findViewById(R.id.compname);
        submit = new AdvButton((ImageButton) findViewById(R.id.competition), R.drawable.confirm, R.drawable.confirm_down);

        types = new String[]{Competition.QUALIFIER, Competition.MEET, Competition.CHAMPIONSHIP};
        typesAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, types);

        compType.setAdapter(typesAdapter);
        compType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                selectedType = "";
            }
        });

        sdf = new SimpleDateFormat("MM/dd/yyyy");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContent();
                clearContent();
            }
        });

    }

    private void saveContent() {
        if (compName.getText().toString().length() == 0
                || date.getText().toString().length() == 0
                || teamNumber.getText().toString().length() == 0) {
            Toast.makeText(this, "no competition available ", Toast.LENGTH_SHORT).show();
            return;
        }

        Competition competition = new Competition();
        competition.setName(compName.getText().toString());
        competition.setType(selectedType);

        try {
            competition.setDate(sdf.parse(date.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        competition.setHostingTeamID(Integer.parseInt(teamNumber.getText().toString()));

        competition.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    Toast.makeText(AddCompetitionActivity.this, "Competition Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddCompetitionActivity.this, "Competition Not Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        competition.pinInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    Toast.makeText(AddCompetitionActivity.this, "Competition Pinned", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddCompetitionActivity.this, "Competition Not Pinned", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearContent() {
        date.setText("");
        teamNumber.setText("");
        compName.setText("");
        compType.setSelection(0);

        selectedType = "";
    }

    private void loadIntent() {
        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra(IntentName.SELECTED_COMPETITION);
    }

    private void goToMenuPage() {
        Intent toMenu = new Intent(this, OuterMenuActivity.class);
        toMenu.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        startActivity(toMenu);
    }

    @Override
    public void onBackPressed() {
        goToMenuPage();
    }
}
