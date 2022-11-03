package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

public class AdminCourseOptions extends AppCompatActivity implements View.OnClickListener {
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
        setClickListeners();

        et_courseCode = (EditText) findViewById(R.id.courseCode);
        et_courseName = (EditText) findViewById(R.id.courseName);

    }

    private void setClickListeners() {
        for(Button button : Arrays.asList(btn_createCourse, btn_deleteCourse, btn_editCourse, btn_cancel)){
            button.setOnClickListener(this);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.createCourse:
                Toast.makeText(this, "CREATE COURSE", Toast.LENGTH_SHORT).show();
                break;
            case R.id.deleteCourse:
                Toast.makeText(this, "DELETE COURSE", Toast.LENGTH_SHORT).show();
                break;
            case R.id.editCourse:
                Toast.makeText(this, "EDIT COURSE", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cancelCourseOptions:
                Toast.makeText(this, "CANCEL", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}