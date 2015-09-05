package com.example.jimmyklein.pillreminder;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuInflater;

import android.content.Intent;

/**
 * Created by ramanjit on 9/5/2015.
 */
public class DoctorInformation extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_information);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //MenuInflater mnuInflater = getSupportMenuInflater();
        //inflater.inflate(R.menu.main_activity_actions, menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            System.out.println("setting");
        }

        return super.onOptionsItemSelected(item);
    }

    public void moveToPatientPage(View view) {
        System.out.println("fdgfdgdf");
        //Intent i = new Intent( DoctorPatientActivity.this, PatientLogIn.class);
        //startActivity(i);

    }




}
