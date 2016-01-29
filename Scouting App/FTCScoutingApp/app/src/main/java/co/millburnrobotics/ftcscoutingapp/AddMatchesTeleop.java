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
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

public class AddMatchesTeleop extends AppCompatActivity {

    private String selectedCompetition;
    private String selectedMatch;
    private boolean doLoad;

    private Match curMatch;
    private MatchData[] matchDataz;

    private EditText[] floorGoals;
    private EditText[] lowGoals;
    private EditText[] midGoals;
    private EditText[] highGoals;

    private Spinner[] climbers;
    private Spinner[] parkings;
    private Spinner[] zipLines;
    private Spinner[] allClears;

    private TextView[] teamNumbers;

    private Integer[] climbersList;
    private Integer[] zipLinesList;
    private String[] parkingsList;
    private String[] allClearsList;

    private ArrayAdapter<Integer> climbersAdapter;
    private ArrayAdapter<Integer> zipLinesAdapter;
    private ArrayAdapter<String> parkingsAdapter;
    private ArrayAdapter<String> allClearsAdapter;

    private Integer[] climbersSelected;
    private Integer[] zipLinesSelected;
    private String[] parkingsSelected;
    private String[] allClearsSelected;

    private Button back;
    private Button notes;
    private Button next;

    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matches_teleop);

        loadIntent();

        ParseQuery<Match> matchQuery = ParseQuery.getQuery(Match.class);
        matchQuery.fromLocalDatastore();
        try {
            curMatch = matchQuery.get(selectedMatch);
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

        for (int i = 0; i < teamNumbers.length; i++) {
            teamNumbers[i].setText(Integer.toString(matchDataz[i].getTeamNumber()));
        }

        floorGoals = new EditText[4];
        lowGoals = new EditText[4];
        midGoals = new EditText[4];
        highGoals = new EditText[4];

        climbers = new Spinner[4];
        zipLines = new Spinner[4];
        parkings = new Spinner[4];
        allClears = new Spinner[4];

        floorGoals[0] = (EditText) findViewById(R.id.T1Floor);
        floorGoals[1] = (EditText) findViewById(R.id.T2Floor);
        floorGoals[2] = (EditText) findViewById(R.id.T3Floor);
        floorGoals[3] = (EditText) findViewById(R.id.T4Floor);

        lowGoals[0] = (EditText) findViewById(R.id.T1Low);
        lowGoals[1] = (EditText) findViewById(R.id.T2Low);
        lowGoals[2] = (EditText) findViewById(R.id.T3Low);
        lowGoals[3] = (EditText) findViewById(R.id.T4Low);

        midGoals[0] = (EditText) findViewById(R.id.T1Mid);
        midGoals[1] = (EditText) findViewById(R.id.T2Mid);
        midGoals[2] = (EditText) findViewById(R.id.T3Mid);
        midGoals[3] = (EditText) findViewById(R.id.T4Mid);

        highGoals[0] = (EditText) findViewById(R.id.T1High);
        highGoals[1] = (EditText) findViewById(R.id.T2High);
        highGoals[2] = (EditText) findViewById(R.id.T3High);
        highGoals[3] = (EditText) findViewById(R.id.T4High);

        climbers[0] = (Spinner) findViewById(R.id.T1ClimbersTeleOp);
        climbers[1] = (Spinner) findViewById(R.id.T2ClimbersTeleOp);
        climbers[2] = (Spinner) findViewById(R.id.T3ClimbersTeleOp);
        climbers[3] = (Spinner) findViewById(R.id.T4ClimbersTeleOp);

        zipLines[0] = (Spinner) findViewById(R.id.T1ZipLine);
        zipLines[1] = (Spinner) findViewById(R.id.T2ZipLine);
        zipLines[2] = (Spinner) findViewById(R.id.T3ZipLine);
        zipLines[3] = (Spinner) findViewById(R.id.T4ZipLine);

        parkings[0] = (Spinner) findViewById(R.id.T1ParkingEndgame);
        parkings[1] = (Spinner) findViewById(R.id.T2ParkingEndgame);
        parkings[2] = (Spinner) findViewById(R.id.T3ParkingEndgame);
        parkings[3] = (Spinner) findViewById(R.id.T4ParkingEndgame);

        allClears[0] = (Spinner) findViewById(R.id.T1AllClear);
        allClears[1] = (Spinner) findViewById(R.id.T2AllClear);
        allClears[2] = (Spinner) findViewById(R.id.T3AllClear);
        allClears[3] = (Spinner) findViewById(R.id.T4AllClear);

        climbersList = new Integer[]{0, 1, 2};
        zipLinesList = new Integer[]{0, 1, 2, 3};
        parkingsList = new String[]{MatchData.NONE, MatchData.FLOOR, MatchData.LOW, MatchData.MID, MatchData.HIGH, MatchData.PULL_UP};
        allClearsList = new String[]{"No", "Yes"};

        climbersAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, climbersList);
        zipLinesAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, zipLinesList);
        parkingsAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, parkingsList);
        allClearsAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, allClearsList);

        climbersSelected = new Integer[4];
        zipLinesSelected = new Integer[4];
        parkingsSelected = new String[4];
        allClearsSelected = new String[4];

        for (int i = 0; i < 4; i++) {
            climbers[i].setAdapter(climbersAdapter);
            zipLines[i].setAdapter(zipLinesAdapter);
            parkings[i].setAdapter(parkingsAdapter);
            allClears[i].setAdapter(allClearsAdapter);

            climbers[i].setOnItemSelectedListener(new MyOnItemSelectedListener(i) {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    climbersSelected[myID] = (Integer) parent.getItemAtPosition(position);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    climbersSelected[myID] = null;
                }
            });

            zipLines[i].setOnItemSelectedListener(new MyOnItemSelectedListener(i) {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    zipLinesSelected[myID] = (Integer) parent.getItemAtPosition(position);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    zipLinesSelected[myID] = null;
                }
            });

            parkings[i].setOnItemSelectedListener(new MyOnItemSelectedListener(i) {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    parkingsSelected[myID] = (String) parent.getItemAtPosition(position);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    parkingsSelected[myID] = null;
                }
            });

            allClears[i].setOnItemSelectedListener(new MyOnItemSelectedListener(i) {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    allClearsSelected[myID] = (String) parent.getItemAtPosition(position);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    allClearsSelected[myID] = null;
                }
            });
        }

        back = (Button) findViewById(R.id.TeleOpBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContent();
                goToAutonomous();
            }
        });

        notes = (Button) findViewById(R.id.TeleOpNotes);
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContent();
                goToNotes();
            }
        });

        next = (Button) findViewById(R.id.TeleOpNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContent();
                //unpinContent();
                goToFrontPage();
            }
        });

        if (doLoad) {
            loadContent();
        }

    }

    private void loadContent() {
        for (int i = 0; i < 4; i++) {
            floorGoals[i].setText(Integer.toString(matchDataz[i].getTeleopFloorGoal()));
            lowGoals[i].setText(Integer.toString(matchDataz[i].getTeleopLowGoal()));
            midGoals[i].setText(Integer.toString(matchDataz[i].getTeleopMidGoal()));
            highGoals[i].setText(Integer.toString(matchDataz[i].getTeleopHighGoal()));

            if (matchDataz[i].getTeleopFloorGoal() == 0) {
                floorGoals[i].setText("");
            }

            if (matchDataz[i].getTeleopLowGoal() == 0) {
                lowGoals[i].setText("");
            }

            if (matchDataz[i].getTeleopMidGoal() == 0) {
                midGoals[i].setText("");
            }

            if (matchDataz[i].getTeleopHighGoal() == 0) {
                highGoals[i].setText("");
            }

            climbers[i].setSelection(climbersAdapter.getPosition(matchDataz[i].getTeleopClimberInShelter()));
            zipLines[i].setSelection(zipLinesAdapter.getPosition(matchDataz[i].getTeleopClimberZipLine()));
            parkings[i].setSelection(parkingsAdapter.getPosition(matchDataz[i].getTeleopParking()));
            allClears[i].setSelection(allClearsAdapter.getPosition(matchDataz[i].getTeleopAllClear() ? "Yes" : "No"));

            climbersSelected[i] = matchDataz[i].getTeleopClimberInShelter();
            zipLinesSelected[i] = matchDataz[i].getTeleopClimberZipLine();
            parkingsSelected[i] = matchDataz[i].getTeleopParking();
            allClearsSelected[i] = matchDataz[i].getTeleopAllClear() ? "Yes" : "No";
        }
    }

    private void unpinContent() {
        for (int i = 0; i < 4; i++) {
            try {
                matchDataz[i].unpin();
            } catch (ParseException e) {
                return;
            }
        }

        try {
            curMatch.unpin();
        } catch (ParseException e) {
            return;
        }

    }

    private void saveContent() {

        for (int i = 0; i < 4; i++) {
            if (floorGoals[i] != null && floorGoals[i].length() == 0) {
                floorGoals[i].setText("0");
            }

            if (lowGoals[i] != null && lowGoals[i].length() == 0) {
                lowGoals[i].setText("0");
            }

            if (midGoals[i] != null && midGoals[i].length() == 0) {
                midGoals[i].setText("0");
            }

            if (highGoals[i] != null && highGoals[i].length() == 0) {
                highGoals[i].setText("0");
            }

            matchDataz[i].setTeleopFloorGoal(Integer.parseInt(floorGoals[i].getText().toString()));
            matchDataz[i].setTeleopLowGoal(Integer.parseInt(lowGoals[i].getText().toString()));
            matchDataz[i].setTeleopMidGoal(Integer.parseInt(midGoals[i].getText().toString()));
            matchDataz[i].setTeleopHighGoal(Integer.parseInt(highGoals[i].getText().toString()));

            matchDataz[i].setTeleopClimberInShelter(climbersSelected[i]);
            matchDataz[i].setTeleopClimberZipLine(zipLinesSelected[i]);
            matchDataz[i].setTeleopParking(parkingsSelected[i]);

            if (allClearsSelected[i].equals("Yes")) {
                matchDataz[i].setTeleopAllClear(true);
            } else {
                matchDataz[i].setTeleopAllClear(false);
            }

            try {
                matchDataz[i].save();
            } catch (ParseException e) {
                return;
            }
        }
    }

    private void loadIntent() {
        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra(IntentName.SELECTED_COMPETITION);
        selectedMatch = incoming.getStringExtra(IntentName.SELECTED_MATCH);
        doLoad = incoming.getBooleanExtra(IntentName.DO_LOAD, false);
    }

    private void goToAutonomous() {
        Intent toAutonomous = new Intent(this, AddMatchesFrontActivity.class);
        toAutonomous.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        toAutonomous.putExtra(IntentName.SELECTED_MATCH, selectedMatch);
        toAutonomous.putExtra(IntentName.DO_LOAD, doLoad);
        startActivity(toAutonomous);
    }

    private void goToNotes() {
        Intent toNotes = new Intent(this, NotesActivity.class);
        toNotes.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        toNotes.putExtra(IntentName.SELECTED_MATCH, selectedMatch);
        toNotes.putExtra(IntentName.DO_LOAD, doLoad);
        startActivity(toNotes);
    }

    private void goToFrontPage() {
        Intent toFront = new Intent(this, AddMatchesFrontActivity.class);
        toFront.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        startActivity(toFront);
    }

}
