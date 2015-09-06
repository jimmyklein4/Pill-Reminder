package com.example.jimmyklein.pillreminder;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Waqas on 9/5/2015.
 */
public class alertFragTab extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alert_fragment, container, false);
        return v;
        /* TODO
        * Setup timer to time the dosage taken every day
        * if the pill bottle doesnt return "taken" value in time, the timer sets off the alarm and sends another request to the pillbottle
        *   Missed Command sent
        * The LED turns rfed indicating pill was not taken.
        * */


    }
}
