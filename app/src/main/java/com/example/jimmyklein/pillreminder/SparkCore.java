package com.example.jimmyklein.pillreminder;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudException;
import io.particle.android.sdk.utils.Async;
import io.particle.android.sdk.utils.Toaster;

public class SparkCore extends Activity {

    Button buttonTaken;
    Button buttonMissed;
    Button buttonOff;
    Button buttonLogin;

    String email = "wikkii@msn.com";
    String password = "wikki123"; //will put my password on here but not on git
    String TOKEN = "";
    String API_URL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spark_core);

        buttonLogin = (Button) findViewById(R.id.butLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSpark(v.getContext(), email, password);

            }
        });

        buttonTaken = (Button) findViewById(R.id.butTaken);
        buttonTaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonMissed = (Button) findViewById(R.id.butMissed);
        buttonMissed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonOff = (Button) findViewById(R.id.butOff);
        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void loginSpark(Context c, final String email, final String password){

        Async.executeAsync(ParticleCloud.get(c), new Async.ApiWork<ParticleCloud,Void>(){

            @Override
            public Void callApi( ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                particleCloud.logIn(email, password);
                return null;
            }

            @Override
            public void onSuccess(Void aVoid) {
                Toaster.l(SparkCore.this, "Logged in");
            }

            @Override
            public void onFailure(ParticleCloudException exception) {
                Log.e("Fail", String.valueOf(exception));
                Toaster.l(SparkCore.this, "Wrong shit son");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spark_core, menu);
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
