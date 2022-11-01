package com.example.studentcoursebookingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String INSTRUCTOR_TABLE = "INSTRUCTOR_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_INSTRUCTOR_FIRSTNAME = "INSTRUCTOR_FIRSTNAME";
    public static final String COLUMN_INSTRUCTOR_LASTNAME = "INSTRUCTOR_LASTNAME";
    public static final String COLUMN_INSTRUCTOR_EMAIL = "INSTRUCTOR_EMAIL";
    public static final String COLUMN_INSTRUCTOR_USERNAME = "INSTRUCTOR_USERNAME";
    public static final String COLUMN_INSTRUCTOR_PASSWORD = "INSTRUCTOR_PASSWORD";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    // called when the database is first accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + INSTRUCTOR_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " " +COLUMN_INSTRUCTOR_FIRSTNAME + " TEXT, " + COLUMN_INSTRUCTOR_LASTNAME + " TEXT, " + COLUMN_INSTRUCTOR_EMAIL + " TEXT, " +
                " " +COLUMN_INSTRUCTOR_USERNAME + " TEXT, " + COLUMN_INSTRUCTOR_PASSWORD + " TEXT)";

        db.execSQL(createTableStatement);
    }


    // for when the database version changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
