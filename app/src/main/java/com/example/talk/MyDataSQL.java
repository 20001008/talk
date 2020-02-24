package com.example.talk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataSQL extends SQLiteOpenHelper {
    public static final String SQLDATA_BOOK = "create table Book (" + "id integer primary key autoincrement," + "message text," + "num integer,"+"type integer)";
    public static final String SQLDATA_BOOKTAB="create table BookTab("+"id integer primary key autoincrement,"+"Tab_name text,"+"Tab_code integer)";
    public MyDataSQL(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLDATA_BOOK);
        db.execSQL(SQLDATA_BOOKTAB);
        Log.d("tag", "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("drop table if exists Book");
db.execSQL("drop table if exists BookTab");
onCreate(db);
    }
}
