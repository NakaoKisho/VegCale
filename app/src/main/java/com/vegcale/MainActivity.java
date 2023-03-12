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
import com.vegcale.utility.fragment.FragmentUtility;

public class MainActivity extends AppCompatActivity {
    private FragmentUtility mFragmentUtility;
    private HomeFragment mHomeFragment;
    private VegetableListFragment mVegetableListFragment;
    private VegetableArticleFragment mVegetableArticleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        blackenOrWhitenStatusBarColor();
        showGoogleMobileAds();

        mFragmentUtility = new FragmentUtility(this);
        mHomeFragment = new HomeFragment();
        mVegetableListFragment = new VegetableListFragment();
        mVegetableArticleFragment = new VegetableArticleFragment();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemReselectedListener(item -> {
            if (!mFragmentUtility.isFragmentVisible(FragmentUtility.ItemDetailFragmentTag)) return;

            mFragmentUtility.changeFragment(
                    mVegetableListFragment,
                    FragmentUtility.VegetableListFragmentTag,
                    FragmentUtility.SlideAnimation.LeftToRight
            );
        });
        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    final int bottomNavigationButtonId = item.getItemId();
                    transitionToFragmentAssociatedToButton(bottomNavigationButtonId);

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

    private void transitionToFragmentAssociatedToButton(final int bottomNavigationButtonId) {
        Fragment directionFragment = null;
        String fragmentTag = "";

        if (bottomNavigationButtonId == R.id.bottom_navigation_action_1) {
            directionFragment = mHomeFragment;
            fragmentTag = FragmentUtility.HomeFragmentTag;
        } else if (bottomNavigationButtonId == R.id.bottom_navigation_action_2) {
            directionFragment = mVegetableListFragment;
            fragmentTag = FragmentUtility.VegetableListFragmentTag;
        } else if (bottomNavigationButtonId == R.id.bottom_navigation_action_3) {
            directionFragment = mVegetableArticleFragment;
            fragmentTag = FragmentUtility.TipListFragmentTag;
        }

        assert directionFragment != null : "directionFragment must not be null.";
        mFragmentUtility.changeFragment(
                directionFragment,
                fragmentTag,
                FragmentUtility.SlideAnimation.Nothing
        );
    }

    private void showGoogleMobileAds() {
        MobileAds.initialize(this, initializationStatus -> {
        });

        AdView googleAdView = findViewById(R.id.adView);
        AdRequest googleAdRequest = new AdRequest.Builder().build();
        googleAdView.loadAd(googleAdRequest);
    }
}