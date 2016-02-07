package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

public class AddMatchesAutonomousActivity extends AppCompatActivity {

    private String selectedCompetition;
    private String selectedMatch;
    private boolean doLoad;

    private Competition curComp;
    private Match curMatch;

    private MatchData[] matchDataz;
    private TextView[] teamNumbers;
    private Spinner[] beacons;
    private Spinner[] climbersAutos;
    private Spinner[] parkingAutos;

    private AdvButton back;
    private AdvButton notes;
    private AdvButton next;

    private String[] beaconList;
    private Integer[] climbersAutoList;
    private String[] parkingAutoList;

    private ArrayAdapter<String> beaconAdapter;
    private ArrayAdapter<Integer> climbersAdapter;
    private ArrayAdapter<String> parkingAdapter;

    private String[] beaconSelected;
    private Integer[] climbersSelected;
    private String[] parkingSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_matches_autonomous);

        loadIntent();

        back = new AdvButton((ImageButton) findViewById(R.id.back), R.drawable.back, R.drawable.back_down);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFrontPage();
            }
        });

        ParseQuery<Match> query = ParseQuery.getQuery(Match.class);
        query.fromLocalDatastore();
        try {
            curMatch = query.get(selectedMatch);
        } catch (ParseException e) {
            return;
        }

        ParseRelation<MatchData> matchDatazRel = curMatch.getMatchDataz();

        ParseQuery<MatchData> md1Query = matchDatazRel.getQuery();
        ParseQuery<MatchData> md2Query = matchDatazRel.getQuery();
        ParseQuery<MatchData> md3Query = matchDatazRel.getQuery();
        ParseQuery<MatchData> md4Query = matchDatazRel.getQuery();

        md1Query.whereEqualTo(MatchData.ALLIANCE_COLOR, MatchData.RED_1);
        md2Query.whereEqualTo(MatchData.ALLIANCE_COLOR, MatchData.RED_2);
        md3Query.whereEqualTo(MatchData.ALLIANCE_COLOR, MatchData.BLUE_1);
        md4Query.whereEqualTo(MatchData.ALLIANCE_COLOR, MatchData.BLUE_2);

        md1Query.fromLocalDatastore();
        md2Query.fromLocalDatastore();
        md3Query.fromLocalDatastore();
        md4Query.fromLocalDatastore();

        matchDataz = new MatchData[4];

        try {
            matchDataz[0] = md1Query.find().get(0);
            matchDataz[1] = md2Query.find().get(0);
            matchDataz[2] = md3Query.find().get(0);
            matchDataz[3] = md4Query.find().get(0);
        } catch (ParseException e) {
            return;
        }

        teamNumbers = new TextView[4];

        teamNumbers[0] = (TextView) findViewById(R.id.T1TXT);
        teamNumbers[1] = (TextView) findViewById(R.id.T2TXT);
        teamNumbers[2] = (TextView) findViewById(R.id.T3TXT);
        teamNumbers[3] = (TextView) findViewById(R.id.T4TXT);

        for (int i = 0; i < 4; i++) {
            teamNumbers[i].setText(Integer.toString(matchDataz[i].getTeamNumber()));
        }

        beacons = new Spinner[4];
        climbersAutos = new Spinner[4];
        parkingAutos = new Spinner[4];

        beacons[0] = (Spinner) findViewById(R.id.T1Beacon);
        climbersAutos[0] = (Spinner) findViewById(R.id.T1ClimbersAuto);
        parkingAutos[0] = (Spinner) findViewById(R.id.T1ParkingAuto);

        beacons[1] = (Spinner) findViewById(R.id.T2Beacon);
        climbersAutos[1] = (Spinner) findViewById(R.id.T2ClimbersAuto);
        parkingAutos[1] = (Spinner) findViewById(R.id.T2ParkingAuto);

        beacons[2] = (Spinner) findViewById(R.id.T3Beacon);
        climbersAutos[2] = (Spinner) findViewById(R.id.T3ClimbersAuto);
        parkingAutos[2] = (Spinner) findViewById(R.id.T3ParkingAuto);

        beacons[3] = (Spinner) findViewById(R.id.T4Beacon);
        climbersAutos[3] = (Spinner) findViewById(R.id.T4ClimbersAuto);
        parkingAutos[3] = (Spinner) findViewById(R.id.T4ParkingAuto);

        beaconList = new String[]{"No", "Yes"};
        climbersAutoList = new Integer[]{0, 1, 2};
        parkingAutoList = new String[]{MatchData.NONE, MatchData.FLOOR, MatchData.LOW, MatchData.MID, MatchData.HIGH};

        beaconAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, beaconList);
        climbersAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, climbersAutoList);
        parkingAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, parkingAutoList);

        beaconSelected = new String[4];
        climbersSelected = new Integer[4];
        parkingSelected = new String[4];

        for (int i = 0; i < 4; i++) {
            beacons[i].setAdapter(beaconAdapter);
            beacons[i].setOnItemSelectedListener(new MyOnItemSelectedListener(i) {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    beaconSelected[myID] = (String) parent.getItemAtPosition(position);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    beaconSelected[myID] = null;
                }
            });

            climbersAutos[i].setAdapter(climbersAdapter);
            climbersAutos[i].setOnItemSelectedListener(new MyOnItemSelectedListener(i) {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    climbersSelected[myID] = (Integer) parent.getItemAtPosition(position);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    climbersSelected[myID] = null;
                }
            });

            parkingAutos[i].setAdapter(parkingAdapter);
            parkingAutos[i].setOnItemSelectedListener(new MyOnItemSelectedListener(i) {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    parkingSelected[myID] = (String) parent.getItemAtPosition(position);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    parkingSelected[myID] = null;
                }
            });
        }

        notes = new AdvButton((ImageButton) findViewById(R.id.notes), R.drawable.notes, R.drawable.notes_down);
        notes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveContent();
                goToNotes();
            }
        });

        next = new AdvButton((ImageButton) findViewById(R.id.next), R.drawable.next, R.drawable.next_down);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContent();
                goToTeleop();
            }
        });

        if (doLoad) {
            loadContent();
        }
    }

    private void goToFrontPage() {
        Intent toFront = new Intent(this, AddMatchesFrontActivity.class);
        toFront.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        startActivity(toFront);
    }

    private void loadIntent() {
        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra(IntentName.SELECTED_COMPETITION);
        selectedMatch = incoming.getStringExtra(IntentName.SELECTED_MATCH);
        doLoad = incoming.getBooleanExtra(IntentName.DO_LOAD, false);
    }

    private void saveContent() {
        for (int i = 0; i < 4; i++) {
            if (beaconSelected[i].equals("Yes")) {
                matchDataz[i].setAutoBeacon(true);
            } else {
                matchDataz[i].setAutoBeacon(false);
            }

            matchDataz[i].setAutoClimberInShelter(climbersSelected[i]);
            matchDataz[i].setAutoParking(parkingSelected[i]);

            try {
                matchDataz[i].save();
            } catch (ParseException e) {
                return;
            }
        }
    }

    private void goToNotes() {
        Intent toNotes = new Intent(this, NotesActivity.class);
        toNotes.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        toNotes.putExtra(IntentName.SELECTED_MATCH, selectedMatch);
        toNotes.putExtra(IntentName.DO_LOAD, doLoad);
        toNotes.putExtra(IntentName.SENDER, IntentName.AUTO);
        startActivity(toNotes);
    }

    private void goToTeleop() {
        Intent toTeleop = new Intent(this, AddMatchesTeleop.class);
        toTeleop.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        toTeleop.putExtra(IntentName.SELECTED_MATCH, selectedMatch);
        toTeleop.putExtra(IntentName.DO_LOAD, doLoad);
        startActivity(toTeleop);
    }

    private void loadContent() {
        for (int i = 0; i < 4; i++) {
            beacons[i].setSelection(beaconAdapter.getPosition(matchDataz[i].getAutoBeacon() ? "Yes" : "No"));
            climbersAutos[i].setSelection(climbersAdapter.getPosition(matchDataz[i].getAutoClimberInShelter()));
            parkingAutos[i].setSelection(parkingAdapter.getPosition(matchDataz[i].getAutoParking()));

            beaconSelected[i] = matchDataz[i].getAutoBeacon() ? "Yes" : "No";
            climbersSelected[i] = matchDataz[i].getAutoClimberInShelter();
            parkingSelected[i] = matchDataz[i].getAutoParking();
        }
    }

    public void onBackPressed() {
        goToFrontPage();
    }

}
