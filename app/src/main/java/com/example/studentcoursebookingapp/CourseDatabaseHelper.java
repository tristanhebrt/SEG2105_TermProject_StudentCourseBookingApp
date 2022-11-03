package com.example.studentcoursebookingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CourseDatabaseHelper extends SQLiteOpenHelper {

    public static final String COURSE_TABLE = "COURSE_TABLE";
    public static final String COLUMN_COURSE_ID = "COURSE_TABLE";
    public static final int COLUMN_COURSE_CODE = Integer.parseInt("COURSE_CODE");
    public static final String COLUMN_COURSE_NAME = "COURSE_NAME";

    public CourseDatabaseHelper(@Nullable Context context) {
        super(context, "course.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + COURSE_TABLE + " (" + COLUMN_COURSE_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COURSE_CODE + " INTEGER, " + COLUMN_COURSE_NAME + "TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createCourse(CourseModel courseModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(String.valueOf(COLUMN_COURSE_CODE), courseModel.getCourseCode());
        cv.put(COLUMN_COURSE_NAME, courseModel.getCourseName());

        long insert = db.insert(COURSE_TABLE, null, cv);
    }

    public boolean deleteCourse(CourseModel courseModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + COURSE_TABLE + " WHERE " + COLUMN_COURSE_ID + " = " + courseModel.getCourseId();

        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }

    public List<CourseModel> getCourses(){
        List<CourseModel> returnList = new ArrayList<>();

        // get data from database
        String queryString = "SELECT * FROM " + COURSE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int courseId = cursor.getInt(0);
                int courseCode = cursor.getInt(1);
                String courseName = cursor.getString(2);

                CourseModel newCourse = new CourseModel(courseId, courseCode, courseName);
                returnList.add(newCourse);

            }while (cursor.moveToNext());
        }else{
            // failed
        }
        cursor.close();
        db.close();
        return returnList;
    }
}
