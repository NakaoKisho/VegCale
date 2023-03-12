package com.vegcale.utilities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.vegcale.R;

public class FragmentUtility {
    public static final String HomeFragmentTag = "HomeFragment";
    public static final String VegetableListFragmentTag = "VegetableListFragment";
    public static final String TipListFragmentTag = "TipListFragment";
    public static final String ItemDetailFragmentTag = "ItemDetailFragment";

    public enum SlideAnimation {
        LeftToRight,
        Nothing,
        RightToLeft,
    }
    private final FragmentActivity mFragmentActivity;

    public FragmentUtility(FragmentActivity mFragmentActivity) {
        this.mFragmentActivity = mFragmentActivity;
    }

    public void changeFragment(
            @NonNull Fragment directionFragment,
            @NonNull String fragmentTag,
            @NonNull SlideAnimation mSlideAnimation
    ) {
        FragmentTransaction mFragmentTransaction =
                mFragmentActivity
                        .getSupportFragmentManager()
                        .beginTransaction();

        switch (mSlideAnimation) {
            case LeftToRight:
                mFragmentTransaction.setCustomAnimations(
                        R.anim.slide_in_from_left,
                        R.anim.fade_out
                );
            case RightToLeft:
                mFragmentTransaction.setCustomAnimations(
                        R.anim.slide_in_from_right,
                        R.anim.fade_out
                );
            case Nothing:
        }

        mFragmentTransaction
                .setReorderingAllowed(true)
                .replace(R.id.navigation_container, directionFragment, fragmentTag)
                .commit();
    }

    public boolean isFragmentVisible(String fragmentTag) {
        Fragment mFragment =
                mFragmentActivity.getSupportFragmentManager().findFragmentByTag(fragmentTag);

        return mFragment != null && mFragment.isVisible();
    }
}
