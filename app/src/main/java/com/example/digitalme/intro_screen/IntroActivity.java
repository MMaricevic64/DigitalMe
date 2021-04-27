package com.example.digitalme.intro_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.digitalme.MainActivity;
import com.example.digitalme.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    ViewPager2 screenPager;
    OnboardingAdapter onboardingAdapter;
    TabLayout tabIndicators;
    Button btnGetStarted;
    Animation btn_get_started_animation;

    //List of intro screens
    List<ScreenItem> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make activity full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Hide action bar -> done in manifest
        //getSupportActionBar().hide();

        //When this activity is about to launch check SharedPreferences if it is first time!
        //If its not, launch next activity
        if(restorePrefData()){   openNextActivity();  }

        //Set view
        setContentView(R.layout.activity_intro);

        //Setup Screen items
        setupScreenItems();

        //Get Started button
        btnGetStarted = findViewById(R.id.btn_get_started);

        //Animation of button Get Started
        btn_get_started_animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_get_started_animation);

        //Setup adapter
        onboardingAdapter = new OnboardingAdapter(mList);

        //Setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        screenPager.setAdapter(onboardingAdapter);


        //Setup tab indicators
        tabIndicators = findViewById(R.id.tab_indicators);
        new TabLayoutMediator(tabIndicators, screenPager,
                (tab, position) ->{}
        ).attach();

        //Make button Get Started visible at last screen
        tabIndicators.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size()-1){
                    loadLastScreen();  //Make button visible and apply animation
                }
                else{
                    btnGetStarted.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Button Get Started listener
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save boolean variable to SharedPreferences so this activity will not start again
                saveFirstOpenState();
                //Launch next activity
                openNextActivity();
            }
        });
    }

    public void skipToNextActivity(View v){ //Method for Skip text view

        //Save boolean variable to SharedPreferences so this activity will not start again
        saveFirstOpenState();
        //Launch next activity
        openNextActivity();
    }

    private void openNextActivity() { //Open next activity and finish this one

        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivity);
        finish();

    }

    private boolean restorePrefData() { //Checks if this activity was opened once

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        return pref.getBoolean("isIntroOpened",false);

    }

    private void saveFirstOpenState() { //Save boolean variable to SharedPreferences so this activity will not launch again

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.apply();

    }

    private void loadLastScreen(){  //Make visible Get Started button at last screen

        btnGetStarted.setVisibility(View.VISIBLE);
        btnGetStarted.setAnimation(btn_get_started_animation);

    }

    private void setupScreenItems(){  //Fill Screen Item list

        mList.add(new ScreenItem("Personal Documents","Organize your personal documents all at one place!",R.drawable.documents));
        mList.add(new ScreenItem("Cards","Have preview of all your cards!",R.drawable.cards));
        mList.add(new ScreenItem("Notes","Keep up your events at one place!",R.drawable.notes));
        mList.add(new ScreenItem("Make profile","Make your profile so people can get your information for future business!",R.drawable.profile));

    }

}