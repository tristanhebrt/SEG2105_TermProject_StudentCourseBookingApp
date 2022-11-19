package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Arrays;

public class InstructorHome extends AppCompatActivity implements View.OnClickListener {
    Button btn_editSelectedCourse, btn_viewAllCourses, btn_viewMyCourses;
    ListView lv_allCourses, lv_myCourses;
    CourseDatabaseHelper courseDatabaseHelper;
    ArrayAdapter allCoursesArrayAdapter, myCoursesArrayAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_home);

        btn_editSelectedCourse = (Button) findViewById(R.id.editSelectedCourse);
        btn_viewAllCourses = (Button) findViewById(R.id.viewAllCourses);
        btn_viewMyCourses = (Button) findViewById(R.id.viewMyCourses);
        setClickListeners();

        lv_allCourses = (ListView) findViewById(R.id.allCoursesList);
        lv_myCourses = (ListView) findViewById(R.id.myCoursesList);
    }

    private void setClickListeners() {
        for (Button button : Arrays.asList(btn_editSelectedCourse, btn_viewAllCourses, btn_viewMyCourses)){
            button.setOnClickListener(this);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editSelectedCourse:

                break;

            case R.id.viewAllCourses:
                lv_myCourses.setVisibility(View.GONE);
                lv_allCourses.setVisibility(View.VISIBLE);
                courseDatabaseHelper = new CourseDatabaseHelper(InstructorHome.this);
                ShowAllCoursesOnListView(courseDatabaseHelper);
                break;

            case R.id.viewMyCourses:
                lv_allCourses.setVisibility(View.GONE);
                lv_myCourses.setVisibility(View.VISIBLE);
                courseDatabaseHelper = new CourseDatabaseHelper(InstructorHome.this);
                ShowMyCoursesOnListView(courseDatabaseHelper);
                break;
        }
    }

    private void ShowAllCoursesOnListView(CourseDatabaseHelper courseDatabaseHelper) {

        allCoursesArrayAdapter = new ArrayAdapter<CourseModel>(InstructorHome.this, android.R.layout.simple_list_item_1, courseDatabaseHelper.getCourses());
        lv_allCourses.setAdapter(allCoursesArrayAdapter);
    }

    private void ShowMyCoursesOnListView(CourseDatabaseHelper courseDatabaseHelper) {

        myCoursesArrayAdapter = new ArrayAdapter<CourseModel>(InstructorHome.this, android.R.layout.simple_list_item_1, courseDatabaseHelper.getMyCourses());
        lv_myCourses.setAdapter(myCoursesArrayAdapter);
    }
}