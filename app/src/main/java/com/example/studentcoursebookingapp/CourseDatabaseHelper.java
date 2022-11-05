package com.example.studentcoursebookingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CourseDatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "COURSE_TABLE";
    public static final String COLUMN_COURSE_ID = "ID";

    public static final String COLUMN_COURSE_CODE = "COURSE_CODE";
    public static final String COLUMN_COURSE_NAME = "COURSE_NAMETEXT";

    public CourseDatabaseHelper(@Nullable Context context) {
        super(context, "course.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_COURSE_CODE + " INTEGER, "
                + COLUMN_COURSE_NAME + " TEXT )";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // drop older table

        onCreate(sqLiteDatabase); // create table again
    }

    public boolean createCourse(CourseModel courseModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(String.valueOf(COLUMN_COURSE_CODE), courseModel.getCourseCode());
        cv.put(COLUMN_COURSE_NAME, courseModel.getCourseName());

        long insert = db.insert(TABLE_NAME, null, cv);
        return insert != -1;
    }

    public boolean deleteCourse(int courseID){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_COURSE_ID + " = '" + courseID + "'";

        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }

    public List<CourseModel> getCourses(){
        List<CourseModel> returnList = new ArrayList<>();

        // get data from database
        String queryString = "SELECT * FROM " + TABLE_NAME;
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

    public Cursor getCourseID(Editable courseCode, String courseName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " + COLUMN_COURSE_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_COURSE_CODE + " = '" + courseCode + "' AND " + COLUMN_COURSE_NAME + " = '" + courseName + "' ";

        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
