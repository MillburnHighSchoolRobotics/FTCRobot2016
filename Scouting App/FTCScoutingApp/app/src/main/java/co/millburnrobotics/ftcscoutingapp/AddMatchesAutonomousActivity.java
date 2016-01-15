package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.List;

public class AddMatchesAutonomousActivity extends AppCompatActivity {

    String selectedCompetition = "";
    String selectedMatch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matches_autonomous);

        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra("SelectedCompetition");
        selectedMatch = incoming.getStringExtra("SelectedMatch");
        //INSERT PARSE INIT STUFF HERE

        final Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ParseQuery<Match> query = ParseQuery.getQuery(Match.class);
        Match match = null;
        try {
            match = query.get(selectedMatch);
        } catch (ParseException e) {
            return;
        }

        ParseRelation<MatchData> matchDataz = match.getMatchDataz();

        ParseQuery<MatchData> md1Query = matchDataz.getQuery();
        ParseQuery<MatchData> md2Query = matchDataz.getQuery();
        ParseQuery<MatchData> md3Query = matchDataz.getQuery();
        ParseQuery<MatchData> md4Query = matchDataz.getQuery();

        md1Query.whereEqualTo(MatchData.ALLIANCE_COLOR, MatchData.RED_1);
        md2Query.whereEqualTo(MatchData.ALLIANCE_COLOR, MatchData.RED_2);
        md3Query.whereEqualTo(MatchData.ALLIANCE_COLOR, MatchData.BLUE_1);
        md4Query.whereEqualTo(MatchData.ALLIANCE_COLOR, MatchData.BLUE_2);

        final MatchData md1, md2, md3, md4;

        try {
            md1 = md1Query.find().get(0);
            md2 = md2Query.find().get(0);
            md3 = md3Query.find().get(0);
            md4 = md4Query.find().get(0);
        } catch (ParseException e) {
            return;
        }

        final TextView team1Number = (TextView) findViewById(R.id.T1TXT);
        final TextView team2Number = (TextView) findViewById(R.id.T2TXT);
        final TextView team3Number = (TextView) findViewById(R.id.T3TXT);
        final TextView team4Number = (TextView) findViewById(R.id.T4TXT);

        team1Number.setText(Integer.toString(md1.getTeamNumber()));
        team2Number.setText(Integer.toString(md2.getTeamNumber()));
        team3Number.setText(Integer.toString(md3.getTeamNumber()));
        team4Number.setText(Integer.toString(md4.getTeamNumber()));

        final Spinner team1Beacon = (Spinner) findViewById(R.id.T1Beacon);
        final Spinner team1ClimbersAuto = (Spinner) findViewById(R.id.T1ClimbersAuto);
        final Spinner team1ParkingAuto = (Spinner) findViewById(R.id.T1ParkingAuto);

        final Spinner team2Beacon = (Spinner) findViewById(R.id.T2Beacon);
        final Spinner team2ClimbersAuto = (Spinner) findViewById(R.id.T2ClimbersAuto);
        final Spinner team2ParkingAuto = (Spinner) findViewById(R.id.T2ParkingAuto);

        final Spinner team3Beacon = (Spinner) findViewById(R.id.T3Beacon);
        final Spinner team3ClimbersAuto = (Spinner) findViewById(R.id.T3ClimbersAuto);
        final Spinner team3ParkingAuto = (Spinner) findViewById(R.id.T3ParkingAuto);

        final Spinner team4Beacon = (Spinner) findViewById(R.id.T4Beacon);
        final Spinner team4ClimbersAuto = (Spinner) findViewById(R.id.T4ClimbersAuto);
        final Spinner team4ParkingAuto = (Spinner) findViewById(R.id.T4ParkingAuto);

        List<String> beaconList = new ArrayList<String>();
        List<String> climbersList = new ArrayList<String>();
        List<String> parkingList = new ArrayList<String>();

        beaconList.add("No");
        beaconList.add("Yes");


        climbersList.add("0");
        climbersList.add("1");
        climbersList.add("2");

        parkingList.add(MatchData.NONE);
        parkingList.add(MatchData.FLOOR);
        parkingList.add(MatchData.LOW);
        parkingList.add(MatchData.MID);
        parkingList.add(MatchData.HIGH);

        ArrayAdapter<String> beaconAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, beaconList);
        ArrayAdapter<String> climbersAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, climbersList);
        ArrayAdapter<String> parkingAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, parkingList);

        team1Beacon.setAdapter(beaconAdapter);
        team2Beacon.setAdapter(beaconAdapter);
        team3Beacon.setAdapter(beaconAdapter);
        team4Beacon.setAdapter(beaconAdapter);

        team1ClimbersAuto.setAdapter(climbersAdapter);
        team2ClimbersAuto.setAdapter(climbersAdapter);
        team3ClimbersAuto.setAdapter(climbersAdapter);
        team4ClimbersAuto.setAdapter(climbersAdapter);

        team1ParkingAuto.setAdapter(parkingAdapter);
        team2ParkingAuto.setAdapter(parkingAdapter);
        team3ParkingAuto.setAdapter(parkingAdapter);
        team4ParkingAuto.setAdapter(parkingAdapter);

        final String[] beaconSelected = {"", "", "", ""};
        final String[] climbersSelected = {"", "", "", ""};
        final String[] parkingSelected = {"", "", "", ""};

        team1Beacon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                beaconSelected[0] = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent){
                beaconSelected[0]= "";
            }
        });

        team2Beacon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                beaconSelected[1] = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent){
                beaconSelected[1]= "";
            }
        });

        team3Beacon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                beaconSelected[2] = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent){
                beaconSelected[2] = "";
            }
        });

        team4Beacon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                beaconSelected[3] = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                beaconSelected[3] = "";
            }
        });

        team1ClimbersAuto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                climbersSelected[0] = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                climbersSelected[0] = "";
            }
        });

        team2ClimbersAuto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                climbersSelected[1] = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                climbersSelected[1] = "";
            }
        });

        team3ClimbersAuto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                climbersSelected[2] = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                climbersSelected[2] = "";
            }
        });

        team4ClimbersAuto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                climbersSelected[3] = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                climbersSelected[3] = "";
            }
        });

        team1ParkingAuto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parkingSelected[0] = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                parkingSelected[0] = "";
            }
        });

        team2ParkingAuto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parkingSelected[1] = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                parkingSelected[1] = "";
            }
        });

        team3ParkingAuto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parkingSelected[2] = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                parkingSelected[2] = "";
            }
        });

        team4ParkingAuto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parkingSelected[3] = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                parkingSelected[3] = "";
            }
        });

        final Runnable saving = new Runnable() {
            public void run() {
                Log.e("qqq", "test1");
                if (beaconSelected[0].equals("Yes")) {
                    md1.setAutoBeacon(true);
                } else {
                    md1.setAutoBeacon(false);
                }

                if (beaconSelected[1].equals("Yes")) {
                    md2.setAutoBeacon(true);
                } else {
                    md2.setAutoBeacon(false);
                }

                if (beaconSelected[2].equals("Yes")) {
                    md3.setAutoBeacon(true);
                } else {
                    md3.setAutoBeacon(false);
                }

                if (beaconSelected[3].equals("Yes")) {
                    md4.setAutoBeacon(true);
                } else {
                    md4.setAutoBeacon(false);
                }

                md1.setAutoClimberInShelter(Integer.parseInt(climbersSelected[0]));
                md2.setAutoClimberInShelter(Integer.parseInt(climbersSelected[1]));
                md3.setAutoClimberInShelter(Integer.parseInt(climbersSelected[2]));
                md4.setAutoClimberInShelter(Integer.parseInt(climbersSelected[3]));

                md1.setAutoParking(parkingSelected[0]);
                md2.setAutoParking(parkingSelected[1]);
                md3.setAutoParking(parkingSelected[2]);
                md4.setAutoParking(parkingSelected[3]);
                Log.e("qqq", "saving");
                try {
                    md1.save();
                    md2.save();
                    md3.save();
                    md4.save();
                } catch (ParseException e) {
                    Log.e("qqq", e.toString());
                    return;
                }

                Log.e("qqq", "we here" );
            }
        };

        final Button notes = (Button) findViewById(R.id.notes);
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saving.run();

                Intent toNotes = new Intent(v.getContext(), NotesActivity.class);
                toNotes.putExtra("SelectedCompetition", selectedCompetition);
                toNotes.putExtra("SelectedMatch", selectedMatch);
                startActivity(toNotes);
            }
        });

        final Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saving.run();

                Intent toTeleop = new Intent(v.getContext(), AddMatchesTeleop.class);
                toTeleop.putExtra("SelectedCompetition", selectedCompetition);
                toTeleop.putExtra("SelectedMatch", selectedMatch);
                startActivity(toTeleop);
            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        Intent toFront = new Intent(this, AddMatchesFrontActivity.class);
        toFront.putExtra("SelectedCompetition", selectedCompetition);
        startActivity(toFront);
    }

}
