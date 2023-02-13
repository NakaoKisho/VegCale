/*
 * Copyright 2020 Vegetable Calendar Project
 ********************************************
 *    Editor    *    Date    *    Reason    *
 *------------------------------------------*
 *    Kisho     * 2020/9/30  *    Launch    *
 *------------------------------------------*
 */

package com.vegcale;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blackenOrWhitenStatusBarColor();
        showGoogleMobileAds();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemReselectedListener(item -> {
        });
        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    final int bottomNavigationButtonId = item.getItemId();
                    changeFragment(bottomNavigationButtonId);

                    return true;
                });
    }


    private void blackenOrWhitenStatusBarColor() {
        int nightModeFlags =
                getApplicationContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO) {
            window.setStatusBarColor(Color.WHITE);
            return;
        }

        window.setStatusBarColor(Color.BLACK);
    }

    private void changeFragment(final int bottomNavigationButtonId) {
        Fragment directionFragment = null;

        if (bottomNavigationButtonId == R.id.bottom_navigation_action_1) {
            directionFragment = new HomeFragment();
        } else if (bottomNavigationButtonId == R.id.bottom_navigation_action_2) {
            directionFragment = new CalendarFragment();
        } else if (bottomNavigationButtonId == R.id.bottom_navigation_action_3) {
            directionFragment = new TipFragment();
        }

        assert directionFragment != null : "directionFragment must not be null.";
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.navigation_container, directionFragment)
                .commit();
    }

    private void showGoogleMobileAds() {
        MobileAds.initialize(this, initializationStatus -> {
        });

        AdView googleAdView = findViewById(R.id.adView);
        AdRequest googleAdRequest = new AdRequest.Builder().build();
        googleAdView.loadAd(googleAdRequest);
    }
}