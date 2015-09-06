package com.example.jimmyklein.pillreminder;

/**
 * Created by ramanjit on 9/5/2015.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import java.util.UUID;

public class PatientView extends Activity {

    private PebbleDictionary data;
    private final static UUID PEBBLE_APP_UUID = UUID.fromString("1886509e-785a-43d9-906d-a66e820ca16a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //alertPebble();
        setContentView(R.layout.patient_view);
        ListView lv = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> arrad = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                DataHandler.getInstance().getSchedule()
        );
        lv.setAdapter(arrad);

        Firebase alertref = new Firebase(DataHandler.getInstance().dataURI + "small/alerts");
        alertref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue(Boolean.class)) {
                    System.out.println("NEW ALERT THING YUR MISSING YUR MED " + dataSnapshot.getKey());
                    // TODO: add pebble
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
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
        if (id == R.id.doctor_info){
            //start doctor information intent
            Intent i = new Intent(PatientView.this, PatientDoctorInformation.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
/*
    public void alertPebble(){
        if(PebbleKit.isWatchConnected(getApplicationContext())){
            data = new PebbleDictionary();
            data.addUint8(0, (byte) 42);
            PebbleKit.sendDataToPebble(getApplicationContext(), PEBBLE_APP_UUID, data);
            PebbleKit.registerReceivedAckHandler(getApplicationContext(), new PebbleKit.PebbleAckReceiver(PEBBLE_APP_UUID) {
                @Override
                public void receiveAck(Context context, int transactionId) {
                    Log.i(getLocalClassName(), "Received ack for transaction " + transactionId);
                }
            });
            PebbleKit.registerReceivedNackHandler(getApplicationContext(), new PebbleKit.PebbleNackReceiver(PEBBLE_APP_UUID) {
                @Override
                public void receiveNack(Context context, int transactionId) {
                    if(data!= null){
                        PebbleKit.sendDataToPebble(getApplicationContext(), PEBBLE_APP_UUID, data);
                    } else {
                        data = new PebbleDictionary();
                        data.addUint8(0, (byte) 42);
                    }
                }

            });
        }
    }
*/
}
