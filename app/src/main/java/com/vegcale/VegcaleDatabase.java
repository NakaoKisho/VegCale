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
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class VegcaleDatabase {
    private final FirebaseDatabase vegcaleDatabase;
    private final String plantsInfoRootPath = "plants_info/";

    public VegcaleDatabase() {
        final String vegcaleDatabaseUrl =
                "https://vegcale-app-default-rtdb.asia-southeast1.firebasedatabase.app/";

        vegcaleDatabase = FirebaseDatabase.getInstance(vegcaleDatabaseUrl);
    }

    public void fetchPlantsData(
            int nextItemCount,
            boolean isFirstDataFetch,
            @Nullable String startAfterValue,
            @NonNull ValueEventListener mValueEventListener
    ) {
        if (isFirstDataFetch && startAfterValue != null) {
            throw new Error("If isFirstDataFetch is true, startAfterValue should not be null,");
        }
        if (!isFirstDataFetch && startAfterValue == null) {
            throw new Error("If isFirstDataFetch is false, startAfterValue should be null,");
        }

        String plantsPath = plantsInfoRootPath + "plants/";

        DatabaseReference plantsReference =
                vegcaleDatabase.getReference(plantsPath);
        Query dataFetchQuery = plantsReference.orderByKey();

        dataFetchQuery = dataFetchQuery.limitToFirst(nextItemCount);

        if (!isFirstDataFetch) {
            dataFetchQuery = dataFetchQuery.startAfter(startAfterValue);
        }

        dataFetchQuery.addListenerForSingleValueEvent(mValueEventListener);
    }

    public void getCount(OnCompleteListener<DataSnapshot> mOnCompleteListener) {
        String itemCountPath = plantsInfoRootPath + "item_count";

        DatabaseReference plantsReference =
                vegcaleDatabase.getReference(itemCountPath);
        plantsReference.get().addOnCompleteListener(mOnCompleteListener);
    }
}