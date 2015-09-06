package com.example.jimmyklein.pillreminder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import io.particle.android.sdk.cloud.SparkCloud;
import io.particle.android.sdk.cloud.SparkCloudException;
import io.particle.android.sdk.utils.Async;
import io.particle.android.sdk.utils.Toaster;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SparkCore extends Activity {
    ParticleService service;
    Button buttonTaken;
    Button buttonMissed;
    Button buttonOff;
    Button buttonLogin;

    String email = "wikkii@msn.com";
    String password = "pillpal"; //will put my password on here but not on git
    String uri = "https://api.particle.io/v1";
    String device = "54ff6f066678574939320667";
    String function = "led";
    String token = "4ce579f5816c2d8983692b86d6bf745a4da421c0";

    public void callLedFunction(String arg) {
        RestAdapter retro = new RestAdapter.Builder()
                .setEndpoint(uri)
                .build();
        service = retro.create(ParticleService.class);
        service.callFunction(device, function, arg, token, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                System.out.println(s);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.toString());
                System.out.println(error.getUrl());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spark_core);

        buttonLogin = (Button) findViewById(R.id.butLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginSpark(v.getContext(), email, password);

            }
        });

        buttonTaken = (Button) findViewById(R.id.butTaken);
        buttonTaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callLedFunction("taken");
                PatientView obj = new PatientView();
                obj.alertPebble();
            }
        });

        buttonMissed = (Button) findViewById(R.id.butMissed);
        buttonMissed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callLedFunction("missed");
                PatientView obj = new PatientView();
                obj.alertPebble();
            }
        });

        buttonOff = (Button) findViewById(R.id.butOff);
        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callLedFunction("off");
            }
        });

    }

    public void loginSpark(Context c, final String email, final String password){

        Async.executeAsync(SparkCloud.get(c), new Async.ApiWork<SparkCloud, Void>() {

            @Override
            public Void callApi(SparkCloud particleCloud) throws SparkCloudException, IOException {
                particleCloud.logIn(email, password);
                return null;
            }

            @Override
            public void onSuccess(Void aVoid) {
                Toaster.l(SparkCore.this, "Logged in");
            }

            @Override
            public void onFailure(SparkCloudException exception) {
                Log.e("Fail", String.valueOf(exception));
                Toaster.l(SparkCore.this, "Wrong shit son");
            }
        });

    }
/*
    public void takenCmd(Context c){
        Async.executeAsync(SparkCloud.get(c), new Async.ApiWork<SparkDevice, Integer>() {

            @Override
            public Integer callApi(SparkDevice sparkDevice) throws SparkCloudException, IOException{
                try {
                    return sparkDevice.callFunction("digitalwrite", list("D7", "HIGH"));
                } catch (SparkDevice.FunctionDoesNotExistException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(Integer integer) {
                Toaster.s(SparkCore.this, "LED ON D7 sucessfullly turned on");
            }

            @Override
            public void onFailure(SparkCloudException exception) {

            }
        });

    }
*/


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
