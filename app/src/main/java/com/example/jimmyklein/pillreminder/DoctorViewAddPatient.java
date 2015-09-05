package com.example.jimmyklein.pillreminder;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuInflater;

import android.content.Intent;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by ramanjit on 9/5/2015.
 */
public class DoctorViewAddPatient extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.doctorview_add_patient);
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
        }

        return super.onOptionsItemSelected(item);
    }


    public void addPatientClicked(View view){
        EditText email = (EditText)findViewById(R.id.editText3);
        EditText name = (EditText)findViewById(R.id.editText4);

        String record  = "email:" + email.getText().toString() + " name:" + name.getText().toString();
        addPatient(record);

    }
    public void addPatient(final String patientname) {
        DataHandler data = DataHandler.getInstance();
        Firebase docref = new Firebase(data.dataURI + "doctors/" + data.getUserID() + "/patients/");
        docref.child(patientname).setValue(true);
    }

}
