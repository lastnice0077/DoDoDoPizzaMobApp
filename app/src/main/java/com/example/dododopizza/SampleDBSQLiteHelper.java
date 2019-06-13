package com.example.dododopizza;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SampleDBSQLiteHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "login_database";
    public static final String TABLE_NAME = "loginDriver";
    public static final String KEY_ID = "_id";
    public static final String COLUMN_LOGIN= "login";
    public static final String COLUMN_PASSWORD= "password";
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_LOGIN + " TEXT, " +
            COLUMN_PASSWORD + " TEXT" + ")";
    public SampleDBSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}