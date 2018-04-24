package com.developer.kayali.bookstoreapp.data;

import android.provider.BaseColumns;

public class BookContract {
    // Constructor
    private BookContract() {
    }

    // Entry class for Books Table
    public static final class BookEntry implements BaseColumns {
        // Store Table and Columns names as constants
        public final static String TABLE_NAME = "books";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_BOOK_NAME = "book_name";
        public final static String COLUMN_BOOK_PRICE = "book_price";
        public final static String COLUMN_QUANTITY = "quantity";
        public final static String COLUMN_SUPPLIER_NAME = "supplier_name";
        public final static String COLUMN_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";
    }
}
