package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminEditCourse extends AppCompatActivity {
    EditText et_newCourseCode, et_newCourseName;
    Button btn_save;
    String courseCode, courseName, newCourseCode, newCourseName;
    CourseDatabaseHelper courseDatabaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_course);

        et_newCourseCode = (EditText) findViewById(R.id.newCourseCode);
        et_newCourseName = (EditText) findViewById(R.id.newCourseName);
        btn_save = (Button) findViewById(R.id.saveEditCourse);
        courseDatabaseHelper = new CourseDatabaseHelper(this);

        Intent receivedIntent = getIntent();
        courseCode = receivedIntent.getStringExtra("courseCode"); // get current course code
        courseName = receivedIntent.getStringExtra("courseName"); // get current course name

        et_newCourseCode.setText(courseCode);
        et_newCourseName.setText(courseName);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCourseCode = et_newCourseCode.getText().toString();
                newCourseName = et_newCourseName.getText().toString();
                Cursor data = courseDatabaseHelper.getCourseID(courseCode, courseName);
                int courseID = -1;
                while(data.moveToNext()) courseID = data.getInt(0);
                if(courseID > -1) {
                    if(!newCourseCode.equals("") && !newCourseName.equals("")){
                        courseDatabaseHelper.updateCourseCode(courseID, newCourseCode);
                        courseDatabaseHelper.updateCourseName(courseID, newCourseName);
                        Toast.makeText(AdminEditCourse.this, "Success", Toast.LENGTH_SHORT).show();
                        openAdminCourseOptions();
                    }else{
                        Toast.makeText(AdminEditCourse.this, "Enter a new name and code to save", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }

    public void openAdminCourseOptions() {
        Intent intent = new Intent(this, AdminCourseOptions.class);
        startActivity(intent);
    }

}