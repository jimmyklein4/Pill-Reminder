package com.example.jimmyklein.pillreminder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        ListView lv = (ListView) v.findViewById(R.id.listView3);
        ArrayAdapter<String> arradapter = new ArrayAdapter<String>(
                v.getContext(),
                android.R.layout.simple_list_item_1,
                DataHandler.getInstance().getAlerts()
        );
        lv.setAdapter(arradapter);
        return v;
    }

    public void alertTaken(){
        SparkCore taken = new SparkCore();
        taken.callLedFunction("taken");
    }
    public void alertMissed(){
        SparkCore missed = new SparkCore();
        missed.callLedFunction("missed");
    }
    public void alertOff(){
        SparkCore off = new SparkCore();
        off.callLedFunction("off");
    }
}
