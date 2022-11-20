package com.example.studentcoursebookingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InstructorDatabaseHelper extends SQLiteOpenHelper {

    public static final String INSTRUCTOR_TABLE = "INSTRUCTOR_TABLE";
    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_INSTRUCTOR_FIRSTNAME = "INSTRUCTOR_FIRSTNAME";
    public static final String COLUMN_INSTRUCTOR_LASTNAME = "INSTRUCTOR_LASTNAME";
    public static final String COLUMN_INSTRUCTOR_EMAIL = "INSTRUCTOR_EMAIL";
    public static final String COLUMN_INSTRUCTOR_USERNAME = "INSTRUCTOR_USERNAME";
    public static final String COLUMN_INSTRUCTOR_PASSWORD = "INSTRUCTOR_PASSWORD";

    public InstructorDatabaseHelper(@Nullable Context context) {
        super(context, "instructor.db", null, 1);
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

    public boolean createInstructorAccount(InstructorModel instructorModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_INSTRUCTOR_FIRSTNAME, instructorModel.getFirstName());
        cv.put(COLUMN_INSTRUCTOR_LASTNAME, instructorModel.getLastName());
        cv.put(COLUMN_INSTRUCTOR_EMAIL, instructorModel.getEmail());
        cv.put(COLUMN_INSTRUCTOR_USERNAME, instructorModel.getUsername());
        cv.put(COLUMN_INSTRUCTOR_PASSWORD, instructorModel.getPassword());

        long insert = db.insert(INSTRUCTOR_TABLE, null, cv);
        return insert != -1;
    }

    public boolean deleteInstructorAccount(InstructorModel instructorModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + INSTRUCTOR_TABLE + " WHERE " + COLUMN_ID + " = " + instructorModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }

    public List<InstructorModel> getInstructors(){
        List<InstructorModel> returnList = new ArrayList<>();

        // get data from database

        String queryString = "SELECT * FROM " + INSTRUCTOR_TABLE;
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

                InstructorModel newInstructor = new InstructorModel(id, firstName, lastName, email, username, password);
                returnList.add(newInstructor);

            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
        return returnList;
    }

    public boolean checkInstructorAccount(String username, String password){
        String queryString = " SELECT * FROM " + INSTRUCTOR_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        boolean isInstructor = false;

        if(cursor.moveToFirst()){
            do{
                String usernameCheck = cursor.getString(4);
                String passwordCheck = cursor.getString(5);

                if(username.equals(usernameCheck) && password.equals(passwordCheck)){
                    isInstructor = true;
                }

            }while (cursor.moveToNext());

        }else{
            isInstructor = false;
        }
        cursor.close();   // cleanup
        db.close();
        return isInstructor;
    }

    public Cursor getInstructorID(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " + COLUMN_ID + " FROM " + INSTRUCTOR_TABLE + " WHERE " +
                COLUMN_INSTRUCTOR_USERNAME + " = '" + username + "' AND " + COLUMN_INSTRUCTOR_PASSWORD + " = '" + password + "' ";

        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public String getInstructorName(int instructorId){

        String queryString = " SELECT * FROM " + INSTRUCTOR_TABLE + " WHERE " + COLUMN_ID + " = " + instructorId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String instructorName = cursor.getString(1);
                return instructorName;
            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
        return null;
    }
}
