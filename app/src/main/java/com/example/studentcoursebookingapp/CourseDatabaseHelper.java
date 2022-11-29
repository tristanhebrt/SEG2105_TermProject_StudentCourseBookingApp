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
import java.util.Arrays;
import java.util.List;

public class CourseDatabaseHelper extends SQLiteOpenHelper {

    MainActivity mainActivity = new MainActivity();

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

    public CourseDatabaseHelper(@Nullable Context context) {
        super(context, "course.db", null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + COLUMN_COURSE_CODE + " INTEGER, "
                + COLUMN_COURSE_NAME + " TEXT, "

                + COLUMN_COURSE_INSTRUCTOR + " TEXT, "
                + COLUMN_COURSE_INSTRUCTOR_ID + " INTEGER, "

                + COLUMN_COURSE_DAYS_AND_HOURS + " TEXT, "
                + COLUMN_COURSE_DESCRIPTION + " TEXT, "

                + COLUMN_COURSE_CAPACITY + " INTEGER, "
                + COLUMN_COURSE_ENROLLED + " TEXT )";

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
        String queryString = "DELETE FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_ID + " = '" + courseID + "'";

        Cursor cursor = db.rawQuery(queryString, null);
        return cursor.moveToFirst();
    }

    public void removeInstructor(int courseID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " +
                TABLE_NAME + " SET " +
                COLUMN_COURSE_INSTRUCTOR + " = '" + "" + "' , " +
                COLUMN_COURSE_INSTRUCTOR_ID + " = " + -1 + " , " +
                COLUMN_COURSE_DAYS_AND_HOURS + " = '" + "" + "' , " +
                COLUMN_COURSE_DESCRIPTION + " = '" + "" + "' , " +
                COLUMN_COURSE_ENROLLED + " = '" + "" + "' , " +
                COLUMN_COURSE_CAPACITY + " = " + -1 + " WHERE " +
                COLUMN_COURSE_ID + " = '" + courseID + "' ";

        db.execSQL(query);
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

                String courseDaysAndHours = cursor.getString(5);

                String courseDescription = cursor.getString(6);

                int courseCapacity = cursor.getInt(7);

                String courseEnrolled = cursor.getString(8);


                CourseModel newCourse = new CourseModel(courseId, courseCode, courseName, courseInstructor, courseInstructorId, courseDaysAndHours,
                        courseDescription, courseCapacity, courseEnrolled);
                returnList.add(newCourse);

            }while (cursor.moveToNext());
        }else{
            // failed
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<CourseModel> getMyCourses(int currentId){
        List<CourseModel> returnList = new ArrayList<>();

        // get data from database

        String queryString = " SELECT * FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_INSTRUCTOR_ID + " = '" + currentId + "' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int courseId = cursor.getInt(0);
                String courseCode = cursor.getString(1);
                String courseName = cursor.getString(2);

                String courseInstructor = cursor.getString(3);
                int courseInstructorId = cursor.getInt(4);

                String courseDaysAndHours = cursor.getString(5);

                String courseDescription = cursor.getString(6);

                int courseCapacity = cursor.getInt(7);
                String courseEnrolled = cursor.getString(8);

                CourseModel newCourse = new CourseModel(courseId, courseCode, courseName, courseInstructor, courseInstructorId, courseDaysAndHours,
                        courseDescription, courseCapacity, courseEnrolled);
                returnList.add(newCourse);

            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
        return returnList;
    }



























    public List<CourseModel> getStudentCourses(int currentId){
        List<CourseModel> returnList = new ArrayList<>();
        List<Integer> studentCoursesId = getCourseIdByStudentId(currentId);

        for (int i = 0; i < studentCoursesId.size(); i++) {

            String queryString = " SELECT * FROM " +
                    TABLE_NAME + " WHERE " +
                    COLUMN_COURSE_ID + " = '" + studentCoursesId.get(i) + "' ";

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryString, null);

            if (cursor.moveToFirst()) {
                do {
                    int courseId = cursor.getInt(0);
                    String courseCode = cursor.getString(1);
                    String courseName = cursor.getString(2);

                    String courseInstructor = cursor.getString(3);
                    int courseInstructorId = cursor.getInt(4);

                    String courseDaysAndHours = cursor.getString(5);

                    String courseDescription = cursor.getString(6);

                    int courseCapacity = cursor.getInt(7);
                    String courseEnrolled = cursor.getString(8);

                    CourseModel newCourse = new CourseModel(courseId, courseCode, courseName, courseInstructor, courseInstructorId, courseDaysAndHours,
                            courseDescription, courseCapacity, courseEnrolled);
                    returnList.add(newCourse);

                } while (cursor.moveToNext());

            } else {
                // failure don't add anything
            }
            cursor.close();   // cleanup
            db.close();
        }
        return returnList;
    }

    public List<Integer> getCourseIdByStudentId(int studentId){
        List<Integer> returnList = new ArrayList<>();

        String queryString = " SELECT * FROM " +
                TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String courseEnrolled = cursor.getString(8);
                if (courseEnrolled != null) {
                    List<String> enrolledStudents = new ArrayList<String>(Arrays.asList(courseEnrolled.split("/")));
                    if (enrolledStudents.contains(Integer.toString(studentId))) {
                        returnList.add(cursor.getInt(0));
                    }
                }

            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
        return returnList;
    }




























    public List<CourseModel> getSearchedCourses(String searchCode, String searchName, String searchDay){
        List<CourseModel> returnList = new ArrayList<>();

        // get data from database

        String queryString = " SELECT * FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_CODE + " = '" + searchCode + "' OR " +
                COLUMN_COURSE_NAME + " = '" + searchName + "' ";

        if (searchDay != null) {
            if (getCourseIdByDay(searchDay) >= 0) {
                queryString = " SELECT * FROM " +
                        TABLE_NAME + " WHERE " +
                        COLUMN_COURSE_CODE + " = '" + searchCode + "' OR " +
                        COLUMN_COURSE_NAME + " = '" + searchName + "' OR " +
                        COLUMN_COURSE_ID + " = '" + getCourseIdByDay(searchDay) + "'";
            }
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int courseId = cursor.getInt(0);
                String courseCode = cursor.getString(1);
                String courseName = cursor.getString(2);

                String courseInstructor = cursor.getString(3);
                int courseInstructorId = cursor.getInt(4);

                String courseDaysAndHours = cursor.getString(5);

                String courseDescription = cursor.getString(6);

                int courseCapacity = cursor.getInt(7);
                String courseEnrolled = cursor.getString(8);

                CourseModel newCourse = new CourseModel(courseId, courseCode, courseName, courseInstructor, courseInstructorId, courseDaysAndHours,
                        courseDescription, courseCapacity, courseEnrolled);
                returnList.add(newCourse);

            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
        return returnList;
    }

    public int getCourseIdByDay(String searchCourseDay){
        String queryString = " SELECT * FROM " +
                TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String courseDaysAndHours = cursor.getString(5);
                List<String> courseDays = new ArrayList<String>(Arrays.asList(courseDaysAndHours.split("/")));
                if (courseDays.contains(searchCourseDay)){ return cursor.getInt(0); }

            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
        return -1;
    }



    public Cursor getCourseID(String courseCode, String courseName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " +
                COLUMN_COURSE_ID + " FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_CODE + " = '" + courseCode + "' AND " +
                COLUMN_COURSE_NAME + " = '" + courseName + "' ";

        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public int getCourseInstructorId(int courseId){

        String queryString = " SELECT * FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_ID + " = " + courseId;

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

    public void updateCourseCode(int courseID, String newCourseCode){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " +
                TABLE_NAME + " SET " +
                COLUMN_COURSE_CODE + " = '" + newCourseCode + "' WHERE " +
                COLUMN_COURSE_ID + " = '" + courseID + "'";

        db.execSQL(query);
    }

    public void updateCourseName(int courseID, String newCourseName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " +
                TABLE_NAME + " SET " +
                COLUMN_COURSE_NAME + " = '" + newCourseName + "' WHERE " +
                COLUMN_COURSE_ID + " = '" + courseID + "'";

        db.execSQL(query);
    }

    public void updateCourseInfo(int courseID, String courseInstructor, int courseInstructorId, String courseDaysAndHours, String courseDescription, int courseCapacity){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " +
                TABLE_NAME + " SET " +
                COLUMN_COURSE_INSTRUCTOR + " = '" + courseInstructor + "' , " +
                COLUMN_COURSE_INSTRUCTOR_ID + " = '" + courseInstructorId + "' , " +
                COLUMN_COURSE_DAYS_AND_HOURS + " = '" + courseDaysAndHours + "' , " +
                COLUMN_COURSE_DESCRIPTION + " = '" + courseDescription + "' , " +
                COLUMN_COURSE_ENROLLED + " = '" + " " + "' , " +
                COLUMN_COURSE_CAPACITY + " = '" + courseCapacity + "' WHERE " +
                COLUMN_COURSE_ID + " = '" + courseID + "' ";

        db.execSQL(query);
    }

    public void enrollStudent(int courseId, int studentId){

        String queryString = " SELECT * FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_ID + " = " + courseId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        String enrolled = " ";
        if(cursor.moveToFirst()){
            do{
                enrolled = cursor.getString(8);
                enrolled += Integer.toString(studentId) + "/";

            }while (cursor.moveToNext());

            String query = " UPDATE " +
                    TABLE_NAME + " SET " +
                    COLUMN_COURSE_ENROLLED + " = '" + enrolled + "' WHERE " +
                    COLUMN_COURSE_ID + " = '" + courseId + "' ";
            db.execSQL(query);

        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
    }
}


























