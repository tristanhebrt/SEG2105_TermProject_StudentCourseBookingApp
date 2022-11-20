package com.example.studentcoursebookingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CourseDatabaseHelper extends SQLiteOpenHelper {

    MainActivity mainActivity = new MainActivity();

    public static final String TABLE_NAME = "COURSE_TABLE";
    public static final String COLUMN_COURSE_ID = "ID";

    public static final String COLUMN_COURSE_CODE = "COURSE_CODE";
    public static final String COLUMN_COURSE_NAME = "COURSE_NAMETEXT";

    public static final String COLUMN_COURSE_INSTRUCTOR = "COURSE_INSTRUCTOR";
    public static final String COLUMN_COURSE_INSTRUCTOR_ID = "COURSE_INSTRUCTOR_ID";

    public static final String COLUMN_COURSE_DAYS = "COURSE_DAYS";
    public static final String COLUMN_COURSE_HOURS = "COURSE_HOURS";
    public static final String COLUMN_COURSE_DESCRIPTION = "COURSE_DESCRIPTION";

    public static final String COLUMN_COURSE_CAPACITY = "COURSE_CAPACITY";
    public static final String COLUMN_COURSE_ENROLLED = "COURSE_ENROLLED";

    public CourseDatabaseHelper(@Nullable Context context) {
        super(context, "course.db", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + COLUMN_COURSE_CODE + " INTEGER, "
                + COLUMN_COURSE_NAME + " TEXT, "

                + COLUMN_COURSE_INSTRUCTOR + " TEXT, "
                + COLUMN_COURSE_INSTRUCTOR_ID + " INTEGER, "

                + COLUMN_COURSE_DAYS + " TEXT, "
                + COLUMN_COURSE_HOURS + " TEXT, "
                + COLUMN_COURSE_DESCRIPTION + " TEXT, "

                + COLUMN_COURSE_CAPACITY + " INTEGER, "
                + COLUMN_COURSE_ENROLLED + " INTEGER )";

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

    public List<CourseModel> getCourses(){
        List<CourseModel> returnList = new ArrayList<>();

        // get data from database
        String queryString = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int courseId = cursor.getInt(0);

                String courseCode = cursor.getString(1);
                String courseName = cursor.getString(2);

                String courseInstructor = cursor.getString(3);
                int courseInstructorId = cursor.getInt(4);

                String courseDays = cursor.getString(5);
                String courseHours = cursor.getString(6);

                String courseDescription = cursor.getString(7);

                int courseCapacity = cursor.getInt(8);
                int courseEnrolled = cursor.getInt(9);


                CourseModel newCourse = new CourseModel(courseId, courseCode, courseName, courseInstructor, courseInstructorId, courseDays,
                        courseHours, courseDescription, courseCapacity, courseEnrolled);
                returnList.add(newCourse);

            }while (cursor.moveToNext());
        }else{
            // failed
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<CourseModel> getMyCourses(){
        List<CourseModel> returnList = new ArrayList<>();

        // get data from database

        String queryString = " SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_COURSE_INSTRUCTOR_ID + " = " + mainActivity.getCurrentId();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int courseId = cursor.getInt(0);
                String courseCode = cursor.getString(1);
                String courseName = cursor.getString(2);

                String courseInstructor = cursor.getString(3);
                int courseInstructorId = cursor.getInt(4);

                String courseDays = cursor.getString(5);
                String courseHours = cursor.getString(6);

                String courseDescription = cursor.getString(7);

                int courseCapacity = cursor.getInt(8);
                int courseEnrolled = cursor.getInt(9);

                CourseModel newCourse = new CourseModel(courseId, courseCode, courseName, courseInstructor, courseInstructorId, courseDays,
                        courseHours, courseDescription, courseCapacity, courseEnrolled);
                returnList.add(newCourse);

            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
        return returnList;
    }

    public boolean deleteCourse(int courseID){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_COURSE_ID + " = '" + courseID + "'";

        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }

    public Cursor getCourseID(String courseCode, String courseName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " + COLUMN_COURSE_ID + " FROM " + TABLE_NAME + " WHERE " +
                COLUMN_COURSE_CODE + " = '" + courseCode + "' AND " + COLUMN_COURSE_NAME + " = '" + courseName + "' ";

        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateCourseCode(int courseID, String newCourseCode){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + TABLE_NAME + " SET " + COLUMN_COURSE_CODE + " = '" +
                newCourseCode + "' WHERE " + COLUMN_COURSE_ID + " = '" + courseID + "'";

        db.execSQL(query);
    }

    public void updateCourseName(int courseID, String newCourseName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + TABLE_NAME + " SET " + COLUMN_COURSE_NAME + " = '" +
                newCourseName + "' WHERE " + COLUMN_COURSE_ID + " = '" + courseID + "'";

        db.execSQL(query);
    }

       //public void addCourseInfo(int courseID, String courseInstructor, String courseDays, String courseHours, String courseDescription, int courseCapacity){
       //     SQLiteDatabase db = this.getWritableDatabase();
       //     String query = " INSERT INTO " + TABLE_NAME + " (" + COLUMN_COURSE_INSTRUCTOR + " , " + COLUMN_COURSE_DAYS + " , " + COLUMN_COURSE_HOURS + " , " +
       //             COLUMN_COURSE_DESCRIPTION + " , " + COLUMN_COURSE_CAPACITY + ") " + " VALUES " + " (" + courseInstructor + " , " + courseDays + " , " + courseHours + " , " +
       //             courseDescription + " , " + courseCapacity + ") " + " WHERE " + COLUMN_COURSE_ID + " = " + courseID;
       //     db.execSQL(query);
       // }

    public void removeInstructor(int courseID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + TABLE_NAME + " SET " + COLUMN_COURSE_INSTRUCTOR + " = " + null + " , " + COLUMN_COURSE_INSTRUCTOR_ID + " = " + 0 + " , " +
                COLUMN_COURSE_DAYS + " = " + null + " , " + COLUMN_COURSE_HOURS+ " = " + null + " , " + COLUMN_COURSE_DESCRIPTION + " = " + null + " , " +
                COLUMN_COURSE_CAPACITY + " = " + 0 + " WHERE " + COLUMN_COURSE_ID + " = " + courseID;

        db.execSQL(query);
    }

    public void updateCourseInfo(int courseID, String courseInstructor, String courseDays, String courseHours, String courseDescription, int courseCapacity){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + TABLE_NAME + " SET " + COLUMN_COURSE_INSTRUCTOR + " = " + courseInstructor + " , " + COLUMN_COURSE_DAYS + " = " + courseDays + " , " +
                COLUMN_COURSE_HOURS+ " = " + courseHours + " , " + COLUMN_COURSE_DESCRIPTION + " = " + courseDescription + " , " + COLUMN_COURSE_CAPACITY + " = " + 0 + " WHERE " +
                COLUMN_COURSE_ID + " = " + courseID;

        db.execSQL(query);
    }

    public int getCourseInstructorId(int courseId){

        String queryString = " SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_COURSE_ID + " = " + courseId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int courseInstructorId = cursor.getInt(4);
                return courseInstructorId;
            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
        return -1;
    }
}


























