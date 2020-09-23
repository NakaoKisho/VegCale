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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    /** ViewPager2 field to animate screen slides*/
    private ViewPager2 mViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Mobile Ads SDK
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the view pager2
        mViewPager2 = findViewById(R.id.pager);

        // Create an adapter
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(this);

        // Set the adapter onto the view pager2
        mViewPager2.setAdapter(adapter);

        // Find the tabLayout
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        // Link this TabLayout with the view pager2
        // This synchronizes the view pager2's position with the selected tab,
        // and the scroll position
        // 1. Instantiating a TabLayoutMediator
        // 2. Link this TabLayout with the view pager2 by calling attach()
        new TabLayoutMediator(tabLayout, mViewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("HOME");
                        break;
                    case 1:
                        tab.setText("CALENDAR");
                        break;
                    case 2:
                        tab.setText("TIPS");
                        break;
                }
            }
        }).attach();
    }

    @Override
    public void onBackPressed() {
        if (mViewPager2.getCurrentItem() == 0) {
            // If the user is currently looking at the Home, user goes back to android's home.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous section.
            mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() - 1);
        }
    }
}