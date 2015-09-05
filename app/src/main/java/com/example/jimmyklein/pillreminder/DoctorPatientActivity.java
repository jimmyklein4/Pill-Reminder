package com.example.jimmyklein.pillreminder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class DoctorPatientActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.doctor_patient_view);
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

    public void displayError(String error) {
        // TODO: display actual login error
        return;
    }

    public void tryPatientLogin(final String username, final String password) {
        DataHandler data = DataHandler.getInstance();
        System.out.println("in tryPatientLogin: " + username + ", " + password);
        Firebase patref = new Firebase(data.dataURI + "patients/" + username);
        patref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    displayError("Username not found");
                } else {
                    System.out.println("logging in as pat " + username);
                    DataHandler.getInstance().setLogin(username, false);
                    moveToPatientPage();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                displayError("Login request cancelled");
            }
        });
    }

    public void tryDocLogin(final String username, final String password) {
        DataHandler data = DataHandler.getInstance();
        System.out.println("in tryDocLogin: " + username + ", " + password);
        Firebase docref = new Firebase(data.dataURI + "doctors/" + username);
        docref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("tryDocLogin onDataChange");
                if (dataSnapshot.getValue() == null) {
                    tryPatientLogin(username, password);
                } else {
                    System.out.println("logging in as doc " + username);
                    DataHandler.getInstance().setLogin(username, true);
                    moveToPatientPage();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                displayError("Login request cancelled");
            }
        });
    }

    public void login(View view) {
        // TODO: actually get text information here instead of string username
        String username = "pat1";
        System.out.println("Trying to login with " + username);
        tryDocLogin(username, "");
    }

    public void moveToPatientPage() {
        Intent i = new Intent( DoctorPatientActivity.this, PatientLogIn.class);
        startActivity(i);
    }

    public void moveToDoctorPage() {
        // TODO: move to the doctor pages
    }
}
