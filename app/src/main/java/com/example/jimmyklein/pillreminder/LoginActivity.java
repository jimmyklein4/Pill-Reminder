package com.example.jimmyklein.pillreminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.login_view);
        //hides the action bar on the top on the login view
        getActionBar().hide();
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
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    public void tryPatientLogin(final String uid, final String password) {
        DataHandler data = DataHandler.getInstance();
        System.out.println("in tryPatientLogin: " + uid + ", " + password);
        Firebase patref = new Firebase(data.dataURI + "patients/" + uid);
        patref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    displayError("Username Not Found");
                } else {
                    System.out.println("logging in as pat " + uid);
                    DataHandler.getInstance().setLogin(uid, false);
                    moveToPatientPage();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                displayError("Login Request Cancelled");
            }
        });
    }

    public void tryDocLogin(final String email, final String password) {
        DataHandler data = DataHandler.getInstance();
        System.out.println("in tryDocLogin: " + email + ", " + password);
        final String uid = data.sanitizeKey(email);
        Firebase docref = new Firebase(data.dataURI + "doctors/" + uid);
        docref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("tryDocLogin onDataChange");
                if (dataSnapshot.getValue() == null) {
                    tryPatientLogin(uid, password);
                } else {
                    System.out.println("logging in as doc " + uid);
                    DataHandler.getInstance().setLogin(uid, true);
                    moveToDoctorPage();
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
        // NOT NEEDED FOR DEMO
        EditText i =  (EditText) findViewById(R.id.editText);
        String result = i.getText().toString();
        if( !(android.util.Patterns.EMAIL_ADDRESS.matcher(result).matches()) ){
            Toast.makeText(LoginActivity.this,"please enter a valid email!", Toast.LENGTH_SHORT).show();
        }else {
            System.out.println("Trying to login with " + result);
            tryDocLogin(result, "");
        }
    }

    public void moveToPatientPage() {
        Intent i = new Intent( LoginActivity.this, PatientView.class);
        startActivity(i);

    }

    public void moveToDoctorPage() {
        Intent i = new Intent( LoginActivity.this, DoctorView.class);
        startActivity(i);
    }
}
