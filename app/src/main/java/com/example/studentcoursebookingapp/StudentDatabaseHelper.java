package com.example.studentcoursebookingapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentDatabaseHelper extends SQLiteOpenHelper {

    public static final String STUDENT_TABLE = "STUDENT_TABLE";
    public static final String COLUMN_STUDENT_ID = "ID";
    public static final String COLUMN_STUDENT_FIRSTNAME = "STUDENT_FIRSTNAME";
    public static final String COLUMN_STUDENT_LASTNAME = "STUDENT_LASTNAME";
    public static final String COLUMN_STUDENT_EMAIL = "STUDENT_EMAIL";
    public static final String COLUMN_STUDENT_USERNAME = "STUDENT_USERNAME";
    public static final String COLUMN_STUDENT_PASSWORD = "STUDENT_PASSWORD";

    public StudentDatabaseHelper(@Nullable Context context) {
        super(context, "student.db", null, 3);
    }

    // called when the database is first accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + STUDENT_TABLE + " (" +
                COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + " " +
                COLUMN_STUDENT_FIRSTNAME + " TEXT, " +
                COLUMN_STUDENT_LASTNAME + " TEXT, " +
                COLUMN_STUDENT_EMAIL + " TEXT, " + " " +
                COLUMN_STUDENT_USERNAME + " TEXT, " +
                COLUMN_STUDENT_PASSWORD + " TEXT)";

        db.execSQL(createTableStatement);
    }


    // for when the database version changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE); // drop older table

        onCreate(sqLiteDatabase); // create table again
    }

    public boolean createStudentAccount(StudentModel studentModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STUDENT_FIRSTNAME, studentModel.getFirstName());
        cv.put(COLUMN_STUDENT_LASTNAME, studentModel.getLastName());
        cv.put(COLUMN_STUDENT_EMAIL, studentModel.getEmail());
        cv.put(COLUMN_STUDENT_USERNAME, studentModel.getUsername());
        cv.put(COLUMN_STUDENT_PASSWORD, studentModel.getPassword());

        long insert = db.insert(STUDENT_TABLE, null, cv);

        return insert != -1;
    }

    public boolean deleteStudentAccount(StudentModel studentModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + STUDENT_TABLE + " WHERE " + COLUMN_STUDENT_ID + " = " + studentModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }

    public List<StudentModel> getStudents(){
        List<StudentModel> returnList = new ArrayList<>();

        // get data from database

        String queryString = "SELECT * FROM " + STUDENT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                String email = cursor.getString(3);
                String username = cursor.getString(4);
                String password = cursor.getString(5);

                StudentModel newStudent = new StudentModel(id, firstName, lastName, email, username, password);
                returnList.add(newStudent);

            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
        return returnList;
    }

    public boolean checkStudentAccount(String username, String password){
        String queryString = " SELECT * FROM " + STUDENT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        boolean isStudent = false;

        if(cursor.moveToFirst()){
            do{
                String usernameCheck = cursor.getString(4);
                String passwordCheck = cursor.getString(5);

                if(username.equals(usernameCheck) && password.equals(passwordCheck)){
                    isStudent = true;
                }

            }while (cursor.moveToNext());

        }else{
            isStudent = false;
        }
        cursor.close();   // cleanup
        db.close();
        return isStudent;
    }

    public Cursor getStudentID(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " + COLUMN_STUDENT_ID + " FROM " + STUDENT_TABLE + " WHERE " +
                COLUMN_STUDENT_USERNAME + " = '" + username + "' AND " + COLUMN_STUDENT_PASSWORD + " = '" + password + "' ";

        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public String getStudentInfo(int studentId){

        System.out.println("getStudent info was called");

        System.out.println("studentId : " + studentId);

        String studentInfo = "nothing";

        System.out.println("no error0");

        try {
            System.out.println("no error1");

            String query = " SELECT * FROM " +
                    STUDENT_TABLE + " WHERE " +
                    COLUMN_STUDENT_ID + " = '" + studentId + "' ";

            System.out.println("no error1.2");  // doesn't go any further

            SQLiteDatabase db = this.getReadableDatabase();

            System.out.println("no error1.4");

            Cursor cursor = db.rawQuery(query, null);

            System.out.println("no error1.6");

            if (cursor.moveToFirst()) {
                System.out.println("no error2");
                do {
                    System.out.println("no error3");
                    String firstName = cursor.getString(1);
                    String lastName = cursor.getString(2);
                    String username = cursor.getString(4);

                    System.out.println("firstName : " + firstName);
                    System.out.println("lastName : " + lastName);
                    System.out.println("username : " + username);

                    studentInfo = "Name : " + firstName + " " + lastName +
                            "\nUsername : " + username;

                    System.out.println("studentInfo :" + studentInfo);

                } while (cursor.moveToNext());
            } else {
                // failed
                System.out.println("error");
            }
            cursor.close();
            db.close();

            return studentInfo;

        }catch (Exception e) {
            return "";
        }
    }
}


