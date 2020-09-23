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

import com.vegcale.R;
import com.vegcale.data.VegetableTipContract.VegetableTipEntry;
import static com.vegcale.VegetableConstant.STRAWBERRIES;
import static com.vegcale.VegetableConstant.WHERE_TO_PLACE_CHERRY_TOMATOES_FLOWER_POT;

public class VegetableTipDbHelper extends SQLiteOpenHelper {

    /** Name of the database file */
    public static final int DATABASE_VERSION = 1;

    /** Database version. If we change the database schema, increase this number */
    public static final String DATABASE_NAME = "vegetable_tips.db";

    /** Context */
    private Context mContext;

    public VegetableTipDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
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
                + VegetableTipEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, "
                + VegetableTipEntry.COLUMN_IMAGE + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_VEGETABLES_TABLE);

        prepopulateVegetableTipsData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion1) { }
    
    public void prepopulateVegetableTipsData(SQLiteDatabase sqLiteDatabase) {
        // information of cherry tomatoes
        ContentValues tips = new ContentValues();
        tips.put(VegetableTipEntry.COLUMN_TITLE, WHERE_TO_PLACE_CHERRY_TOMATOES_FLOWER_POT);
        tips.put(VegetableTipEntry.COLUMN_DESCRIPTION, "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ");
        tips.put(VegetableTipEntry.COLUMN_IMAGE, mContext.getResources().getResourceEntryName(R.drawable.ic_cherry_tomato_circle));
        sqLiteDatabase.insert(VegetableTipEntry.TABLE_NAME, null, tips);

        ContentValues strawberry = new ContentValues();
        strawberry.put(VegetableTipEntry.COLUMN_TITLE, STRAWBERRIES);
        strawberry.put(VegetableTipEntry.COLUMN_DESCRIPTION, "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ");
        strawberry.put(VegetableTipEntry.COLUMN_IMAGE, mContext.getResources().getResourceEntryName(R.drawable.ic_strawberry_circle));
        sqLiteDatabase.insert(VegetableTipEntry.TABLE_NAME, null, strawberry);
    }
}
