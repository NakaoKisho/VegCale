/*
 * Copyright 2020 Vegetable Calendar Project
 ********************************************
 *    Editor    *    Date    *    Reason    *
 *------------------------------------------*
 *    Kisho     * 2020/9/30  *    Launch    *
 *------------------------------------------*
 */

package com.vegcale;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeGoogleMobileAdsSdk();

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(getBottomNavigationOnItemSelectedListener());
    }

    private void initializeGoogleMobileAdsSdk() {
        MobileAds.initialize(this, initializationStatus -> { });
    }

    private NavigationBarView.OnItemSelectedListener getBottomNavigationOnItemSelectedListener() {
        return item -> {
            int bottomNavigationButtonId = item.getItemId();
            changeFragment(bottomNavigationButtonId);

            return true;
        };
    }

    private void changeFragment(int bottomNavigationButtonId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        Fragment directionFragment = null;

        if (bottomNavigationButtonId == R.id.bottom_navigation_action_1) {
            directionFragment = new HomeFragment();
        } else if (bottomNavigationButtonId == R.id.bottom_navigation_action_2) {
            directionFragment = new CalendarFragment();
        } else if (bottomNavigationButtonId == R.id.bottom_navigation_action_3) {
            directionFragment = new TipFragment();
        }

        if (directionFragment == null) {
            throw new NullPointerException();
        }

        fragmentTransaction.replace(R.id.navigation_container, directionFragment);
        fragmentTransaction.commit();
    }
}