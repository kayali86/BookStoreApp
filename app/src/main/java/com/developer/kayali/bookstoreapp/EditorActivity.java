package com.developer.kayali.bookstoreapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.kayali.bookstoreapp.data.BookContract.BookEntry;
import com.developer.kayali.bookstoreapp.data.BookDbHelper;

public class EditorActivity extends AppCompatActivity {
    // Declare EditText views
    private EditText bookNameEditText;
    private EditText bookPriceEditText;
    private EditText quantityEditText;
    private EditText supplierNameEditText;
    private EditText supplierPhoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        // Initialize EditText views by id
        bookNameEditText = (EditText) findViewById(R.id.edit_text_book_name);
        bookPriceEditText = (EditText) findViewById(R.id.edit_text_price);
        quantityEditText = (EditText) findViewById(R.id.edit_text_quantity);
        supplierNameEditText = (EditText) findViewById(R.id.edit_text_supplier_name);
        supplierPhoneEditText = (EditText) findViewById(R.id.edit_text_supplier_phone);
    }

    // Add new book to Database
    private void addBook() {
        // Verify if the required fields is empty
        if (!TextUtils.isEmpty(bookNameEditText.getText().toString().trim()) &&
                bookPriceEditText.getText().length() != 0 && quantityEditText.getText().length() != 0) {
            // Get values from EditText Views
            String bookNameString = bookNameEditText.getText().toString().trim();
            float bookPrice = Float.parseFloat(bookPriceEditText.getText().toString().trim());
            int quantity = Integer.parseInt(quantityEditText.getText().toString().trim());
            String supplierNameString = supplierNameEditText.getText().toString().trim();
            // Replace empty supplier name with "unknown" word
            if (TextUtils.isEmpty(supplierNameString)) {
                supplierNameString = getString(R.string.unknown);
            }
            int supplierPhoneNumber;
            // To avoid Number format exception when parsing text to integer and replace it by 0 when it's value = null
            if (supplierPhoneEditText.getText().length() != 0) {
                supplierPhoneNumber = Integer.parseInt(supplierPhoneEditText.getText().toString().trim());
            } else {
                supplierPhoneNumber = 0;
            }
            // Declare a new ContentValue to insert a new row in Database
            ContentValues values = new ContentValues();
            values.put(BookEntry.COLUMN_BOOK_NAME, bookNameString);
            values.put(BookEntry.COLUMN_BOOK_PRICE, bookPrice);
            values.put(BookEntry.COLUMN_QUANTITY, quantity);
            values.put(BookEntry.COLUMN_SUPPLIER_NAME, supplierNameString);
            values.put(BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER, supplierPhoneNumber);

            BookDbHelper mDbHelper = new BookDbHelper(this);
            // Reference to Writable Database
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            // Store Row id to display it in Toast message
            long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);

            // Show a toast message depending on whether or not the insertion was successful
            if (newRowId == -1) {
                // If the row ID is -1, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.saving_error), Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast with the row ID.
                Toast.makeText(this, getString(R.string.saved_with_id) + newRowId, Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            // If the required fields is empty
            Toast.makeText(this, getString(R.string.required_fields), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu options from the res/menu/menu_editor.xml file.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // If save button clicked
            case R.id.action_save:
                addBook();
                return true;

            // If home button clicked
            case android.R.id.home:
                // Navigate back to parent activity (MainActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
