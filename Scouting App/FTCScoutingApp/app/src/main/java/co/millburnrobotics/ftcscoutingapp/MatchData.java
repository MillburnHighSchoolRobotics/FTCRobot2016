package co.millburnrobotics.ftcscoutingapp;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yanjun on 9/29/2015.
 */

@ParseClassName("MatchData")
public class MatchData extends ParseObject {

    public MatchData() {
        if (parkingPoints.size() == 0 || parkingStrings.size() == 0) {
            parkingPoints.put(FLOOR, 5);
            parkingPoints.put(LOW, 10);
            parkingPoints.put(MID, 20);
            parkingPoints.put(HIGH, 40);
            parkingPoints.put(PULL_UP, 80);

            parkingStrings.put(5, FLOOR);
            parkingStrings.put(10, LOW);
            parkingStrings.put(20, MID);
            parkingStrings.put(40, HIGH);
            parkingStrings.put(80, PULL_UP);
        }
    }

    public void setCompetitionName(String competitionName) {
        put(COMPETITION_NAME, competitionName);
    }

    public String getCompetitionName() {
        return getString(COMPETITION_NAME);
    }

    public void setCompetitionDate(Date competitionDate) {
        put(COMPETITION_DATE, competitionDate);
    }

    public Date getCompetitionDate() {
        return getDate(COMPETITION_DATE);
    }

    public Competition getCompetition() {
        ParseQuery<Competition> query = ParseQuery.getQuery(Competition.class);
        query.whereEqualTo(Competition.NAME, getString(COMPETITION_NAME));
        List<Competition> list = null;

        try {
            list = query.find();
        } catch (ParseException e) {
            return null;
        }
        if (list.size() >= 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public void setTeamNumber(int teamNumber) {
        put(TEAM_NUMBER, teamNumber);
    }

    public int getTeamNumber() {
        return getInt(TEAM_NUMBER);
    }

    public Team getTeam() {
        ParseQuery<Team> query = ParseQuery.getQuery(Team.class);
        query.whereEqualTo(Team.NUMBER, getInt(TEAM_NUMBER));
        List<Team> list = null;

        try {
            list = query.find();
        } catch (ParseException e) {
            return null;
        }
        if (list.size() >= 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public void setMatchNumber(int number) {
        put(MATCH_NUMBER, number);
    }

    public int getMatchNumber() {
        return getInt(MATCH_NUMBER);
    }

    public void setAllianceColor(String color) {
        if (color.equals(BLUE_1) || color.equals(RED_1) || color.equals(BLUE_2) || color.equals(RED_2)) {
            put(ALLIANCE_COLOR, color);
        }
    }

    public String getAllianceColor() {
        return getString(ALLIANCE_COLOR);
    }

    public void setAutoClimberInShelter(int climbers) {
        put(AUTO_CLIMBER_IN_SHELTER, climbers * CLIMBER_IN_SHELTER_MULTIPLIER);
    }

    public int getAutoClimberInShelter() {
        return getInt(AUTO_CLIMBER_IN_SHELTER) / CLIMBER_IN_SHELTER_MULTIPLIER;
    }

    public int getAutoClimberInShelterScore() {
        return getInt(AUTO_CLIMBER_IN_SHELTER);
    }

    public void setAutoBeacon(boolean isPressed) {
        put(AUTO_BEACON, (isPressed ? 1 : 0) * BEACON_MULITPLIER);
    }

    public boolean getAutoBeacon() {
        return getInt(AUTO_BEACON) == BEACON_MULITPLIER;
    }

    public int getAutoBeaconScore() {
        return getInt(AUTO_BEACON);
    }

    public void setAutoParking(String parking) {
        if (parkingPoints.containsKey(parking)) {
            put(AUTO_PARKING, parkingPoints.get(parking));
        }
    }

    public String getAutoParking() {
        return parkingStrings.get(getInt(AUTO_PARKING));
    }

    public int getAutoParkingPoints() {
        return getInt(AUTO_PARKING);
    }

    public void setTeleopClimberInShelter(int climbers) {
        put(TELEOP_CLIMBER_IN_SHELTER, climbers * CLIMBER_IN_SHELTER_MULTIPLIER);
    }

    public int getTeleopClimberInShelter() {
        return getInt(TELEOP_CLIMBER_IN_SHELTER) / CLIMBER_IN_SHELTER_MULTIPLIER;
    }

    public int getTeleopClimberInShelterScore() {
        return getInt(TELEOP_CLIMBER_IN_SHELTER);
    }

    public void setTeleopParking(String parking) {
        if (parkingPoints.containsKey(parking)) {
            put(TELEOP_PARKING, parkingPoints.get(parking));
        }
    }

    public String getTeleopParking() {
        return parkingStrings.get(getInt(TELEOP_PARKING));
    }

    public int getTeleopParkingScore() {
        return getInt(TELEOP_PARKING);
    }

    public void setTeleopClimberZipLine(int climbers) {
        put(TELEOP_CLIMBER_ZIP_LINE, climbers * CLIMBER_ZIP_LINE_MULTIPLIER);
    }

    public int getTeleopClimberZipLine() {
        return getInt(TELEOP_CLIMBER_ZIP_LINE) / CLIMBER_ZIP_LINE_MULTIPLIER;
    }

    public int getTeleopClimberZipLineScore() {
        return getInt(TELEOP_CLIMBER_ZIP_LINE);
    }

    public void setTeleopFloorGoal(int debris) {
        put(TELEOP_FLOOR_GOAL, debris * FLOOR_GOAL_MULTIPLIER);
    }

    public int getTeleopFloorGoal() {
        return getInt(TELEOP_FLOOR_GOAL) / FLOOR_GOAL_MULTIPLIER;
    }

    public int getTeleopFloorGoalScore() {
        return getInt(TELEOP_FLOOR_GOAL);
    }

    public void setTeleopLowGoal(int debris) {
        put(TELEOP_LOW_GOAL, debris * LOW_GOAL_MULTIPLIER);
    }

    public int getTeleopLowGoal() {
        return getInt(TELEOP_LOW_GOAL) / LOW_GOAL_MULTIPLIER;
    }

    public int getTeleopLowGoalScore() {
        return getInt(TELEOP_LOW_GOAL);
    }

    public void setTeleopMidGoal(int debris) {
        put(TELEOP_MID_GOAL, debris * MID_GOAL_MULTIPLIER);
    }

    public int getTeleopMidGoal() {
        return getInt(TELEOP_MID_GOAL) / MID_GOAL_MULTIPLIER;
    }

    public int getTeleopMidGoalScore() {
        return getInt(TELEOP_MID_GOAL);
    }

    public void setTeleopHighGoal(int debris) {
        put(TELEOP_HIGH_GOAL, debris * HIGH_GOAL_MULTIPLIER);
    }

    public int getTeleopHighGoal() {
        return getInt(TELEOP_HIGH_GOAL) / HIGH_GOAL_MULTIPLIER;
    }

    public int getTeleopHighGoalScore() {
        return getInt(TELEOP_HIGH_GOAL);
    }

    public void setTeleopAllClear(boolean isClaimed) {
        put(TELEOP_ALL_CLEAR, (isClaimed ? 1 : 0) * ALL_CLEAR_MULTIPLIER);
    }

    public boolean getTeleopAllClear() {
        return getInt(TELEOP_ALL_CLEAR) == ALL_CLEAR_MULTIPLIER;
    }

    public int getTeleopAllClearScore() {
        return getInt(TELEOP_ALL_CLEAR);
    }

    public int getAutonomousScore () {

    }

    public static final String COMPETITION_NAME = "Competition Name";
    public static final String COMPETITION_DATE = "Competition Date";
    public static final String MATCH_NUMBER = "Match Number";
    public static final String TEAM_NUMBER = "Team Number";
    public static final String ALLIANCE_COLOR = "Alliance Color";

    public static final String AUTO_CLIMBER_IN_SHELTER = "Auto Climber in Shelter";
    public static final String AUTO_BEACON = "Auto Beacon";
    public static final String AUTO_PARKING = "Auto Parking";

    public static final String TELEOP_CLIMBER_IN_SHELTER = "Teleop Climber in Shelter";
    public static final String TELEOP_PARKING = "Teleop Parking";
    public static final String TELEOP_CLIMBER_ZIP_LINE = "Teleop Climber Zip Line";
    public static final String TELEOP_FLOOR_GOAL = "Teleop Floor Goal";
    public static final String TELEOP_LOW_GOAL = "Teleop Low Goal";
    public static final String TELEOP_MID_GOAL = "Teleop Mid Goal";
    public static final String TELEOP_HIGH_GOAL = "Teleop High Goal";
    public static final String TELEOP_ALL_CLEAR = "Teleop All Clear";

    public static final String FLOOR = "Floor";
    public static final String LOW = "Low";
    public static final String MID = "Mid";
    public static final String HIGH = "High";
    public static final String PULL_UP = "Pull Up";

    public static final Map<String, Integer> parkingPoints = new HashMap<String, Integer>();
    public static final Map<Integer, String> parkingStrings = new HashMap<Integer, String>();

    public static final String BLUE_1 = "Blue 1";
    public static final String BLUE_2 = "Blue 2";
    public static final String RED_1 = "Red 1";
    public static final String RED_2 = "Red 2";

    public static final int CLIMBER_IN_SHELTER_MULTIPLIER = 10;
    public static final int BEACON_MULITPLIER = 20;

    public static final int HIGH_GOAL_MULTIPLIER = 15;
    public static final int MID_GOAL_MULTIPLIER = 10;
    public static final int LOW_GOAL_MULTIPLIER = 5;
    public static final int FLOOR_GOAL_MULTIPLIER = 1;

    public static final int ALL_CLEAR_MULTIPLIER = 20;
    public static final int CLIMBER_ZIP_LINE_MULTIPLIER = 20;
}

