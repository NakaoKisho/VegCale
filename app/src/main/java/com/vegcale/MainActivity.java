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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLogo();
        setContentView(R.layout.activity_main);
        showGoogleMobileAds();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemReselectedListener(getBottomNavigationOnItemReselectedListener());
        bottomNavigationView.setOnItemSelectedListener(getBottomNavigationOnItemSelectedListener());
    }

    private void changeFragment(final int bottomNavigationButtonId) {
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

        assert directionFragment != null : "directionFragment must not be null.";
        fragmentTransaction.replace(R.id.navigation_container, directionFragment);
        fragmentTransaction.commit();
    }

    private NavigationBarView.OnItemReselectedListener getBottomNavigationOnItemReselectedListener() {
        return item -> {};
    }

    private NavigationBarView.OnItemSelectedListener getBottomNavigationOnItemSelectedListener() {
        return item -> {
            final int bottomNavigationButtonId = item.getItemId();
            changeFragment(bottomNavigationButtonId);

            return true;
        };
    }

    private void setLogo() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar == null) {
            return;
        }

        actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_logo_no_colored);
    }

    private void showGoogleMobileAds() {
        MobileAds.initialize(this, initializationStatus -> { });

        AdView googleAdView = findViewById(R.id.adView);
        AdRequest googleAdRequest = new AdRequest.Builder().build();
        googleAdView.loadAd(googleAdRequest);
    }
}