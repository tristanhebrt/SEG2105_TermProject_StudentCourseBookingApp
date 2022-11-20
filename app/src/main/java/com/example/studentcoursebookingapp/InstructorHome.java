package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

public class InstructorHome extends AppCompatActivity implements View.OnClickListener {
    Button btn_editSelectedCourse, btn_viewAllCourses, btn_viewMyCourses;
    ListView lv_allCourses, lv_myCourses;
    CourseDatabaseHelper courseDatabaseHelper;
    ArrayAdapter allCoursesArrayAdapter, myCoursesArrayAdapter;

    public int selectedCourseId = -1;
    public int userId;

    MainActivity mainActivity = new MainActivity();

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

        // Bundle extras = getIntent().getExtras();  getting user id from MainActivity
        // if (extras != null){ userId = extras.getInt("userId"); }

        userId = mainActivity.getCurrentId();

        lv_allCourses.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                CourseModel clickedCourse = (CourseModel) parent.getItemAtPosition(position);
                selectedCourseId = clickedCourse.getCourseId();
                Toast.makeText(InstructorHome.this, "course id = " + selectedCourseId, Toast.LENGTH_SHORT).show();

            }
        });

        lv_myCourses.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                CourseModel clickedCourse = (CourseModel) parent.getItemAtPosition(position);
                selectedCourseId = clickedCourse.getCourseId();
                Toast.makeText(InstructorHome.this, "course id = " + selectedCourseId, Toast.LENGTH_SHORT).show();
            }
        });
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
                if (selectedCourseId != -1) {
                    int i = courseDatabaseHelper.getCourseInstructorId(selectedCourseId); // selected course's instructor id
                    int j = mainActivity.getCurrentId(); // current user's id
                    if (i != -1 || i == j) { // if the selected course doesn't have an assigned instructor or if the assigned instructor is the current user
                        openInstructorCourseOptions();
                        break;
                    }
                }
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

    public Integer getSelectedCourseId(){
        return selectedCourseId;
    }

    private void openInstructorCourseOptions() {
        Intent intent = new Intent(this, InstructorCourseOptions.class);
        intent.putExtra("selectedCourseId", selectedCourseId);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
}