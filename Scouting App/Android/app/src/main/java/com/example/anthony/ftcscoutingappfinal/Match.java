package com.example.anthony.ftcscoutingappfinal;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import com.parse.ParseException;
import com.parse.ParseRelation;

import java.util.List;

/**
 * Created by Yanjun on 9/29/2015.
 */
@ParseClassName("Match")
public class Match extends ParseObject {

    public Match() {

    }

    public void setCompetitionName(String competitionName) {
        put(COMPETITION_NAME, competitionName);
    }

    public String getCompetitionName() {
        return getString(COMPETITION_NAME);
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

    public void addMatchData(MatchData matchData) {
        ParseRelation<MatchData> relation = getRelation(MATCH_DATA);
        relation.add(matchData);
    }

    public void removeMatchData(MatchData matchData) {
        ParseRelation<MatchData> relation = getRelation(MATCH_DATA);
        relation.remove(matchData);
    }

    public ParseRelation<MatchData> getMatchDataz() {
        return getRelation(MATCH_DATA);
    }

    public String getNotes() {
        return getString(NOTES);
    }

    public void setNotes(String notes) {
        put(NOTES, notes);
    }

    public int getBlueAllianceRawScore() {
        return getInt(BLUE_ALLIANCE_RAW_SCORE);
    }

    public void setBlueAllianceRawScore(int score) {
        put(BLUE_ALLIANCE_RAW_SCORE, score);
    }

    public int getBlueAlliancePenalty() {
        return getInt(BLUE_ALLIANCE_PENALTY);
    }

    public void setBlueAlliancePenalty(int penalty) {
        put(BLUE_ALLIANCE_PENALTY, penalty);
    }

    public int getRedAllianceRawScore() {
        return getInt(RED_ALLIANCE_RAW_SCORE);
    }

    public void setRedAllianceRawScore(int score) {
        put(RED_ALLIANCE_RAW_SCORE, score);
    }

    public int getRedAlliancePenalty() {
        return getInt(RED_ALLIANCE_PENALTY);
    }

    public void setRedAlliancePenalty(int penalty) {
        put(RED_ALLIANCE_PENALTY, penalty);
    }

    public int getRedAllianceTotalScore() {
        return getInt(RED_ALLIANCE_RAW_SCORE) + getInt(RED_ALLIANCE_PENALTY);
    }

    public int getBlueAllianceTotalScore() {
        return getInt(BLUE_ALLIANCE_RAW_SCORE) + getInt(BLUE_ALLIANCE_PENALTY);
    }


    public static final String COMPETITION_NAME = "Competition Name";
    public static final String MATCH_DATA = "Match Data";
    public static final String NOTES = "Notes";
    public static final String BLUE_ALLIANCE_RAW_SCORE = "Blue Alliance Raw Score";
    public static final String BLUE_ALLIANCE_PENALTY = "Blue Alliance Penalty";
    public static final String RED_ALLIANCE_RAW_SCORE = "Red Alliance Raw Score";
    public static final String RED_ALLIANCE_PENALTY = "Red Alliance Penalty";
}
