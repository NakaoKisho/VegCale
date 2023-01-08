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
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeGoogleMobileAdsSdk();

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int bottomNavigationButtonId = item.getItemId();
//                System.out.println(findViewById(R.id.bottom_navigation_action_1).getClass());
//                Menu bottomNavigationButtonMenu = bottomNavigationView.getMenu();
//                getMenuInflater().inflate(R.menu.home_bottom_navigation, bottomNavigationButtonMenu);
//                MenuItem action1MenuItem = findViewById(R.id.bottom_navigation_action_1);
//                MenuItem action2MenuItem = findViewById(R.id.bottom_navigation_action_2);
//                MenuItem action3MenuItem = findViewById(R.id.bottom_navigation_action_3);
                NavDirections action = null;

                if (bottomNavigationButtonId == R.id.bottom_navigation_action_1) {
                    action = HomeFragmentDirections.actionHomeFragmentToCalendarFragment();
                } else if (bottomNavigationButtonId == R.id.bottom_navigation_action_2) {
                    action = HomeFragmentDirections.actionHomeFragmentToCalendarFragment();
                } else if (bottomNavigationButtonId == R.id.bottom_navigation_action_3) {
                    action = HomeFragmentDirections.actionHomeFragmentToTipFragment();
                }
//                if (bottomNavigationButtonId == action1MenuItem.getItemId()) {
//                    action = HomeFragmentDirections.actionHomeFragmentToCalendarFragment();
//                } else if (bottomNavigationButtonId == action2MenuItem.getItemId()) {
//                    action = HomeFragmentDirections.actionHomeFragmentToCalendarFragment();
//                } else if (bottomNavigationButtonId == action3MenuItem.getItemId()) {
//                    action = HomeFragmentDirections.actionHomeFragmentToTipFragment();
//                }

                if (action == null) {
                    throw new NullPointerException();
                }

                NavController navController = test();
                navController.navigate(action);

                return false;
            }
        });
    }

    private void initializeGoogleMobileAdsSdk() {
        MobileAds.initialize(this, initializationStatus -> { });
    }

    private NavController test() {
        NavHostFragment navigationContainer =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_container);

        if (navigationContainer == null) {
            throw new NullPointerException();
        }

        return navigationContainer.getNavController();
    }
}