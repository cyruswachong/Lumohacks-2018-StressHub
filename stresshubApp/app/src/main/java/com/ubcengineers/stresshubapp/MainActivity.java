package com.ubcengineers.stresshubapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private String mWelcomeMessage = "Welcome to your Daily Wellness Survey!";
    private String mWelcomeDetail = "To answer each question, swipe to the left to say NO and right to say YES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("unprocessed").child("score");

        mSwipeView = (SwipePlaceHolderView)findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor());

        QuestionsGenerator qg = new QuestionsGenerator();
        String[] questions = qg.getQuestions();

        mSwipeView.addView(new Card(mContext, mSwipeView, mWelcomeMessage, mWelcomeDetail, false));
        mSwipeView.addView(new Card(mContext, mSwipeView, "What's your name?"));
        mSwipeView.addView(new Card(mContext, mSwipeView, questions[0], "", true));
        mSwipeView.addView(new Card(mContext, mSwipeView, questions[1], "", true));
        mSwipeView.addView(new Card(mContext, mSwipeView, questions[2], "", true));
        mSwipeView.addView(new Card(mContext, mSwipeView, questions[3], "", true));



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                int result = Integer.parseInt(value);
                SurveySender.setTemperatureScore(result);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d(TAG, "onCancelled: Unable to read");
            }
        });
    }

}
