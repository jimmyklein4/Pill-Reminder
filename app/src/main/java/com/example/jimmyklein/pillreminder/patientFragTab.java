package com.example.jimmyklein.pillreminder;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        getPatients();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.patient_fragment, container, false);
        ListView lv = (ListView) v.findViewById(R.id.listView2);
        ArrayAdapter<String> arradapter = new ArrayAdapter<String>(
                v.getContext(),
                android.R.layout.simple_list_item_1,
                patients
        );
        lv.setAdapter(arradapter);
        return v;
    }

    public void getPatients() {
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
