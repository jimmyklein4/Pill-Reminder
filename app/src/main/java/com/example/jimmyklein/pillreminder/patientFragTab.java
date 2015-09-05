package com.example.jimmyklein.pillreminder;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waqas on 9/5/2015.
 */
public class patientFragTab extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.patient_fragment, container, false);
        ListView lv = (ListView) v.findViewById(R.id.listView2);
        List<String> arrlist = new ArrayList<>();
        arrlist.add("hey");
        arrlist.add("ho");
        ArrayAdapter<String> arradapter = new ArrayAdapter<String>(
                v,
                android.R.layout.simple_list_item_1,
                arrlist
        );
        lv.setAdapter(arradapter);
        arrlist.add("what?");
        return v;


    }
}
