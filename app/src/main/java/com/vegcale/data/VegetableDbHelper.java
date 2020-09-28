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
import com.vegcale.data.VegetableContract.VegetableEntry;
import static com.vegcale.VegetableConstant.ALL_WEEK_OF_MONTH;
import static com.vegcale.VegetableConstant.CHERRY_TOMATOES;
import static com.vegcale.VegetableConstant.FIRST_WEEK_OF_MOTH;
import static com.vegcale.VegetableConstant.NOT_DEFINED;
import static com.vegcale.VegetableConstant.PLANTING;
import static com.vegcale.VegetableConstant.RED_PEPPERS;
import static com.vegcale.VegetableConstant.SEEDING;
import static com.vegcale.VegetableConstant.SPROUT_IN_A_WEEK;
import static com.vegcale.VegetableConstant.SPROUT_IN_ONE_TO_TWO_WEEKS;
import static com.vegcale.VegetableConstant.STRAWBERRIES;

import static java.util.Calendar.APRIL;
import static java.util.Calendar.JUNE;
import static java.util.Calendar.MARCH;
import static java.util.Calendar.MAY;
import static java.util.Calendar.NOVEMBER;
import static java.util.Calendar.OCTOBER;
import static java.util.Calendar.SEPTEMBER;

public class VegetableDbHelper extends SQLiteOpenHelper {

    /** Name of the database file */
    public static final int DATABASE_VERSION = 1;

    /** Database version. If we change the database schema, increase this number */
    public static final String DATABASE_NAME = "vegetable.db";

    /** Context */
    private Context mContext;

    public VegetableDbHelper(Context context) {
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
        // CREATE TABLE vegetablePlantingSchedule (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL,
        // description1 TEXT NOT NULL, description2 TEXT NOT NULL, month INTEGER NOT NULL, week_of_month INTEGER NOT NULL);
        // Create a String that contains the SQL statement to create the vegetablePlantingSchedule table
        String SQL_CREATE_VEGETABLES_TABLE = "CREATE TABLE " + VegetableEntry.TABLE_NAME + "("
                + VegetableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + VegetableEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + VegetableEntry.COLUMN_DESCRIPTION1 + " TEXT NOT NULL, "
                + VegetableEntry.COLUMN_DESCRIPTION2 + " TEXT NOT NULL, "
                + VegetableEntry.COLUMN_MONTH + " INTEGER NOT NULL, "
                + VegetableEntry.COLUMN_WEEK_OF_MONTH + " INTEGER NOT NULL, "
                + VegetableEntry.COLUMN_IMAGE + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_VEGETABLES_TABLE);

        prepopulateVegetablesData(sqLiteDatabase);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param sqLiteDatabase The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) { }

    /**
     * Prepopulate data with vegetables data
     * @param sqLiteDatabase Database to prepopulate
     */
    public void prepopulateVegetablesData(SQLiteDatabase sqLiteDatabase) {
        // information of cherry tomatoes
        ContentValues cherryTomatoSeedingValues0 = new ContentValues();
        cherryTomatoSeedingValues0.put(VegetableEntry.COLUMN_NAME, CHERRY_TOMATOES);
        cherryTomatoSeedingValues0.put(VegetableEntry.COLUMN_DESCRIPTION1, SEEDING);
        cherryTomatoSeedingValues0.put(VegetableEntry.COLUMN_DESCRIPTION2, SPROUT_IN_A_WEEK);
        cherryTomatoSeedingValues0.put(VegetableEntry.COLUMN_MONTH, MARCH + 1);
        cherryTomatoSeedingValues0.put(VegetableEntry.COLUMN_WEEK_OF_MONTH, ALL_WEEK_OF_MONTH);
        cherryTomatoSeedingValues0.put(VegetableEntry.COLUMN_IMAGE, imageIdentifier(CHERRY_TOMATOES));
        sqLiteDatabase.insert(VegetableEntry.TABLE_NAME, null, cherryTomatoSeedingValues0);

        // information of cherry tomatoes
        ContentValues cherryTomatoSeedingValues1 = new ContentValues();
        cherryTomatoSeedingValues1.put(VegetableEntry.COLUMN_NAME, CHERRY_TOMATOES);
        cherryTomatoSeedingValues1.put(VegetableEntry.COLUMN_DESCRIPTION1, SEEDING);
        cherryTomatoSeedingValues1.put(VegetableEntry.COLUMN_DESCRIPTION2, SPROUT_IN_A_WEEK);
        cherryTomatoSeedingValues1.put(VegetableEntry.COLUMN_MONTH, APRIL + 1);
        cherryTomatoSeedingValues1.put(VegetableEntry.COLUMN_WEEK_OF_MONTH, ALL_WEEK_OF_MONTH);
        cherryTomatoSeedingValues1.put(VegetableEntry.COLUMN_IMAGE, imageIdentifier(CHERRY_TOMATOES));
        sqLiteDatabase.insert(VegetableEntry.TABLE_NAME, null, cherryTomatoSeedingValues1);

        // information of cherry tomatoes
        ContentValues cherryTomatoPlantingValues0 = new ContentValues();
        cherryTomatoPlantingValues0.put(VegetableEntry.COLUMN_NAME, CHERRY_TOMATOES);
        cherryTomatoPlantingValues0.put(VegetableEntry.COLUMN_DESCRIPTION1, PLANTING);
        cherryTomatoPlantingValues0.put(VegetableEntry.COLUMN_DESCRIPTION2, NOT_DEFINED);
        cherryTomatoPlantingValues0.put(VegetableEntry.COLUMN_MONTH, MAY + 1);
        cherryTomatoPlantingValues0.put(VegetableEntry.COLUMN_WEEK_OF_MONTH, FIRST_WEEK_OF_MOTH);
        cherryTomatoPlantingValues0.put(VegetableEntry.COLUMN_IMAGE, imageIdentifier(CHERRY_TOMATOES));
        sqLiteDatabase.insert(VegetableEntry.TABLE_NAME, null, cherryTomatoPlantingValues0);


        // information of strawberries
        ContentValues strawberriesSeedingValues0 = new ContentValues();
        strawberriesSeedingValues0.put(VegetableEntry.COLUMN_NAME, STRAWBERRIES);
        strawberriesSeedingValues0.put(VegetableEntry.COLUMN_DESCRIPTION1, SEEDING);
        strawberriesSeedingValues0.put(VegetableEntry.COLUMN_DESCRIPTION2, SPROUT_IN_ONE_TO_TWO_WEEKS);
        strawberriesSeedingValues0.put(VegetableEntry.COLUMN_MONTH, MARCH + 1);
        strawberriesSeedingValues0.put(VegetableEntry.COLUMN_WEEK_OF_MONTH, ALL_WEEK_OF_MONTH);
        strawberriesSeedingValues0.put(VegetableEntry.COLUMN_IMAGE, imageIdentifier(STRAWBERRIES));
        sqLiteDatabase.insert(VegetableEntry.TABLE_NAME, null, strawberriesSeedingValues0);

        // information of strawberries
        ContentValues strawberriesSeedingValues1 = new ContentValues();
        strawberriesSeedingValues1.put(VegetableEntry.COLUMN_NAME, STRAWBERRIES);
        strawberriesSeedingValues1.put(VegetableEntry.COLUMN_DESCRIPTION1, SEEDING);
        strawberriesSeedingValues1.put(VegetableEntry.COLUMN_DESCRIPTION2, SPROUT_IN_ONE_TO_TWO_WEEKS);
        strawberriesSeedingValues1.put(VegetableEntry.COLUMN_MONTH, APRIL + 1);
        strawberriesSeedingValues1.put(VegetableEntry.COLUMN_WEEK_OF_MONTH, ALL_WEEK_OF_MONTH);
        strawberriesSeedingValues1.put(VegetableEntry.COLUMN_IMAGE, imageIdentifier(STRAWBERRIES));
        sqLiteDatabase.insert(VegetableEntry.TABLE_NAME, null, strawberriesSeedingValues1);

        // information of strawberries
        ContentValues strawberriesSeedingValues2 = new ContentValues();
        strawberriesSeedingValues2.put(VegetableEntry.COLUMN_NAME, STRAWBERRIES);
        strawberriesSeedingValues2.put(VegetableEntry.COLUMN_DESCRIPTION1, SEEDING);
        strawberriesSeedingValues2.put(VegetableEntry.COLUMN_DESCRIPTION2, SPROUT_IN_ONE_TO_TWO_WEEKS);
        strawberriesSeedingValues2.put(VegetableEntry.COLUMN_MONTH, MAY + 1);
        strawberriesSeedingValues2.put(VegetableEntry.COLUMN_WEEK_OF_MONTH, ALL_WEEK_OF_MONTH);
        strawberriesSeedingValues2.put(VegetableEntry.COLUMN_IMAGE, imageIdentifier(STRAWBERRIES));
        sqLiteDatabase.insert(VegetableEntry.TABLE_NAME, null, strawberriesSeedingValues2);

        // information of strawberries
        ContentValues strawberriesSeedingValues3 = new ContentValues();
        strawberriesSeedingValues3.put(VegetableEntry.COLUMN_NAME, STRAWBERRIES);
        strawberriesSeedingValues3.put(VegetableEntry.COLUMN_DESCRIPTION1, SEEDING);
        strawberriesSeedingValues3.put(VegetableEntry.COLUMN_DESCRIPTION2, SPROUT_IN_ONE_TO_TWO_WEEKS);
        strawberriesSeedingValues3.put(VegetableEntry.COLUMN_MONTH, JUNE + 1);
        strawberriesSeedingValues3.put(VegetableEntry.COLUMN_WEEK_OF_MONTH, ALL_WEEK_OF_MONTH);
        strawberriesSeedingValues3.put(VegetableEntry.COLUMN_IMAGE, imageIdentifier(STRAWBERRIES));
        sqLiteDatabase.insert(VegetableEntry.TABLE_NAME, null, strawberriesSeedingValues3);

        // information of strawberries
        ContentValues strawberriesSeedingValues4 = new ContentValues();
        strawberriesSeedingValues4.put(VegetableEntry.COLUMN_NAME, STRAWBERRIES);
        strawberriesSeedingValues4.put(VegetableEntry.COLUMN_DESCRIPTION1, SEEDING);
        strawberriesSeedingValues4.put(VegetableEntry.COLUMN_DESCRIPTION2, SPROUT_IN_ONE_TO_TWO_WEEKS);
        strawberriesSeedingValues4.put(VegetableEntry.COLUMN_MONTH, SEPTEMBER + 1);
        strawberriesSeedingValues4.put(VegetableEntry.COLUMN_WEEK_OF_MONTH, ALL_WEEK_OF_MONTH);
        strawberriesSeedingValues4.put(VegetableEntry.COLUMN_IMAGE, imageIdentifier(STRAWBERRIES));
        sqLiteDatabase.insert(VegetableEntry.TABLE_NAME, null, strawberriesSeedingValues4);

        // information of strawberries
        ContentValues strawberriesSeedingValues5 = new ContentValues();
        strawberriesSeedingValues5.put(VegetableEntry.COLUMN_NAME, STRAWBERRIES);
        strawberriesSeedingValues5.put(VegetableEntry.COLUMN_DESCRIPTION1, SEEDING);
        strawberriesSeedingValues5.put(VegetableEntry.COLUMN_DESCRIPTION2, SPROUT_IN_ONE_TO_TWO_WEEKS);
        strawberriesSeedingValues5.put(VegetableEntry.COLUMN_MONTH, OCTOBER + 1);
        strawberriesSeedingValues5.put(VegetableEntry.COLUMN_WEEK_OF_MONTH, ALL_WEEK_OF_MONTH);
        strawberriesSeedingValues5.put(VegetableEntry.COLUMN_IMAGE, imageIdentifier(STRAWBERRIES));
        sqLiteDatabase.insert(VegetableEntry.TABLE_NAME, null, strawberriesSeedingValues5);

        // information of strawberries
        ContentValues strawberriesSeedingValues6 = new ContentValues();
        strawberriesSeedingValues6.put(VegetableEntry.COLUMN_NAME, STRAWBERRIES);
        strawberriesSeedingValues6.put(VegetableEntry.COLUMN_DESCRIPTION1, SEEDING);
        strawberriesSeedingValues6.put(VegetableEntry.COLUMN_DESCRIPTION2, SPROUT_IN_ONE_TO_TWO_WEEKS);
        strawberriesSeedingValues6.put(VegetableEntry.COLUMN_MONTH, NOVEMBER + 1);
        strawberriesSeedingValues6.put(VegetableEntry.COLUMN_WEEK_OF_MONTH, ALL_WEEK_OF_MONTH);
        strawberriesSeedingValues6.put(VegetableEntry.COLUMN_IMAGE, imageIdentifier(STRAWBERRIES));
        sqLiteDatabase.insert(VegetableEntry.TABLE_NAME, null, strawberriesSeedingValues6);


        // information of strawberries
        ContentValues RedPeppersSeedingValues0 = new ContentValues();
        RedPeppersSeedingValues0.put(VegetableEntry.COLUMN_NAME, RED_PEPPERS);
        RedPeppersSeedingValues0.put(VegetableEntry.COLUMN_DESCRIPTION1, SEEDING);
        RedPeppersSeedingValues0.put(VegetableEntry.COLUMN_DESCRIPTION2, SPROUT_IN_A_WEEK);
        RedPeppersSeedingValues0.put(VegetableEntry.COLUMN_MONTH, MARCH + 1);
        RedPeppersSeedingValues0.put(VegetableEntry.COLUMN_WEEK_OF_MONTH, ALL_WEEK_OF_MONTH);
        RedPeppersSeedingValues0.put(VegetableEntry.COLUMN_IMAGE, imageIdentifier(RED_PEPPERS));
        sqLiteDatabase.insert(VegetableEntry.TABLE_NAME, null, RedPeppersSeedingValues0);

        // information of strawberries
        ContentValues RedPeppersSeedingValues1 = new ContentValues();
        RedPeppersSeedingValues1.put(VegetableEntry.COLUMN_NAME, RED_PEPPERS);
        RedPeppersSeedingValues1.put(VegetableEntry.COLUMN_DESCRIPTION1, SEEDING);
        RedPeppersSeedingValues1.put(VegetableEntry.COLUMN_DESCRIPTION2, SPROUT_IN_A_WEEK);
        RedPeppersSeedingValues1.put(VegetableEntry.COLUMN_MONTH, APRIL + 1);
        RedPeppersSeedingValues1.put(VegetableEntry.COLUMN_WEEK_OF_MONTH, ALL_WEEK_OF_MONTH);
        RedPeppersSeedingValues1.put(VegetableEntry.COLUMN_IMAGE, imageIdentifier(RED_PEPPERS));
        sqLiteDatabase.insert(VegetableEntry.TABLE_NAME, null, RedPeppersSeedingValues1);
    }

    public String imageIdentifier(String id) {
        // Cherry tomatoes image
        String cherryTomatoImageId = mContext.getResources().getResourceEntryName(R.drawable.ic_cherry_tomatoes_circle);

        // Strawberry image
        String strawberryImageId = mContext.getResources().getResourceEntryName(R.drawable.ic_strawberries_circle);

        // Red pepper image
        String redPepperImageId = mContext.getResources().getResourceEntryName(R.drawable.ic_red_peppers_circle);

        //TODO: Set No_image image
        // No Image
        String noImageId = mContext.getResources().getResourceEntryName(R.drawable.ic_launcher_foreground);

        switch (id) {
            case CHERRY_TOMATOES:
                return cherryTomatoImageId;
            case STRAWBERRIES:
                return strawberryImageId;
            case RED_PEPPERS:
                return redPepperImageId;
            default:
                return noImageId;
        }
    }
}
