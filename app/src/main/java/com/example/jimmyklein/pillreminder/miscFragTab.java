package com.example.jimmyklein.pillreminder;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.Activity;
/**
 * Created by Waqas on 9/5/2015.
 */
public class miscFragTab extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_dosage, container, false);
        Spinner miscpat = (Spinner)v.findViewById(R.id.spinner1);
        String[] patients = {"sue@some.com", "bob@none.co", "gregg@rad.biz"};
        ArrayAdapter<String> mparrad = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, patients);
        //mparrad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        miscpat.setAdapter(mparrad);
        return v;
    }


}
