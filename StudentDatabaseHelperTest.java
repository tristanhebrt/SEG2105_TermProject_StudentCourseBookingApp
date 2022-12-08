package com.example.studentcoursebookingapp;

import static org.junit.Assert.*;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Test;

public class StudentDatabaseHelperTest {
    Context appContext;
    StudentDatabaseHelper studentDatabaseHelper = new StudentDatabaseHelper(appContext);
    SQLiteDatabase db;

    StudentModel studentModel = new StudentModel(300,"Cedric",
            "Dimatulac", "cdima078@uottawa.ca",
            "NerdCedric", "superduperSh9zue");

    @Test
    public void testCreateStudentAccount() {
        studentDatabaseHelper.onCreate(db);
        boolean b = studentDatabaseHelper.createStudentAccount(studentModel);
        assertTrue(b);
    }

    @Test
    public void testCheckStudentAccount() {
        studentDatabaseHelper.onCreate(db);
        studentDatabaseHelper.onCreate(db);
        studentDatabaseHelper.createStudentAccount(studentModel);
        boolean b = studentDatabaseHelper.checkStudentAccount("NerdCedric", "superduperSh9zue");
    }

}