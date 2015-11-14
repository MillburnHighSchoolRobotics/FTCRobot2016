package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    String selectedCompetition = "";

    @Override
    public void onBackPressed() {
        Intent toMenu = new Intent(this, MenuActivity.class);
        toMenu.putExtra("SelectedCompetition", selectedCompetition);
        startActivity(toMenu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_competition);


        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra("SelectedCompetition");

        final Spinner selectCompetition = (Spinner) findViewById(R.id.spinner_select_comp);
        final Button select = (Button) findViewById(R.id.submit_button);

        ParseQuery<Competition> query = ParseQuery.getQuery(Competition.class);
        query.orderByDescending(Competition.DATE);
        List<Competition> compList = null;
        try {
            compList = query.find();
        } catch (ParseException e) {
            Toast.makeText(this, "Query Error", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> compNames = new ArrayList<String>();
        final Map<String, Competition> compMap = new HashMap<String, Competition>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        for (Competition c: compList) {

            Log.e("qqq", sdf.format(c.getDate()));
            String idString = sdf.format(c.getDate()) + " - " + c.getName();

            compNames.add(idString);
            compMap.put(idString, c);
        }

        ArrayAdapter<String> compAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, compNames);
        selectCompetition.setAdapter(compAdapter);

        final String[] selectResult = {""};

        selectCompetition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectResult[0] = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                selectResult[0] = "";
            }
        });

        select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                selectedCompetition = compMap.get(selectResult[0]).getObjectId();
                onBackPressed();
            }
        });
    }

}
