package com.example.anthony.ftcscoutingappfinal;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Yanjun on 9/29/2015.
 */
@ParseClassName("Match")
public class Match extends ParseObject {

    public Match() {

    }

    public void setCompetition(Competition competition) {
        put(COMPETITION, competition);
    }

    public Competition getCompetition() {
        return (Competition) getParseObject(COMPETITION);
    }

    public static final String COMPETITION = "Competition";

}
