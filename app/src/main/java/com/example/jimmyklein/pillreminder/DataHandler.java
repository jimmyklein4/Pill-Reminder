package com.example.jimmyklein.pillreminder;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by shua on 9/5/15.
 */
public class DataHandler {
    public static final String dataURI = "https://pillreminder.firebaseio.com/";
    private boolean isLoggedIn = false;
    private boolean isDoctor = false;
    private String userID;

    public String getUserID() { return userID; }
    public boolean getIsLoggedIn() { return isLoggedIn; }
    public boolean getIsDoctor() { return isDoctor; }

    public void setLogin(String uid, boolean isdoc) {
        isLoggedIn = true;
        userID = uid;
        isDoctor = isdoc;
    }

    private static final DataHandler handler = new DataHandler();
    public static DataHandler getInstance() {
        return handler;
    }

}
