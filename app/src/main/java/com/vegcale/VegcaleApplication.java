package com.vegcale;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

public class VegcaleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
