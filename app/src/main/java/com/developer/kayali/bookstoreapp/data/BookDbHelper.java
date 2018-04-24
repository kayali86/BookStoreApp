package com.developer.kayali.bookstoreapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.developer.kayali.bookstoreapp.data.BookContract.BookEntry;

public class BookDbHelper extends SQLiteOpenHelper {
    // Store database name and version as constants
    public static final String DATABASE_NAME = "books";
    public static final int DATABASE_VERSION = 1;

    // Constructor - Context as parameter
    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Store the query that create books table as String
        String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + BookEntry.TABLE_NAME + " ("
                + BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BookEntry.COLUMN_BOOK_NAME + " TEXT NOT NULL, "
                + BookEntry.COLUMN_BOOK_PRICE + " FLOAT NOT NULL, "
                + BookEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, "
                + BookEntry.COLUMN_SUPPLIER_NAME + " TEXT, "
                + BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER + " INTEGER);";
        // Execute the query to create the table
        db.execSQL(SQL_CREATE_BOOKS_TABLE);
    }

    @Override
    // When the Database upgraded
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
