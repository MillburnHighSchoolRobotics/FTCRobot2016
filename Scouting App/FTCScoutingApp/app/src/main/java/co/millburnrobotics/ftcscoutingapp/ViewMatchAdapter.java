package co.millburnrobotics.ftcscoutingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by shant on 11/9/2015.
 */
public class ViewMatchAdapter extends ArrayAdapter<MatchData> {
    Context context;
    int layoutResourceId;

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_view_match_data_team, null);
        }

    }
}
