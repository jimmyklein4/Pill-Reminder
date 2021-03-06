package com.example.jimmyklein.pillreminder;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ramanjit on 9/5/2015.
 */
public class DoctorView extends FragmentActivity {

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctorview);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec("Patient").setIndicator("Patient", null),
                patientFragTab.class, null);

        mTabHost.addTab(
                mTabHost.newTabSpec("Alert").setIndicator("Alert", null),
                alertFragTab.class, null);

        mTabHost.addTab(
                mTabHost.newTabSpec("Dosage").setIndicator("Dosage", null),
                miscFragTab.class, null);



        //Color change for the tabs
        //mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.rgb(22, 240, 214));

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
            finish();
        }
        if (id == R.id.spark_Core){
            Intent intent = new Intent(DoctorView.this, SparkCore.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    public void submitDosage (View view){
        DecimalFormat f = new DecimalFormat("00");

        Spinner patient = (Spinner) findViewById(R.id.spinner1);
        TimePicker dosage_time = (TimePicker) findViewById(R.id.timePicker);
        DatePicker dosage_date = (DatePicker) findViewById(R.id.datePicker);
        EditText description = (EditText) findViewById(R.id.editText5);
        System.out.println(dosage_time.getCurrentHour() + ":" + dosage_time.getCurrentMinute());
        String time = dosage_time.getCurrentHour()+":"+f.format( dosage_time.getCurrentMinute()).toString();
        String date = (dosage_date.getMonth()+1) + "/" + dosage_date.getDayOfMonth()+"/"+dosage_date.getYear();
        String descriptionString = description.getText().toString();
        String patientString = (patient != null) ? patient.getSelectedItem().toString(): "[null]";
        System.out.println(patientString + " " + descriptionString + " " + time + " " + date);

        DataHandler data = DataHandler.getInstance();
        Firebase ref = new Firebase(data.dataURI + "small/schedule");
        ref.child(data.sanitizeKey(time + " " + descriptionString)).setValue(false);
        Toast.makeText(DoctorView.this, "Dosage Added!", Toast.LENGTH_SHORT).show();

        if(patientString.equals("csmith@email.com")) {
            Firebase ref2 = new Firebase(data.dataURI + "small/alerts");
            ref2.child(data.sanitizeKey(time + " " + descriptionString + " - " + patientString)).setValue(true);
        }

        mTabHost.setCurrentTab(0);
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
                    displayError("Patient " + patient + " doesn't exist.");
                    return;
                }
                for (DataSnapshot snapshot: dataSnapshot.child("schedule").getChildren()) {
                    schedule.add(snapshot.getValue(String.class));
                }
                // TODO: how is schedule populated?
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                displayError("Request was cancelled");
            }
        });
    }
}
