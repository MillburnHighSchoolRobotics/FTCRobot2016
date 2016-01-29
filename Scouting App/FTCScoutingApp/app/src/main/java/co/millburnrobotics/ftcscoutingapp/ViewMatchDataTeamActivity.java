package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class ViewMatchDataTeamActivity extends AppCompatActivity {
    private LinearLayout matchDatazLayout;
    private TextView title;

    private String selectedCompetition;
    private int teamNumber;

    private Competition curComp;
    private List<MatchData> matchDataz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match_data_team);

        loadIntent();

        title = (TextView) findViewById(R.id.title);
        title.setText("Showing all matches for Team " + teamNumber);

        ParseQuery<Competition> compQuery = ParseQuery.getQuery(Competition.class);
        compQuery.fromLocalDatastore();
        try {
            curComp = compQuery.get(selectedCompetition);
        } catch (ParseException e) {
            Toast.makeText(this, "cannot find competition", Toast.LENGTH_SHORT).show();
            return;
        }

        ParseQuery<MatchData> mdQuery = ParseQuery.getQuery(MatchData.class);
        mdQuery.whereEqualTo(MatchData.TEAM_NUMBER, teamNumber);
        mdQuery.whereEqualTo(MatchData.COMPETITION_NAME, curComp.getName());
        mdQuery.whereEqualTo(MatchData.COMPETITION_DATE, curComp.getDate());
        mdQuery.orderByAscending(MatchData.ALLIANCE_COLOR);

        try {
            matchDataz = mdQuery.find();
        } catch (ParseException e) {
            return;
        }

        matchDatazLayout = (LinearLayout) findViewById(R.id.listOfMatches);

        for (int i = 0; i < matchDataz.size(); i++) {
            TableLayout toAdd = (TableLayout) LayoutInflater.from(this).inflate(R.layout.matchdata_layout, matchDatazLayout, false);
            MatchData md = matchDataz.get(i);
            TextView nTeam = (TextView) toAdd.findViewById(R.id.team_number);
            TextView nMatch = (TextView) toAdd.findViewById(R.id.match_number);

            nTeam.setText(Integer.toString(md.getTeamNumber()));
            nMatch.setText("Match " + Integer.toString(md.getMatchNumber()));

            if (md.getAllianceColor().equals(MatchData.RED_1) || md.getAllianceColor().equals(MatchData.RED_2)) {
                nTeam.setBackgroundResource(R.color.light_red);
                nMatch.setBackgroundResource(R.color.light_red);
            } else {
                nTeam.setBackgroundResource(R.color.light_blue);
                nMatch.setBackgroundResource(R.color.light_blue);
            }

            TextView nAutoClimber = (TextView) toAdd.findViewById(R.id.AutoClimber);
            TextView szAutoParking = (TextView) toAdd.findViewById(R.id.AutoParking);
            TextView bAutoBeacon = (TextView) toAdd.findViewById(R.id.AutoBeacon);

            TextView nTeleopClimber = (TextView) toAdd.findViewById(R.id.teleOpClimbers);
            TextView szTeleopParking = (TextView) toAdd.findViewById(R.id.teleOpParking);
            TextView nZipLine = (TextView) toAdd.findViewById(R.id.teleOpZipLine);
            TextView nFloorGoal = (TextView) toAdd.findViewById(R.id.teleOpFloorGoal);
            TextView nLowGoal = (TextView) toAdd.findViewById(R.id.teleOpLowGoal);
            TextView nMidGoal = (TextView) toAdd.findViewById(R.id.teleOpMidGoal);
            TextView nHighGoal = (TextView) toAdd.findViewById(R.id.teleOpHighGoal);
            TextView bAllClear = (TextView) toAdd.findViewById(R.id.teleOpAllClear);

            TextView szNotes = (TextView) toAdd.findViewById(R.id.notes);

            nAutoClimber.setText(coupleData(Integer.toString(md.getAutoClimberInShelter()), md.getAutoClimberInShelterScore()));
            bAutoBeacon.setText(coupleData((md.getAutoBeacon() ? "Yes" : "No"), md.getAutoBeaconScore()));
            szAutoParking.setText(coupleData(md.getAutoParking(), md.getAutoParkingPoints()));

            nTeleopClimber.setText(coupleData(Integer.toString(md.getTeleopClimberInShelter()), md.getTeleopClimberInShelterScore()));
            szTeleopParking.setText(coupleData(md.getTeleopParking(), md.getTeleopParkingScore()));
            nZipLine.setText(coupleData(Integer.toString(md.getTeleopClimberZipLine()), md.getTeleopClimberZipLineScore()));
            nFloorGoal.setText(coupleData(Integer.toString(md.getTeleopFloorGoal()), md.getTeleopFloorGoalScore()));
            nLowGoal.setText(coupleData(Integer.toString(md.getTeleopLowGoal()), md.getTeleopLowGoalScore()));
            nMidGoal.setText(coupleData(Integer.toString(md.getTeleopMidGoal()), md.getTeleopMidGoalScore()));
            nHighGoal.setText(coupleData(Integer.toString(md.getTeleopHighGoal()), md.getTeleopHighGoalScore()));
            bAllClear.setText(coupleData((md.getTeleopAllClear() ? "Yes" : "No"), md.getTeleopAllClearScore()));
            szNotes.setText(md.getNotes());

            matchDatazLayout.addView(toAdd);
        }
    }

    private void loadIntent() {
        Intent incoming = getIntent();
        teamNumber = incoming.getIntExtra(IntentName.TEAM_NUMBER, -1);
        selectedCompetition = incoming.getStringExtra(IntentName.SELECTED_COMPETITION);
    }

    private void goToViewMatchData() {
        Intent toViewMatchData = new Intent(this, ViewMatchDataActivity.class);
        toViewMatchData.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        startActivity(toViewMatchData);
    }

    public void onBackPressed() {
        goToViewMatchData();
    }

    private String coupleData(String str1, int str2) {
        return str1 + " (" + Integer.toString(str2) + ")";
    }

}
