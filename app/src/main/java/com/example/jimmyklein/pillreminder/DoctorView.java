package com.example.jimmyklein.pillreminder;



import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuInflater;

import android.content.Intent;


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
        }

        return super.onOptionsItemSelected(item);
    }


}
