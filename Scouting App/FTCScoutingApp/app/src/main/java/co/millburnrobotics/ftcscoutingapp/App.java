package co.millburnrobotics.ftcscoutingapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Yanjun on 11/12/2015.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Competition.class);
        ParseObject.registerSubclass(Match.class);
        ParseObject.registerSubclass(MatchData.class);
        ParseObject.registerSubclass(Team.class);
        Parse.initialize(this, "SlG9zvrlCyjen53XU3WUaf3HAYoZQpra08iCLQNC", "vyRgs4rAN6Ukj6qPfm2fzKNXlTbV8n3ALVringOF");
    }
}
