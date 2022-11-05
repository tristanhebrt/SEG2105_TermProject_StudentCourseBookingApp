package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

public class AdminCourseOptions extends AppCompatActivity implements View.OnClickListener {
    Button btn_createCourse, btn_deleteCourse, btn_editCourse, btn_cancel;
    EditText et_courseCode, et_courseName;
    ListView lv_courseList;
    CourseDatabaseHelper courseDatabaseHelper;
    ArrayAdapter courseArrayAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course_options);

        btn_createCourse = (Button) findViewById(R.id.createCourse);
        btn_deleteCourse = (Button) findViewById(R.id.deleteCourse);
        btn_editCourse = (Button) findViewById(R.id.editCourse);
        btn_cancel = (Button) findViewById(R.id.cancelCourseOptions);
        setClickListeners();

        et_courseCode = (EditText) findViewById(R.id.courseCode);
        et_courseName = (EditText) findViewById(R.id.courseName);

        lv_courseList = (ListView) findViewById(R.id.courseList);

        courseDatabaseHelper = new CourseDatabaseHelper(AdminCourseOptions.this);
        ShowCoursesOnListView(courseDatabaseHelper);
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

                // Toast.makeText(this, "CREATE COURSE", Toast.LENGTH_SHORT).show();

                CourseModel courseModel;

                try {
                    courseModel = new CourseModel(-1, Integer.parseInt(et_courseCode.getText().toString()), et_courseName.getText().toString());
                    Toast.makeText(AdminCourseOptions.this, "COURSE CREATED", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(AdminCourseOptions.this, "INVALID INFORMATION", Toast.LENGTH_SHORT).show();
                    courseModel = new CourseModel(-1, -1, "error");
                }

                CourseDatabaseHelper courseDatabaseHelper = new CourseDatabaseHelper(AdminCourseOptions.this);

                boolean success = courseDatabaseHelper.createCourse(courseModel);

                // Toast.makeText(InstructorAccountCreator.this, "Success"+success, Toast.LENGTH_SHORT).show();

                break;
            case R.id.deleteCourse:
                Toast.makeText(this, "DELETE COURSE", Toast.LENGTH_SHORT).show();
                break;
            case R.id.editCourse:
                Toast.makeText(this, "EDIT COURSE", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cancelCourseOptions:
                openAdminHome();
                // Toast.makeText(this, "CANCEL", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void openAdminHome() {
        Intent intent = new Intent(this, AdminHome.class);
        startActivity(intent);
    }

    private void ShowCoursesOnListView(CourseDatabaseHelper instructorDatabaseHelper) {
        courseArrayAdapter = new ArrayAdapter<CourseModel>(AdminCourseOptions.this, android.R.layout.simple_list_item_1, courseDatabaseHelper.getCourses());
        lv_courseList.setAdapter(courseArrayAdapter);
    }
}