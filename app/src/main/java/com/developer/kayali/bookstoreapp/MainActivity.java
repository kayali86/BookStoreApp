package com.developer.kayali.bookstoreapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kayali.bookstoreapp.data.BookContract.BookEntry;
import com.developer.kayali.bookstoreapp.data.BookDbHelper;

public class MainActivity extends AppCompatActivity {
    // Declare a new Object from BookDbHelper
    private BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        // BookDbHelper object initializing
        mDbHelper = new BookDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // To update Database info after adding or updating the data using EditorActivity
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        // Reference to Readable database
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Get Columns names from BookEntry class
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_BOOK_NAME,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_QUANTITY,
                BookEntry.COLUMN_SUPPLIER_NAME,
                BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER};
        // Getting info query from database using Cursor with specified projections
        Cursor cursor = db.query(
                BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        // Declare TextView to display info from Database
        TextView displayView = (TextView) findViewById(R.id.text_view_book);
        // We used try/finally statement to close the cursor in all cases
        try {
            // Display the count of rows in books table and names of columns
            displayView.setText("There are " + cursor.getCount() + " books in Books table.\n\n");
            displayView.append(BookEntry._ID + " - " +
                    BookEntry.COLUMN_BOOK_NAME + " - " +
                    BookEntry.COLUMN_BOOK_PRICE + " - " +
                    BookEntry.COLUMN_QUANTITY + " - " +
                    BookEntry.COLUMN_SUPPLIER_NAME + " - " +
                    BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER + "\n");
            // Get columnIndex of each column in books  table
            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int bookNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER);
            // Display books info on TextView using while loop
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentBookName = cursor.getString(bookNameColumnIndex);
                float currentPrice = cursor.getFloat(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                int currentSupplierPhoneNumber = cursor.getInt(supplierPhoneColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentBookName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplierName + " - " +
                        currentSupplierPhoneNumber));
            }
        } finally {
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Delete all the entries in books table
            case R.id.delete_all_books:
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                String SQL_DELETE_BOOKS_FROM_TABLE = "DELETE FROM " + BookEntry.TABLE_NAME + ";";
                db.execSQL(SQL_DELETE_BOOKS_FROM_TABLE);
                // Show a toast message when the entries are successfully deleted
                displayDatabaseInfo();
                Toast.makeText(this, "Books deleted", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}