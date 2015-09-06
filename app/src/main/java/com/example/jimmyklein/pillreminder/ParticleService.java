package com.example.jimmyklein.pillreminder;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by shua on 9/5/15.
 */
public interface ParticleService {
    @FormUrlEncoded
    @POST("/devices/{device}/{function}")
    public void callFunction(
            @Path("device") String device,
            @Path("function") String function,
            @Field("arg") String arg,
            @Field("access_token") String tok,
            retrofit.Callback<String> callback);
}
