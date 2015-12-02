package co.millburnrobotics.ftcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.SaveCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddCompetitionActivity extends AppCompatActivity {

    private String selectedCompetition = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_competition);

        //INSERT PARSE INIT STUFF HERE

        Intent incoming = getIntent();
        selectedCompetition = incoming.getStringExtra("SelectedCompetition");

        final EditText date = (EditText) findViewById(R.id.date);
        final EditText teamNumber = (EditText) findViewById(R.id.teamnumber);
        final Spinner typeSpinner = (Spinner) findViewById(R.id.type_spinner);
        final EditText compName = (EditText) findViewById(R.id.compname);
        Log.e("qqq", Boolean.toString(typeSpinner == null));

        String[] spinneroptions ={Competition.QUALIFIER,Competition.MEET, Competition.CHAMPIONSHIP};

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinneroptions);

        typeSpinner.setAdapter(adapter);

        final String[] spinnerSelected = {""};

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSelected[0] = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent){
                spinnerSelected[0]= "";
            }
        });

        final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        final Button submit = (Button) findViewById(R.id.competition);



        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                if(compName.getText().toString().length() == 0 || date.getText().toString().length()==0 || teamNumber.getText().toString().length() == 0 ){
                    Toast.makeText(v.getContext(),"no competition available ", Toast.LENGTH_SHORT).show();
                    return;
                }

                Competition competition = new Competition();

                competition.setName(compName.getText().toString());
                competition.setType(spinnerSelected[0]);
                try {
                    competition.setDate(sdf.parse(date.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                competition.setHostingTeamID(Integer.parseInt(teamNumber.getText().toString()));

                competition.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e == null) {
                            Toast.makeText(v.getContext(), "Competition Saved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), "Competition Not Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        final Button doneButton = (Button) findViewById(R.id.done);
        doneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent toMenu = new Intent(this, MenuActivity.class);
        toMenu.putExtra("SelectedCompetition", selectedCompetition);
        startActivity(toMenu);
    }
}
