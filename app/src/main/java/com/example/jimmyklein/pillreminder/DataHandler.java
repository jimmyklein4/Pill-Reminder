package com.example.jimmyklein.pillreminder;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by shua on 9/5/15.
 */
public class DataHandler {
    public static final String dataURI = "https://pillreminder.firebaseio.com/";
    private static Map<String, String> patients;
    private static Map<String, String> schedule;
    private static Map<String, String> alerts;
    private static String scheduleID;
    private boolean isLoggedIn = false;
    private boolean isDoctor = false;
    private String userID;

    public DataHandler() {
        patients = new HashMap<>();
        schedule = new TreeMap<>();
        alerts = new TreeMap<>();
        Firebase ref = new Firebase(dataURI + "small/patients");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                patients.put(dataSnapshot.getKey(), dataSnapshot.getValue(String.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                patients.put(dataSnapshot.getKey(), dataSnapshot.getValue(String.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                patients.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        Firebase ref2 = new Firebase(dataURI + "small/schedule");
        ref2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("adding " + dataSnapshot.getKey());
                schedule.put(dataSnapshot.getKey(), dataSnapshot.getValue(String.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                System.out.println("changed " + dataSnapshot.getKey());
                schedule.put(dataSnapshot.getKey(), dataSnapshot.getValue(String.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                System.out.println("remove " + dataSnapshot.getKey());
                schedule.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Firebase ref3 = new Firebase(dataURI + "small/alerts");
        ref3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                alerts.put(dataSnapshot.getKey(), dataSnapshot.getValue(String.class));
                if(dataSnapshot.getValue(Boolean.class)) {
                    Firebase ref = new Firebase(dataURI + "small/alerts");
                    ref.child(dataSnapshot.getKey()).setValue(false);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                alerts.put(dataSnapshot.getKey(), dataSnapshot.getValue(String.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                alerts.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public String getUserID() { return userID; }
    public List<String> getPatients() { return new ArrayList(patients.values()); }
    public List<String> getSchedule() {
        System.out.println("ScheduleID " + scheduleID);
        List<String> ret = new ArrayList<>();
        if (scheduleID.equals("")) {
            ret.addAll(schedule.keySet());
            return ret;
        }
        for (Map.Entry<String, String> ent : schedule.entrySet()) {
            System.out.println(ent.getValue());
            if(scheduleID.equals(ent.getValue())) {
                ret.add(ent.getKey());
            }
        }
        return ret;
    }
    public List<String> getAlerts() { return new ArrayList(alerts.keySet());}
    public boolean getIsLoggedIn() { return isLoggedIn; }
    public boolean getIsDoctor() { return isDoctor; }
    public String getScheduleID() { return scheduleID; }
    public void setScheduleID(String newid) {
        if (newid.equals("") || patients.containsValue(newid)) {
            scheduleID = newid;
        }
    }
    public String sanitizeKey(String key) {
        String ret = key.substring(0, Math.min(key.length(), 767));
        return ret.replaceAll("\\.", "_").toLowerCase();
    }

    public void addPatient(String name, String email) {
        Firebase ref = new Firebase(dataURI + "small/patients/");
        ref.child(sanitizeKey(name)).setValue(email);
    }

    public void addAlert(String alert, String name) {
        Firebase ref = new Firebase(dataURI + "small/alerts");
        ref.child(sanitizeKey(alert + " - " + name)).setValue(true);
    }

    public void setLogin(String uid, boolean isdoc) {
        isLoggedIn = true;
        userID = uid;
        isDoctor = isdoc;
        if(!isDoctor) {
            System.out.println(scheduleID);
            scheduleID = userID;
        }
    }

    private static DataHandler handler = new DataHandler();
    public static DataHandler getInstance() {
        return handler;
    }

}
