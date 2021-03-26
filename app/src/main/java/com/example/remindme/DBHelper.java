package com.example.remindme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "reminderslist.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_REMINDERS = "create table reminders (_id integer primary key autoincrement, " +
            "subject text, description text);";

    public DBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_REMINDERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(), "Upgrading database from " + oldVersion
                + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS reminders");
        onCreate(db);
    }
}
