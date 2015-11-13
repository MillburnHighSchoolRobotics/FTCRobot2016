package co.millburnrobotics.ftcscoutingapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseRelation;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yanjun on 9/29/2015.
 */

@ParseClassName("Competition")
public class Competition extends ParseObject {
    public Competition() {
        if (tournamentType.size() == 0) {
            tournamentType.add(MEET);
            tournamentType.add(QUALIFIER);
            tournamentType.add(CHAMPIONSHIP);
        }
    }

    public void setDate(Date date) {
        put(DATE, date);
    }

    public void setHostingTeamID(int hostingTeamID) {
        put(HOSTING_TEAM_ID, hostingTeamID);
    }

    public void setType(String type) {
        if (tournamentType.contains(type)) {
            put(TYPE, type);
        }
    }

    public Date getDate() {
        return getDate(DATE);
    }

    public int getHostingTeamID() {
        return getInt(HOSTING_TEAM_ID);
    }

    public String getType() {
        return getString(TYPE);
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

    public void addTeam(Team team) {
        ParseRelation<Team> teams = getRelation(TEAM);
        teams.add(team);
    }

    public void removeTeam(Team team) {
        ParseRelation<Team> teams = getRelation(TEAM);
        teams.remove(team);
    }

    public ParseRelation<Team> getTeams() {
        return getRelation(TEAM);
    }

    public void setName(String name) {
        put(NAME, name);
    }

    public String getName() {
        return getString(NAME);
    }

    public static final String DATE = "Date";
    public static final String HOSTING_TEAM_ID = "HostingTeamID";
    public static final String TYPE = "Type";
    public static final String MATCH = "Match";
    public static final String TEAM = "Team";
    public static final String NAME = "Name";

    public static final String QUALIFIER = "Qualifier";
    public static final String MEET = "Meet";
    public static final String CHAMPIONSHIP = "Championship";

    public static final Set<String> tournamentType = new HashSet<String>();
}
