package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class StudentHome extends AppCompatActivity {
    Button btn_enrollSelectedCourse, btn_viewAllCourses, btn_viewMyCourses, btn_search;
    EditText et_courseCode, et_courseName, et_courseDay;
    ListView lv_allCourses, lv_myCourses, lv_searchedCourses;
    CourseDatabaseHelper courseDatabaseHelper;
    ArrayAdapter allCoursesArrayAdapter, myCoursesArrayAdapter, searchedCoursesArrayAdapter;

    public int selectedCourseId = -1;
    public int currentId;
    String searchCode = null;
    String searchName = null;
    String searchDay = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
    }
}