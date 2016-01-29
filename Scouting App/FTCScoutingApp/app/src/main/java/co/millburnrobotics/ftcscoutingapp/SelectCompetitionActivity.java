package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectCompetitionActivity extends AppCompatActivity {

    private String selectedCompetition;

    private Spinner compSpinner;
    private AdvButton select;
    private String selectedComp;

    private List<Competition> compList;
    private ArrayAdapter<String> compAdapter;
    private List<String> compNames;
    private Map<String, Competition> compMap;

    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_select_competition);

        loadIntent();

        compSpinner = (Spinner) findViewById(R.id.spinner_select_comp);
        select = new AdvButton((ImageButton) findViewById(R.id.submit_button), R.drawable.confirm, R.drawable.confirm_down);

        ParseQuery<Competition> query = ParseQuery.getQuery(Competition.class);
        query.fromLocalDatastore();
        query.orderByDescending(Competition.DATE);
        List<Competition> compList = null;
        try {
            compList = query.find();
        } catch (ParseException e) {
            Toast.makeText(this, "Query Error", Toast.LENGTH_SHORT).show();
            return;
        }

        compNames = new ArrayList<>();
        compMap = new HashMap<>();
        sdf = new SimpleDateFormat("MM/dd/yyyy");

        for (Competition c : compList) {
            String idString = sdf.format(c.getDate()) + " - " + c.getName();

            compNames.add(idString);
            compMap.put(idString, c);
        }

        compAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, compNames);
        compSpinner.setAdapter(compAdapter);

        compSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedComp = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                selectedComp = "";
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCompetition = compMap.get(selectedComp).getObjectId();
                goToInnerMenuPage();
            }
        });
    }

    public void onBackPressed() {
        goToMenuPage();
    }

    private void loadIntent() {
        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra(IntentName.SELECTED_COMPETITION);
    }

    private void goToMenuPage() {
        Intent toMenu = new Intent(this, OuterMenuActivity.class);
        toMenu.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        startActivity(toMenu);
    }

    private void goToInnerMenuPage() {
        Intent toMenu = new Intent(this, InnerMenuActivity.class);
        toMenu.putExtra(IntentName.SELECTED_COMPETITION, selectedCompetition);
        startActivity(toMenu);
    }

}
