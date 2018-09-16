package com.ubcengineers.stresshubapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class SurveySender {
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;
    private static String mName;
    private static String mEmail;
    private static int mTemperatureScore = 0;

    public static void send(int score) {

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("processed");

        String key = myRef.push().getKey();

        myRef.child(key).child("initials").setValue(mName);
        myRef.child(key).child("score").setValue(score + mTemperatureScore);
        myRef.child(key).child("phone").setValue(mEmail);
    }

    public static void setName(String name) {
        mName = name;
    }

    public static void setEmail(String email) {
        mEmail = email;
    }

    public static void setTemperatureScore(int temperature) {
        mTemperatureScore = temperature;
    }
}
