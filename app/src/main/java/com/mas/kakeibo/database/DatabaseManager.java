package com.mas.kakeibo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sow.m on 2018/11/08.
 */
public class DatabaseManager {
    private static DatabaseManager mInstance;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized DatabaseManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseManager(context);
        }

        return mInstance;
    }

    private DatabaseManager(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public boolean addEntry(String productName,
                            String productCategory,
                            int price,
                            String shopName,
                            String shopAddress,
                            String purchaseDate,
                            String imageLocation) {

        final ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_PRODUCT_NAME, productName);
        values.put(DatabaseHelper.COL_PRODUCT_CATEGORY, productCategory);
        values.put(DatabaseHelper.COL_PRODUCT_PRICE, price);
        values.put(DatabaseHelper.COL_SHOP_NAME, shopName);
        values.put(DatabaseHelper.COL_SHOP_ADDRESS, shopAddress);
        values.put(DatabaseHelper.COL_PURCHASE_DATE, purchaseDate);
        values.put(DatabaseHelper.COL_PIC_LOCATION, imageLocation);

        return mDatabase.insert(DatabaseHelper.DB_TABLE, null, values) != -1;
    }

    public Cursor retrieveAllEntries() {
        return mDatabase.rawQuery("SELECT * FROM " + DatabaseHelper.DB_TABLE, null);
    }
}
