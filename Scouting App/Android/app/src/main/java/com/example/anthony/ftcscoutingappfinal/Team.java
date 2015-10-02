package com.example.anthony.ftcscoutingappfinal;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseRelation;

/**
 * Created by Yanjun on 9/29/2015.
 */

@ParseClassName("Team")
public class Team extends ParseObject {
    public Team() {

    }

    public int getNumber() {
        return getInt(NUMBER);
    }

    public String getName() {
        return getString(NAME);
    }

    public void setNumber(int number) {
        put(NUMBER, number);
    }

    public void setName(String name) {
        put(NAME, name);
    }

    public void addCompetition(Competition competition) {
        ParseRelation<Competition> competitions = getRelation(COMPETITION);
        competitions.add(competition);
    }

    public void removeCompetition(Competition competition) {
        ParseRelation<Competition> competitions = getRelation(COMPETITION);
        competitions.remove(competition);
    }

    public ParseRelation<Competition> getCompetitions() {
        return getRelation(COMPETITION);
    }

    public void addMatch(Match match) {
        ParseRelation<Match> matches = getRelation(MATCH);
        matches.add(match);
    }

    public void removeMatch(Match match) {
        ParseRelation<Match> matches = getRelation(MATCH);
        matches.remove(match);
    }

    public ParseRelation<Match> getMatches() {
        return getRelation(MATCH);
    }

    public void addMatchData(MatchData matchData) {
        ParseRelation<MatchData> matchDataz = getRelation(MATCH_DATA);
        matchDataz.add(matchData);
    }

    public void removeMatchData(MatchData matchData) {
        ParseRelation<MatchData> matchDataz = getRelation(MATCH_DATA);
        matchDataz.remove(matchData);
    }

    public ParseRelation<MatchData> getMatchDataz() {
        return getRelation(MATCH_DATA);
    }

    public static final String NUMBER = "Number";
    public static final String NAME = "Name";
    public static final String COMPETITION = "Competition";
    public static final String MATCH = "Match";
    public static final String MATCH_DATA = "Match Data";

}
