package com.example.jimmyklein.pillreminder;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
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
        System.out.println(email.getText().toString());
        if( !(android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) ){
            System.out.println("please enter a valid email");
            Toast.makeText(DoctorViewAddPatient.this, "Please enter a valid email!", Toast.LENGTH_SHORT).show();

        }else {
            addPatient(email.getText().toString(), name.getText().toString());
            Intent in = new Intent(DoctorViewAddPatient.this, DoctorView.class);
            startActivity(in);
            finish();
        }
    }

    public void addPatient(final String email, final String patientname) {
        DataHandler.getInstance().addPatient(patientname, email);
    }

    public void addFirePatient(final String email, final String patientname) {
        System.out.println("email: " + email + " name: " + patientname);
        DataHandler data = DataHandler.getInstance();
        Firebase docref = new Firebase(data.dataURI + "doctors/" + data.getUserID() + "/patients/");
        docref.child(data.sanitizeKey(email)).setValue(true);
        Firebase patref = new Firebase(data.dataURI + "patients/" + data.sanitizeKey(email));
        patref.child("name").setValue(patientname);
    }
}
