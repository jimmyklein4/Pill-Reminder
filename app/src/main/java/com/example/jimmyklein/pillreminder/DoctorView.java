package com.example.jimmyklein.pillreminder;



import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuInflater;

import android.content.Intent;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;
import java.util.ArrayList;


/**
 * Created by ramanjit on 9/5/2015.
 */
public class DoctorView extends Activity {

    ActionBar.Tab patientTab, alertTab, miscTab;
    //Fragment patientFragTab = new patientFragTab();
    //Fragment alertFragTab = new alertFragTab();
    //Fragment miscFragTab = new miscFragTab();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctorview);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        patientTab = actionBar.newTab();
        alertTab = actionBar.newTab();
        miscTab = actionBar.newTab();

        //patientTab.setTabListener(new ActionBar.TabListener(patientFragTab));

        actionBar.addTab(patientTab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }if (id == R.id.doctor_patient_add){
            //start doctor information intent
            Intent i = new Intent(DoctorView.this, DoctorViewAddPatient.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }

    public void displayError(String error) {
        // TODO: some error handling
    }

    public void updatePatients(List<String> patients) {
        // TODO: update the listing of patients
        // is it stored as a List inside of DoctorView?
        // who knows
    }

    public void getPatients() {
        DataHandler data = DataHandler.getInstance();
        Firebase docref = new Firebase(data.dataURI + "doctors/" + data.getUserID() + "/patients/");
        docref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> patients = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    patients.add(snapshot.getValue(String.class));
                }
                updatePatients(patients);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                displayError("Cancelled request");
            }
        });
    }

    public void populateSchedule(final String patient) {
        DataHandler data = DataHandler.getInstance();
        final Firebase patref = new Firebase(data.dataURI + "patients/" + patient);
        patref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(patref.child("schedule"));
                List<String> schedule = new ArrayList<> ();
                if (dataSnapshot.getValue() == null) {
                    displayError("Patient " + patient + " doesn't exist");
                    return;
                }
                for (DataSnapshot snapshot: dataSnapshot.child("schedule").getChildren()) {
                    schedule.add(snapshot.getValue(String.class));
                }
                // TODO: how is schedule populated?
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                displayError("request was cancelled");
            }
        });
    }
}
