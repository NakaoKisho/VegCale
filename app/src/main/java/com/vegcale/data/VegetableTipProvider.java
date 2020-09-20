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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vegcale.data.VegetableTipContract.VegetableTipEntry;

public class VegetableTipProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = VegetableTipProvider.class.getSimpleName();

    /** Database helper object */
    private VegetableTipDbHelper mDbHelper;

    /** URI matcher code for the content URI for the vegetable table */
    private static final int TIPS = 200;

    /** URI matcher code for the content URI for a single VEGETABLE in the vegetable table */
    private static final int TIPS_MONTH = 201;

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
        sUriMatcher.addURI(VegetableTipContract.CONTENT_AUTHORITY, VegetableTipContract.PATH_VEGETABLES_TIPS, TIPS);
        sUriMatcher.addURI(VegetableTipContract.CONTENT_AUTHORITY, VegetableTipContract.PATH_VEGETABLES_TIPS + "/#", TIPS_MONTH);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new VegetableTipDbHelper(getContext());
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
            case TIPS:
            case TIPS_MONTH:
                // For the TIPS and TIPS_MONTH code, query the vegetables table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the Vegetables table.
                cursor = database.query(
                        VegetableTipEntry.TABLE_NAME,  // The table to query
                        projection,                 // The array of columns to return (pass null to get all)
                        selection,                  // The columns for the WHERE clause
                        selectionArgs,              // The values for the WHERE clause
                        null,              // don't group the rows
                        null,               // don't filter by row groups
                        sortOrder                   // The sort order
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
            case TIPS:
                return VegetableTipEntry.CONTENT_LIST_TYPE;
            case TIPS_MONTH:
                return VegetableTipEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TIPS:
            case TIPS_MONTH:
                // Get readable database
                SQLiteDatabase database = mDbHelper.getWritableDatabase();

                // Insert the new pet with the given values
                long id = database.insert(VegetableTipEntry.TABLE_NAME, null, contentValues);

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
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TIPS:
            case TIPS_MONTH:
                rowsDeleted = database.delete(VegetableTipEntry.TABLE_NAME, selection, selectionArgs);
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
            case TIPS:
            case TIPS_MONTH:
                // Get readable database
                SQLiteDatabase database = mDbHelper.getWritableDatabase();

                // Perform the update on the database and get the number of rows affected
                int rowsUpdated = database.update(VegetableTipEntry.TABLE_NAME, contentValues, selection, selectionArgs);

                // If 1 or more rows were updated, then notify all listeners that the data at the
                // given URI has changed
                if (rowsUpdated != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return rowsUpdated;
                } else {
                    return database.update(VegetableTipEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                }
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }
}
