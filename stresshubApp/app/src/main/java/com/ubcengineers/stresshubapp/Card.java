package com.ubcengineers.stresshubapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import static android.support.constraint.Constraints.TAG;

@Layout(R.layout.card_view)
public class Card extends Activity{

    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    @View(R.id.titleText)
    private TextView mTitleTextView;

    @View(R.id.descriptionText)
    private TextView mDescriptionTextView;

    @View(R.id.inputText)
    private EditText mTextInput;

    @View(R.id.inputEmail)
    private EditText mTextEmail;

    @View(R.id.card)
    private CardView mCardView;

    private String mTitleText;
    private String mDescription;
    private Boolean mIsWorthPoints;
    private Boolean mIsTextPrompt;
    private int lastState = 0;
    private static int cardCount = 0;
    private static int currentScore = 0;

    public Card(Context context, SwipePlaceHolderView swipeView, String titleText, String description,
                Boolean isWorthPoints) {
        mContext = context;
        mSwipeView = swipeView;
        mTitleText = titleText;
        mDescription = description;
        mIsTextPrompt = false;
        mIsWorthPoints = isWorthPoints;
    }

    public Card(Context context, SwipePlaceHolderView swipeView, String titleText) {
        mContext = context;
        mSwipeView = swipeView;
        mTitleText = titleText;
        mDescription = "";
        mIsTextPrompt = true;
        mIsWorthPoints = false;
    }

    @Resolve
    private void onResolved(){
        Glide.with(mContext);
        Log.d(TAG, "onResolved: changed title and description to" + mTitleText);

        if(!mIsTextPrompt) {
            mTextInput.setVisibility(android.view.View.INVISIBLE);
            mTextEmail.setVisibility(android.view.View.INVISIBLE);
        }
        mTitleTextView.setText(mTitleText);
        mDescriptionTextView.setText(mDescription);
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        if(mIsWorthPoints) {
            cardCount++;
            checkCardCount();
        }
        else if (mIsTextPrompt) {
            SurveySender.setName(mTextInput.getText().toString());
            SurveySender.setEmail(mTextEmail.getText().toString());
        }
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
        if(!mIsTextPrompt) {
            if (lastState > 0) {
                ColorDrawable[] color = {new ColorDrawable(Color.argb(255, 150, 255, 150)),
                        new ColorDrawable(Color.WHITE)};
                TransitionDrawable trans = new TransitionDrawable(color);
                mCardView.setBackground(trans);
                trans.startTransition(100);
            } else if (lastState < 0) {
                ColorDrawable[] color = {new ColorDrawable(Color.argb(255, 255, 150, 150)),
                        new ColorDrawable(Color.WHITE)};
                TransitionDrawable trans = new TransitionDrawable(color);
                mCardView.setBackground(trans);
                trans.startTransition(100);
            } else {
                ColorDrawable[] color = {new ColorDrawable(Color.WHITE),
                        new ColorDrawable(Color.WHITE)};
                TransitionDrawable trans = new TransitionDrawable(color);
                mCardView.setBackground(trans);
                trans.startTransition(100);
            }
            lastState = 0;
        }
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
        if(mIsWorthPoints) {
            cardCount++;
            currentScore += 10;
            checkCardCount();
        }
        else if (mIsTextPrompt) {
            SurveySender.setName(mTextInput.getText().toString());
            SurveySender.setEmail(mTextEmail.getText().toString());
        }
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
        if(!mIsTextPrompt) {
            if (lastState < 0) {
                ColorDrawable[] color = {new ColorDrawable(Color.argb(255, 255, 150, 150)),
                        new ColorDrawable(Color.argb(255, 150, 255, 150))};
                TransitionDrawable trans = new TransitionDrawable(color);
                mCardView.setBackground(trans);
                trans.startTransition(100);
            } else if (lastState == 0) {
                ColorDrawable[] color = {new ColorDrawable(Color.WHITE),
                        new ColorDrawable(Color.argb(255, 150, 255, 150))};
                TransitionDrawable trans = new TransitionDrawable(color);
                mCardView.setBackground(trans);
                trans.startTransition(100);
            }
            lastState = 1;
        }
    }

    @SwipeOutState
    private void onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState");
        if (!mIsTextPrompt) {
            if (lastState > 0) {
                ColorDrawable[] color = {new ColorDrawable(Color.argb(255, 150, 255, 150)),
                        new ColorDrawable(Color.argb(255, 255, 150, 150))};
                TransitionDrawable trans = new TransitionDrawable(color);
                mCardView.setBackground(trans);
                trans.startTransition(100);
            } else if (lastState == 0) {
                ColorDrawable[] color = {new ColorDrawable(Color.WHITE),
                        new ColorDrawable(Color.argb(255, 255, 150, 150))};
                TransitionDrawable trans = new TransitionDrawable(color);
                mCardView.setBackground(trans);
                trans.startTransition(100);
            }
            lastState = -1;
        }
    }

    private void checkCardCount() {
        if (cardCount == 4) {
            SurveySender.send(currentScore);
        }
    }
}

