package com.example.jimmyklein.pillreminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waqas on 9/5/2015.
 */
public class patientFragTab extends Fragment {
    private List<String> patients = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.patient_fragment, container, false);
        final ListView lv = (ListView) v.findViewById(R.id.listView2);
        ArrayAdapter<String> arradapter = new ArrayAdapter<String>(
                v.getContext(),
                android.R.layout.simple_list_item_1,
                DataHandler.getInstance().getPatients()
        );
        lv.setAdapter(arradapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sel = lv.getItemAtPosition(position).toString().trim();
                DataHandler.getInstance().setScheduleID(sel);
                Intent i = new Intent( v.getContext(), PatientView.class);
                startActivity(i);
            }
        });
        return v;
    }

    public void getPatients() {
        for (String pat : DataHandler.getInstance().getPatients()) {
            patients.add(pat);
        }
    }

    public void getFirePatients() {
        final DataHandler data = DataHandler.getInstance();
        Firebase patref = new Firebase(data.dataURI + "doctors/" + data.getUserID() + "/patients/");
        patref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("getting patients for doctor");
                if (dataSnapshot.getValue() == null) {
                    return;
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    patients.add(snapshot.getKey());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("request was cancelled");
            }
        });
    }
}
