/*
 * Copyright 2020 Vegetable Calendar Project
 ********************************************
 *    Editor    *    Date    *    Reason    *
 *------------------------------------------*
 *    Kisho     * 2020/9/30  *    Launch    *
 *------------------------------------------*
 */

package com.vegcale;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentPagerAdapter extends FragmentStateAdapter {

    /** Int field to show the number of pages */
    public static final int NUM_PAGES = 3;

    /** Constructor */
    public FragmentPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new CalendarFragment();
            case 2:
                return new TipFragment();
            default:
                throw new IllegalArgumentException(position + " doesn't match any cases");
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
