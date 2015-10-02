package com.example.anthony.ftcscoutingappfinal;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Yanjun on 9/29/2015.
 */

@ParseClassName("MatchData")
public class MatchData extends ParseObject {

    public MatchData() {

    }

    public void setCompetition(Competition competition) {
        put(COMPETITION, competition);
    }

    public Competition getCompetition() {
        return (Competition) getParseObject(COMPETITION);
    }

    public void setTeam(Team team) {
        put(TEAM, team);
    }

    public Team getTeam() {
        return (Team) getParseObject(TEAM);
    }

    public static final String COMPETITION = "Competition";
    public static final String TEAM = "Team";

}
