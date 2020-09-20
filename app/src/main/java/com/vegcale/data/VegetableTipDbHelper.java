/*
 * Copyright 2020 Vegetable Calendar Project
 ********************************************
 *    Editor    *    Date    *    Reason    *
 *------------------------------------------*
 *    Kisho     * 2020/9/30  *    Launch    *
 *------------------------------------------*
 */

package com.vegcale.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vegcale.data.VegetableTipContract.VegetableTipEntry;
import static com.vegcale.VegetableConstant.CHERRY_TOMATOES;
import static com.vegcale.VegetableConstant.SEEDING;
import static com.vegcale.VegetableConstant.SPROUT_IN_A_WEEK;

public class VegetableTipDbHelper extends SQLiteOpenHelper {

    /** Name of the database file */
    public static final int DATABASE_VERSION = 1;

    /** Database version. If we change the database schema, increase this number */
    public static final String DATABASE_NAME = "vegetable_tips.db";

    public VegetableTipDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param sqLiteDatabase The database.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // CREATE TABLE vegetableTips (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL,
        // description1 TEXT NOT NULL, description2 TEXT NOT NULL);
        // Create a String that contains the SQL statement to create the vegetableTips table
        String SQL_CREATE_VEGETABLES_TABLE = "CREATE TABLE " + VegetableTipEntry.TABLE_NAME + "("
                + VegetableTipEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + VegetableTipEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + VegetableTipEntry.COLUMN_DESCRIPTION1 + " TEXT NOT NULL, "
                + VegetableTipEntry.COLUMN_DESCRIPTION2 + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_VEGETABLES_TABLE);

        prepopulateVegetableTipsData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion1) { }
    
    public void prepopulateVegetableTipsData(SQLiteDatabase sqLiteDatabase) {
        // information of cherry tomatoes
        ContentValues tips = new ContentValues();
        tips.put(VegetableTipEntry.COLUMN_TITLE, CHERRY_TOMATOES);
        tips.put(VegetableTipEntry.COLUMN_DESCRIPTION1, SEEDING);
        tips.put(VegetableTipEntry.COLUMN_DESCRIPTION2, SPROUT_IN_A_WEEK);
        sqLiteDatabase.insert(VegetableTipEntry.TABLE_NAME, null, tips);
    }
}
