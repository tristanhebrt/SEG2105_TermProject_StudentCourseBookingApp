package com.example.studentcoursebookingapp;

import static org.junit.Assert.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.core.database.sqlite.SQLiteDatabaseKt;

import org.junit.Test;

public class CourseDatabaseHelperTest extends SQLiteOpenHelper{
    Context appContext;
    CourseDatabaseHelper courseDatabaseHelper = new CourseDatabaseHelper(appContext);
    SQLiteDatabase db;


    public static final String TABLE_NAME = "COURSE_TABLE";
    public static final String COLUMN_COURSE_ID = "ID";

    public static final String COLUMN_COURSE_CODE = "COURSE_CODE";
    public static final String COLUMN_COURSE_NAME = "COURSE_NAMETEXT";

    public static final String COLUMN_COURSE_INSTRUCTOR = "COURSE_INSTRUCTOR";
    public static final String COLUMN_COURSE_INSTRUCTOR_ID = "COURSE_INSTRUCTOR_ID";

    public static final String COLUMN_COURSE_DAYS_AND_HOURS = "COURSE_DAYS_AND_HOURS";
    public static final String COLUMN_COURSE_DESCRIPTION = "COURSE_DESCRIPTION";

    public static final String COLUMN_COURSE_CAPACITY = "COURSE_CAPACITY";
    public static final String COLUMN_COURSE_ENROLLED = "COURSE_ENROLLED";

    public CourseDatabaseHelperTest(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + COLUMN_COURSE_CODE + " INTEGER, "
                + COLUMN_COURSE_NAME + " TEXT, "

                + COLUMN_COURSE_INSTRUCTOR + " TEXT, "
                + COLUMN_COURSE_INSTRUCTOR_ID + " INTEGER, "

                + COLUMN_COURSE_DAYS_AND_HOURS + " TEXT, "
                + COLUMN_COURSE_DESCRIPTION + " TEXT, "

                + COLUMN_COURSE_CAPACITY + " INTEGER, "
                + COLUMN_COURSE_ENROLLED + " INTEGER )";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //
        onCreate(sqLiteDatabase);
    }

    CourseModel cm1 = new CourseModel(3, "SEG2105",
            "Intro to Software Engineering", "Omar Badreddin",
            1234567, "Tuesdays 1-2:30, Wednesdays 2:30-4",
            "Principles of software engineering: Review of principles of object orientation. Object oriented analysis using UML. Frameworks and APIs. Introduction to the client-server architecture. Analysis, design and programming of simple servers and clients. Introduction to user interface technology.",
            30,4);

    @Test
    public void testCreateCourse() {
        boolean b = courseDatabaseHelper.createCourse(cm1);
        assertEquals(b, false);
    }

    @Test
    public void testDeleteCourse() {
        courseDatabaseHelper.deleteCourse(3);
        assertNull(courseDatabaseHelper.COLUMN_COURSE_ID);
    }

}