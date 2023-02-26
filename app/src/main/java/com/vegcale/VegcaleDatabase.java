/*
 * Copyright 2020 Vegetable Calendar Project
 ********************************************
 *    Editor    *    Date    *    Reason    *
 *------------------------------------------*
 *    Kisho     * 2020/9/30  *    Launch    *
 *------------------------------------------*
 */

package com.vegcale;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VegcaleDatabase {
    private final FirebaseDatabase vegcaleDatabase;
    private final ValueEventListener mValueEventListener;
    private final String plantsInfoRootPath = "plants_info/";

    public VegcaleDatabase(@NonNull ValueEventListener mValueEventListener) {
        this.mValueEventListener = mValueEventListener;
        final String vegcaleDatabaseUrl =
                "https://vegcale-app-default-rtdb.asia-southeast1.firebasedatabase.app/";

        vegcaleDatabase = FirebaseDatabase.getInstance(vegcaleDatabaseUrl);
    }

    public void fetchPlantsData() {
        String plantsPath = plantsInfoRootPath + "plants/";

        DatabaseReference plantsReference =
                vegcaleDatabase.getReference(plantsPath);
//        plantsReference.limitToFirst(10);
        plantsReference.addListenerForSingleValueEvent(mValueEventListener);
    }
}