package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    String selectedCompetition;
    String selectedMatch;
    boolean doLoad;
    String sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra("SelectedCompetition");
        selectedMatch = incoming.getStringExtra("SelectedMatch");
        doLoad = incoming.getBooleanExtra(IntentName.DO_LOAD, false);
        sender = incoming.getStringExtra(IntentName.SENDER);

        ParseQuery<Competition> compQuery = ParseQuery.getQuery(Competition.class);
        compQuery.fromLocalDatastore();
        Competition curComp = null;
        try {
            curComp = compQuery.get(selectedCompetition);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ParseQuery<Match> matchQuery = curComp.getMatches().getQuery();
        matchQuery.fromLocalDatastore();
        Match curMatch = null;
        try {
            curMatch = matchQuery.get(selectedMatch);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ParseQuery<MatchData> mdQuery = curMatch.getMatchDataz().getQuery();
        List<MatchData> dataz = null;
        try {
            dataz = mdQuery.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final TextView red1Number = (TextView) findViewById(R.id.red1_number);
        final TextView red2Number = (TextView) findViewById(R.id.red2_number);
        final TextView blue1Number = (TextView) findViewById(R.id.blue1_number);
        final TextView blue2Number = (TextView) findViewById(R.id.blue2_number);

        final EditText red1Notes = (EditText) findViewById(R.id.red1_notes);
        final EditText red2Notes = (EditText) findViewById(R.id.red2_notes);
        final EditText blue1Notes = (EditText) findViewById(R.id.blue1_notes);
        final EditText blue2Notes = (EditText) findViewById(R.id.blue2_notes);

        final AdvButton saveButton = new AdvButton((ImageButton) findViewById(R.id.button_save), R.drawable.done, R.drawable.done_down);

        Collections.sort(dataz, new Comparator<MatchData>() {
            public int compare(MatchData m1, MatchData m2) {
                return m1.getAllianceColor().compareTo(m2.getAllianceColor());
            }
        });

        blue1Number.setText(Integer.toString(dataz.get(0).getTeamNumber()));
        blue2Number.setText(Integer.toString(dataz.get(1).getTeamNumber()));
        red1Number.setText(Integer.toString(dataz.get(2).getTeamNumber()));
        red2Number.setText(Integer.toString(dataz.get(3).getTeamNumber()));

        blue1Notes.setText(dataz.get(0).getNotes());
        blue2Notes.setText(dataz.get(1).getNotes());
        red1Notes.setText(dataz.get(2).getNotes());
        red2Notes.setText(dataz.get(3).getNotes());

        final List<MatchData> finalDataz = dataz;
        final Match finalCurMatch = curMatch;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalDataz.get(0).setNotes(blue1Notes.getText().toString());
                finalDataz.get(1).setNotes(blue2Notes.getText().toString());
                finalDataz.get(2).setNotes(red1Notes.getText().toString());
                finalDataz.get(3).setNotes(red2Notes.getText().toString());

                for (MatchData md : finalDataz) {
                    try {
                        md.save();
                        md.pin();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                goBack();
            }
        });
    }

    private void goBack() {
        Intent outgoing = null;
        if (sender.equals(IntentName.AUTO)) {
            outgoing = new Intent(this, AddMatchesAutonomousActivity.class);
        } else if (sender.equals(IntentName.TELEOP)) {
            outgoing = new Intent(this, AddMatchesTeleop.class);
        }

        outgoing.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        outgoing.putExtra(IntentName.SELECTED_MATCH, selectedMatch);
        outgoing.putExtra(IntentName.DO_LOAD, true);

        startActivity(outgoing);
    }

    public void onBackPressed() {
        goBack();
    }
}
