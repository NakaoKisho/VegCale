/*
 * Copyright 2020 Vegetable Calendar Project
 ********************************************
 *    Editor    *    Date    *    Reason    *
 *------------------------------------------*
 *    Kisho     * 2020/9/30  *    Launch    *
 *------------------------------------------*
 */

package com.vegcale.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vegcale.data.VegetableContract.VegetableEntry;

public class VegetableProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = VegetableProvider.class.getSimpleName();

    /** Database helper object */
    private VegetableDbHelper mDbHelper;

    /** URI matcher code for the content URI for the vegetable table */
    private static final int VEGETABLES = 200;

    /** URI matcher code for the content URI for a single VEGETABLE in the vegetable table */
    private static final int VEGETABLES_MONTH = 201;

    /** Static for database error */
    private static final int DB_ERROR = -1;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.
        sUriMatcher.addURI(VegetableContract.CONTENT_AUTHORITY, VegetableContract.PATH_VEGETABLES, VEGETABLES);
        sUriMatcher.addURI(VegetableContract.CONTENT_AUTHORITY, VegetableContract.PATH_VEGETABLES + "/#", VEGETABLES_MONTH);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new VegetableDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case VEGETABLES:
                // For the VEGETABLES code, query the vegetables table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the Vegetables table.
                cursor = database.query(
                        VegetableEntry.TABLE_NAME,  // The table to query
                        projection,                 // The array of columns to return (pass null to get all)
                        selection,                  // The columns for the WHERE clause
                        selectionArgs,              // The values for the WHERE clause
                        null,              // don't group the rows
                        null,               // don't filter by row groups
                        sortOrder                   // The sort order
                );
                break;
            case VEGETABLES_MONTH:
                // For the VEGETABLES_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.Vegetables/Vegetables/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = VegetableEntry.COLUMN_MONTH + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the Vegetables table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(
                        VegetableEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case VEGETABLES:
                return VegetableEntry.CONTENT_LIST_TYPE;
            case VEGETABLES_MONTH:
                return VegetableEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case VEGETABLES:
                return insertVegetable(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a vegetables into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertVegetable(Uri uri, ContentValues values) {

        // Get name
        String name = values.getAsString(VegetableEntry.COLUMN_NAME);

        // Get description1
        String description1 = values.getAsString(VegetableEntry.COLUMN_DESCRIPTION1);

        // Get description2
        String description2 = values.getAsString(VegetableEntry.COLUMN_DESCRIPTION2);

        // Get month
        Integer month = values.getAsInteger(VegetableEntry.COLUMN_MONTH);

        // Get week of month
        Integer weekOfMonth = values.getAsInteger(VegetableEntry.COLUMN_WEEK_OF_MONTH);

        if(IsStringDataEmpty(name)
        || IsStringDataEmpty(description1) || IsStringDataEmpty(description2)
        || IsMonthInvalid(month) || IsWeekOfMonthInvalid(weekOfMonth)) {
            Toast.makeText(getContext(), "値に空文字があります", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Get readable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new pet with the given values
        long id = database.insert(VegetableEntry.TABLE_NAME, null, values);

        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == DB_ERROR) {
            Log.e(LOG_TAG, "DB追加に失敗しました " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case VEGETABLES:
                rowsDeleted = database.delete(VegetableEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case VEGETABLES_MONTH:
                selection = VegetableEntry.COLUMN_MONTH + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(VegetableEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case VEGETABLES:
                return updateVegetable(uri, contentValues, selection, selectionArgs);
            case VEGETABLES_MONTH:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = VegetableEntry.COLUMN_MONTH + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateVegetable(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    public int updateVegetable(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(VegetableEntry.COLUMN_NAME)) {
            // Get name
            String name = values.getAsString(VegetableEntry.COLUMN_NAME);

            if (IsStringDataEmpty(name)) {
                Toast.makeText(getContext(), "値に不備があります " + uri, Toast.LENGTH_SHORT).show();
                return DB_ERROR;
            }
        }

        if (values.containsKey(VegetableEntry.COLUMN_DESCRIPTION1)) {
            // Get title
            String description1 = values.getAsString(VegetableEntry.COLUMN_DESCRIPTION1);

            if (IsStringDataEmpty(description1)) {
                Toast.makeText(getContext(), "値に空文字があります " + uri, Toast.LENGTH_SHORT).show();
                return DB_ERROR;
            }
        }

        if (values.containsKey(VegetableEntry.COLUMN_DESCRIPTION2)) {
            // Get title
            String description2 = values.getAsString(VegetableEntry.COLUMN_DESCRIPTION2);

            if (IsStringDataEmpty(description2)) {
                Toast.makeText(getContext(), "値に空文字があります " + uri, Toast.LENGTH_SHORT).show();
                return DB_ERROR;
            }
        }

        if (values.containsKey(VegetableEntry.COLUMN_MONTH)) {
            // Get month
            Integer month = values.getAsInteger(VegetableEntry.COLUMN_MONTH);
            if (IsMonthInvalid(month)) {
                Toast.makeText(getContext(), "値に不正文字があります " + uri, Toast.LENGTH_SHORT).show();
                return DB_ERROR;
            }
        }

        if (values.containsKey(VegetableEntry.COLUMN_WEEK_OF_MONTH)) {
            // Get weekOfMonth
            Integer weekOfMonth = values.getAsInteger(VegetableEntry.COLUMN_WEEK_OF_MONTH);
            if (IsWeekOfMonthInvalid(weekOfMonth)) {
                Toast.makeText(getContext(), "値に不正文字があります " + uri, Toast.LENGTH_SHORT).show();
                return DB_ERROR;
            }
        }

        // No values check
        if (values.size() == 0) {
            return DB_ERROR;
        }

        // Get readable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(VegetableEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return rowsUpdated;
        } else {
            return database.update(VegetableEntry.TABLE_NAME, values, selection, selectionArgs);
        }
    }

    /**
     * Check whether the String data is empty.
     * If the data is empty, it returns true.
     * @param stringData string data have to be checked
     */
    public boolean IsStringDataEmpty(String stringData) {
        if(stringData.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * Check whether the int month data is invalid.
     * If the data is invalid, it returns true.
     * @param month int data have to be checked
     */
    public boolean IsMonthInvalid(int month) {
        if(month < 1 || month > 12) {
            return true;
        }
        return false;
    }

    /**
     * Check whether the int week of month data is invalid.
     * If the data is invalid, it returns true.
     * @param weekOfMonth int data have to be checked
     */
    public boolean IsWeekOfMonthInvalid(int weekOfMonth) {
        if(weekOfMonth < 0 || weekOfMonth > 5) {
            return true;
        }
        return false;
    }
}
