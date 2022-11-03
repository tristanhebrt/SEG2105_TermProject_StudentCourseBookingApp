package com.example.studentcoursebookingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StudentDatabaseHelper extends SQLiteOpenHelper {

    public static final String STUDENT_TABLE = "INSTRUCTOR_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_STUDENT_FIRSTNAME = "INSTRUCTOR_FIRSTNAME";
    public static final String COLUMN_STUDENT_LASTNAME = "INSTRUCTOR_LASTNAME";
    public static final String COLUMN_STUDENT_EMAIL = "INSTRUCTOR_EMAIL";
    public static final String COLUMN_STUDENT_USERNAME = "INSTRUCTOR_USERNAME";
    public static final String COLUMN_STUDENT_PASSWORD = "INSTRUCTOR_PASSWORD";

    public StudentDatabaseHelper(@Nullable Context context) {
        super(context, "student.db", null, 1);
    }

    // called when the database is first accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + STUDENT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " " +COLUMN_STUDENT_FIRSTNAME + " TEXT, " + COLUMN_STUDENT_LASTNAME + " TEXT, " + COLUMN_STUDENT_EMAIL + " TEXT, " +
                " " +COLUMN_STUDENT_USERNAME + " TEXT, " + COLUMN_STUDENT_PASSWORD + " TEXT)";

        db.execSQL(createTableStatement);
    }


    // for when the database version changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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
        String queryString = "DELETE FROM " + STUDENT_TABLE + " WHERE " + COLUMN_ID + " = " + studentModel.getId();

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
}
