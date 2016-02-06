package co.millburnrobotics.ftcscoutingapp;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Yanjun on 1/31/2016.
 */
public class Analysis {

    public static MatchData getAverageResults(Competition comp, Team team) {
        MatchData teamAvg = new MatchData();

        teamAvg.setTeamNumber(team.getNumber());

        ParseQuery<MatchData> teamQuery = ParseQuery.getQuery(MatchData.class);

        teamQuery.whereEqualTo(MatchData.COMPETITION_NAME, comp.getName());
        teamQuery.whereEqualTo(MatchData.COMPETITION_DATE, comp.getDate());
        teamQuery.whereEqualTo(MatchData.TEAM_NUMBER, team.getNumber());

        List<MatchData> teamData = null;

        try {
            teamData = teamQuery.find();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        for (int i = 0; i < teamData.size(); i++) {
            teamAvg.setAutoClimberInShelter(teamAvg.getAutoClimberInShelter() + teamData.get(i).getAutoClimberInShelter());
            teamAvg.put(MatchData.AUTO_PARKING, teamAvg.getAutoParkingPoints() + teamData.get(i).getAutoParkingPoints());
            teamAvg.put(MatchData.AUTO_BEACON, teamAvg.getAutoBeaconScore() + teamData.get(i).getAutoBeaconScore());
            teamAvg.setTeleopFloorGoal(teamAvg.getTeleopFloorGoal() + teamData.get(i).getTeleopFloorGoal());
            teamAvg.setTeleopLowGoal(teamAvg.getTeleopLowGoal() + teamData.get(i).getTeleopLowGoal());
            teamAvg.setTeleopMidGoal(teamAvg.getTeleopMidGoal() + teamData.get(i).getTeleopMidGoal());
            teamAvg.setTeleopHighGoal(teamAvg.getTeleopHighGoal() + teamData.get(i).getTeleopHighGoal());
            teamAvg.setTeleopClimberZipLine(teamAvg.getTeleopClimberZipLine() + teamData.get(i).getTeleopClimberZipLine());
            teamAvg.setTeleopClimberInShelter(teamAvg.getTeleopClimberInShelter() + teamData.get(i).getTeleopClimberInShelter());
            teamAvg.put(MatchData.TELEOP_ALL_CLEAR, teamAvg.getTeleopAllClearScore() + teamData.get(i).getTeleopAllClearScore());
            teamAvg.put(MatchData.TELEOP_PARKING, teamAvg.getTeleopParkingScore() + teamData.get(i).getTeleopParkingScore());
        }

        teamAvg.setAutoClimberInShelter((int) Math.round((double) teamAvg.getAutoClimberInShelter() / teamData.size()));
        teamAvg.put(MatchData.AUTO_PARKING, closestNeighbor((int) Math.round((double) teamAvg.getAutoParkingPoints() / teamData.size()), new int[]{0, 5, 10, 20, 40}));
        teamAvg.put(MatchData.AUTO_BEACON, closestNeighbor((int) Math.round((double) teamAvg.getAutoBeaconScore() / teamData.size()), new int[]{0, 20}));
        teamAvg.setTeleopFloorGoal((int) Math.round((double) teamAvg.getTeleopFloorGoal() / teamData.size()));
        teamAvg.setTeleopLowGoal((int) Math.round((double) teamAvg.getTeleopLowGoal() / teamData.size()));
        teamAvg.setTeleopMidGoal((int) Math.round((double) teamAvg.getTeleopMidGoal() / teamData.size()));
        teamAvg.setTeleopHighGoal((int) Math.round((double) teamAvg.getTeleopHighGoal() / teamData.size()));
        teamAvg.setTeleopClimberZipLine((int) Math.round((double) teamAvg.getTeleopClimberZipLine() / teamData.size()));
        teamAvg.setTeleopClimberInShelter((int) Math.round((double) teamAvg.getTeleopClimberInShelter() / teamData.size()));
        teamAvg.put(MatchData.TELEOP_ALL_CLEAR, closestNeighbor((int) Math.round((double) teamAvg.getTeleopAllClearScore() / teamData.size()), new int[]{0, 20}));
        teamAvg.put(MatchData.TELEOP_PARKING, closestNeighbor((int) Math.round((double) teamAvg.getTeleopParkingScore() / teamData.size()), new int[]{0, 5, 10, 20, 40, 80}));

        return teamAvg;
    }

    public static int scoringFunction(MatchData team1Avg, MatchData team2Avg) {
        int score = 0;

        score += team1Avg.getAutoBeaconScore();
        score += team2Avg.getAutoBeaconScore();

        score += team1Avg.getAutoClimberInShelterScore();
        score += team2Avg.getAutoClimberInShelterScore();

        score += team1Avg.getAutoParkingPoints();
        score += team2Avg.getAutoParkingPoints();

        score += team1Avg.getTeleopFloorGoalScore();
        score += team2Avg.getTeleopFloorGoalScore();

        score += team1Avg.getTeleopParkingScore();
        score += team2Avg.getTeleopParkingScore();

        score += team1Avg.getTeleopClimberInShelterScore();
        score += team2Avg.getTeleopClimberInShelterScore();

        score += team1Avg.getTeleopAllClearScore();
        score += team2Avg.getTeleopAllClearScore();

        score += Math.max(team1Avg.getTeleopClimberZipLineScore(), team2Avg.getTeleopClimberZipLineScore());

        int highGoalCarryover = 0;
        int midGoalCarryover = 0;

        if (team1Avg.getTeleopHighGoal() + team2Avg.getTeleopHighGoal() > 12) {
            score += 12 * MatchData.HIGH_GOAL_MULTIPLIER;
            highGoalCarryover = team1Avg.getTeleopHighGoal() + team2Avg.getTeleopHighGoal() - 12;
        } else {
            score += team1Avg.getTeleopHighGoal() + team2Avg.getTeleopHighGoal();
        }

        if (team1Avg.getTeleopHighGoal() + team2Avg.getTeleopHighGoal() > 12) {
            score += 12 * MatchData.HIGH_GOAL_MULTIPLIER;
            midGoalCarryover = team1Avg.getTeleopHighGoal() + team2Avg.getTeleopHighGoal() - 12;
        } else {
            score += team1Avg.getTeleopHighGoal() + team2Avg.getTeleopHighGoal();
        }

        if (team1Avg.getTeleopLowGoal() + team2Avg.getTeleopLowGoal() + highGoalCarryover + midGoalCarryover > 12) {
            score += 12 * MatchData.LOW_GOAL_MULTIPLIER;
            score += (team1Avg.getTeleopLowGoal() + team2Avg.getTeleopLowGoal() + highGoalCarryover + midGoalCarryover - 12) * MatchData.FLOOR_GOAL_MULTIPLIER;
        } else {
            score += (team1Avg.getTeleopLowGoal() + team2Avg.getTeleopLowGoal() + highGoalCarryover + midGoalCarryover) * MatchData.LOW_GOAL_MULTIPLIER;
        }

        return score;
    }

    public static int closestNeighbor(double value, int[] values) {

        Arrays.sort(values);

        double[] diffAbs = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            diffAbs[i] = Math.abs(values[i] - value);
        }

        double minValue = Double.MAX_VALUE;
        int minIndex = 0;

        for (int i = 0; i < diffAbs.length; i++) {
            if (diffAbs[i] <= minValue) {
                minValue = diffAbs[i];
                minIndex = i;
            }
        }

        return values[minIndex];
    }

    public static SimulationResult simulateRound(MatchData[] alliance1Avg, MatchData[] alliance2Avg) {
        int alliance1Score = scoringFunction(alliance1Avg[0], alliance1Avg[1]);
        int alliance2Score = scoringFunction(alliance2Avg[0], alliance2Avg[1]);

        SimulationResult sr = new SimulationResult();

        if (alliance1Score > alliance2Score) {
            sr.winner = FIRST;
            sr.margin = alliance1Score - alliance2Score;
        } else if (alliance1Score < alliance2Score) {
            sr.winner = SECOND;
            sr.margin = alliance2Score - alliance1Score;
        } else {
            sr.winner = TIE;
            sr.margin = 0;
        }

        return sr;
    }

    public static List<Integer> findOptimalAlliance(MatchData[] allData, Team curTeam) {
        List<Integer> teamList = new ArrayList<>();

        Arrays.sort(allData, new Comparator<MatchData>() {
            @Override
            public int compare(MatchData lhs, MatchData rhs) {
                return Integer.compare(lhs.getTeamNumber(), rhs.getTeamNumber());
            }
        });

        int loc = Arrays.binarySearch(allData, new Comparator<MatchData>() {
            @Override
            public int compare(MatchData lhs, MatchData rhs) {
                return Integer.compare(lhs.getTeamNumber(), rhs.getTeamNumber());
            }
        });

        MatchData curTeamAvg = allData[loc];

        MatchData[] allRelData = new MatchData[allData.length-1];
        int j = 0;
        for (int i = 0; i < allData.length; i++) {
            if (i == loc) {
                continue;
            }

            allRelData[j++] = allData[i];
        }

        List<AllianceData> possibleMatchups = new ArrayList<>();

        for (int i = 0; i < allData.length; i++) {
            for (j = i + 1; j < allData.length; j++) {
                AllianceData data = new AllianceData();
                data.teamID1 = allData[i].getTeamNumber();
                data.teamID2 = allData[j].getTeamNumber();
                data.score = scoringFunction(allData[i], allData[j]);

                possibleMatchups.add(data);
            }
        }

        Collections.sort(possibleMatchups, new Comparator<AllianceData>() {
            @Override
            public int compare(AllianceData lhs, AllianceData rhs) {
                return -Integer.compare(lhs.score, rhs.score);
            }
        });

        int[][] partners = new int[allRelData.length][2];

        for (int i = 0; i < partners.length; i++) {
            partners[i][0] = allRelData[i].getTeamNumber();

            int firstScore = scoringFunction(curTeamAvg, allRelData[i]);

            for (j = 0; j < possibleMatchups.size(); j++) {
                if (!possibleMatchups.get(j).contains(curTeamAvg.getTeamNumber()) && !possibleMatchups.get(j).contains(allRelData[i].getTeamNumber())) {
                    break;
                }
            }

            int secondScore = possibleMatchups.get(j).score;

            partners[i][1] = firstScore - secondScore;
        }

        Arrays.sort(partners, new Comparator<int[]>() {
            @Override
            public int compare(int[] lhs, int[] rhs) {
                return -Integer.compare(lhs[1], rhs[1]);
            }
        });

        for (int i = 0; i < partners.length; i++) {
            teamList.add(partners[i][0]);
        }

        return teamList;
    }

    public static final int TIE = 0;
    public static final int FIRST = 1;
    public static final int SECOND = 2;

    public static class SimulationResult {
        public int winner;
        public int margin;
    }

    public static class AllianceData {
        public int teamID1;
        public int teamID2;
        public int score;

        public boolean contains(int ID) {
            return (ID == teamID1) || (ID == teamID2);
        }
    }
}
