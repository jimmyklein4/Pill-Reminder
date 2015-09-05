/*package com.example.jimmyklein.pillreminder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudException;
import io.particle.android.sdk.cloud.ParticleDevice;
import io.particle.android.sdk.utils.Async;
import io.particle.android.sdk.utils.Toaster;

public class LoginActivity  {

    String email = "wikkii@msn.com";
    String password = "wikki123";

    public void login(){
                        // Don't:
                        AsyncTask task = new AsyncTask() {
                            @Override
                            protected Object doInBackground(Object[] params) {
                                try {
                                    ParticleCloud.get(LoginActivity.this).logIn(email, password);

                                } catch (final ParticleCloudException e) {
                                    Runnable mainThread = new Runnable() {
                                        @Override
                                        public void run() {
                                            Toaster.l(LoginActivity.this, e.getBestMessage());
                                            e.printStackTrace();
                                            Log.d("info", e.getBestMessage());
//                                            Log.d("info", e.getCause().toString());
                                        }
                                    };

                                }

                                return null;
                            }

                        };
//                        task.execute();

                        //-------

                        // DO!:
                        Async.executeAsync(ParticleCloud.get(v.getContext()), new Async.ApiWork<ParticleCloud, Integer>() {

                            private ParticleDevice mDevice;

                            @Override
                            public Integer callApi(ParticleCloud sparkCloud) throws ParticleCloudException, IOException {
                                sparkCloud.logIn(email, password);
                                sparkCloud.getDevices();
                                mDevice = sparkCloud.getDevice("1f0034000747343232361234");
                                Integer variable;
                                try {
                                    variable = mDevice.getVariable("analogvalue");
                                } catch (ParticleDevice.VariableDoesNotExistException e) {
                                    Toaster.s(LoginActivity.this, "Error reading variable");
                                    variable = -1;
                                }
                                return variable;

                            }

                            @Override
                            public void onSuccess(Integer value) {
                                Toaster.l(LoginActivity.this, "Logged in");
                                Intent intent = ValueActivity.buildIntent(LoginActivity.this, value, mDevice.getID());
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(ParticleCloudException e) {
                                Toaster.l(LoginActivity.this, e.getBestMessage());
                                e.printStackTrace();
                                Log.d("info", e.getBestMessage());
                            }
                        });


                    }
                }

        );
    }

}
*/