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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VegcaleDatabase {
    private final DatabaseReference vegcaleDatabaseReference;
    private DataSnapshot plantsDataSnapShot;
    ValueEventListener abcd;

    public VegcaleDatabase(@NonNull String databasePath, ValueEventListener abcd) {
        final String vegcaleDatabaseUrl =
                "https://vegcale-app-default-rtdb.asia-southeast1.firebasedatabase.app/";

        FirebaseDatabase vegcaleDatabase = FirebaseDatabase.getInstance(vegcaleDatabaseUrl);
        vegcaleDatabaseReference = vegcaleDatabase.getReference(databasePath);
        this.abcd = abcd;
    }

    public DataSnapshot getOrangeValue() {
        vegcaleDatabaseReference.addListenerForSingleValueEvent(abcd);
//        vegcaleDatabaseReference.get().addOnCompleteListener(task -> {
//            if (!task.isSuccessful()) {
//                plantsDataSnapShot = null;
//                return;
//            }
//
//            plantsDataSnapShot = task.getResult();
//        });
//
        return plantsDataSnapShot;
    }
}