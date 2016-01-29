package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StartScreenActivity extends AppCompatActivity {

    private List<Team> teams;
    private List<Competition> competitions;
    private volatile boolean isReady;

    private TextView loadText;

    private Drawable[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start_screen);

        loadText = (TextView) findViewById(R.id.load_text);
        images = new Drawable[10];

        isReady = false;

        new Thread(new Runnable() {
            @Override
            public void run() {

                ParseQuery<Team> teamQuery = ParseQuery.getQuery(Team.class);
                teamQuery.orderByAscending(Team.NUMBER);
                teamQuery.setLimit(1000);
                try {
                    Team.unpinAll();
                    teams = teamQuery.find();
                    Team.pinAll(teams);
                } catch (ParseException e) {
                    return;
                }

                ParseQuery<Competition> compQuery = ParseQuery.getQuery(Competition.class);
                compQuery.orderByDescending(Competition.DATE);
                compQuery.setLimit(1000);
                try {
                    Competition.unpinAll();
                    competitions = compQuery.find();
                    Competition.pinAll(competitions);

                    for (Competition comp : competitions) {
                        ParseRelation<Team> teamRel = comp.getTeams();
                        ParseQuery<Team> teamRelQuery = teamRel.getQuery();
                        teamRelQuery.orderByAscending(Team.NUMBER);
                        List<Team> tempTeamList = teamRelQuery.find();
                        Team.pinAll(tempTeamList);
                    }

                } catch (ParseException e) {
                    return;
                }

                isReady = true;

                Log.d("start activity", "is ready");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isReady) {}
                if (isReady) {
                    Log.d("start activity", "starting activity");
                    startActivity(new Intent(StartScreenActivity.this, OuterMenuActivity.class));
                }
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

}
