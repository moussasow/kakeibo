package com.mas.kakeibo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sow.m on 2018/11/08.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "KakeiboDatabase.db";

    // table
    public static final String DB_TABLE = "kakeibo_table";

    // Columns
    public static final String COL_ID = "_id";
    public static final String COL_PRODUCT_NAME = "product_name";
    public static final String COL_PRODUCT_CATEGORY = "product_category";
    public static final String COL_PRODUCT_PRICE = "product_price";
    public static final String COL_SHOP_NAME = "shop_name";
    public static final String COL_SHOP_ADDRESS = "shop_address";
    public static final String COL_PURCHASE_DATE = "purchase_date";
    public static final String COL_PIC_LOCATION = "picture_location";

    // columns

    // create table command
    private static final String CREATE_KAKEIBO_TABLE = "CREATE TABLE IF NOT EXISTS " + DB_TABLE +
            " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_PRODUCT_NAME        + " TEXT, " +
            COL_PRODUCT_CATEGORY    + " TEXT, " +
            COL_PRODUCT_PRICE       + " INTEGER, " +
            COL_SHOP_NAME           + " TEXT, " +
            COL_SHOP_ADDRESS        + " TEXT, " +
            COL_PURCHASE_DATE       + " TEXT, " +
            COL_PIC_LOCATION        + " TEXT" +
            ")";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_KAKEIBO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        // create new table
        onCreate(db);
    }
}
