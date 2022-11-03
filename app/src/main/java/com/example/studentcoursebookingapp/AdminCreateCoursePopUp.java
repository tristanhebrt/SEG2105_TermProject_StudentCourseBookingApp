package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AdminCreateCoursePopUp extends AppCompatActivity {
    Button btn_createCourse, btn_deleteCourse, btn_editCourse, btn_cancel;
    EditText et_courseCode, et_courseName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_course_pop_up);

        btn_createCourse = (Button) findViewById(R.id.createCourse);
        btn_deleteCourse = (Button) findViewById(R.id.deleteCourse);
        btn_editCourse = (Button) findViewById(R.id.editCourse);
        btn_cancel = (Button) findViewById(R.id.cancelCourseOptions);
        et_courseCode = (EditText) findViewById(R.id.courseCode);
        et_courseName = (EditText) findViewById(R.id.courseName);

    }
}