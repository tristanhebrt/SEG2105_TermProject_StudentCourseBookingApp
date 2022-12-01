package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

public class StudentHome extends AppCompatActivity implements View.OnClickListener {
    Button btn_enrollSelectedCourse, btn_unEnrollSelectedCourse, btn_viewAllCourses, btn_viewMyCourses, btn_search;
    EditText et_courseCode, et_courseName, et_courseDay;
    ListView lv_allCourses, lv_myCourses, lv_searchedCourses;
    CourseDatabaseHelper courseDatabaseHelper;
    ArrayAdapter allCoursesArrayAdapter, myCoursesArrayAdapter, searchedCoursesArrayAdapter;

    public int selectedCourseId = -1;
    public int currentId;
    String searchCode = "";
    String searchName = "";
    String searchDay = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        btn_enrollSelectedCourse = (Button) findViewById(R.id.enrollCourseStudentHomeButton);
        btn_unEnrollSelectedCourse = (Button) findViewById(R.id.unEnrollCourseStudentHomeButton);
        btn_viewAllCourses = (Button) findViewById(R.id.viewAllCourses);
        btn_viewMyCourses = (Button) findViewById(R.id.viewMyCourses);
        btn_search = (Button) findViewById(R.id.searchCourseStudentHomeButton);

        setClickListeners();

        et_courseCode = (EditText) findViewById(R.id.searchCourseCodeStudentHomeEditText);
        et_courseName = (EditText) findViewById(R.id.searchCourseNameStudentHomeEditText);
        et_courseDay = (EditText) findViewById(R.id.searchCourseDayStudentHomeEditText);

        lv_allCourses = (ListView) findViewById(R.id.allCoursesList);
        lv_myCourses = (ListView) findViewById(R.id.myCoursesList);
        lv_searchedCourses = (ListView) findViewById(R.id.searchedCoursesList);

        Bundle extras = getIntent().getExtras();  // getting currentId from MainActivity
        if (extras != null){ currentId = extras.getInt("currentId"); }

        lv_allCourses.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                CourseModel clickedCourse = (CourseModel) parent.getItemAtPosition(position);
                selectedCourseId = clickedCourse.getCourseId();
                Toast.makeText(StudentHome.this, "Course selected", Toast.LENGTH_SHORT).show();

            }
        });

        lv_myCourses.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                CourseModel clickedCourse = (CourseModel) parent.getItemAtPosition(position);
                selectedCourseId = clickedCourse.getCourseId();
                Toast.makeText(StudentHome.this, "Course selected", Toast.LENGTH_SHORT).show();
            }
        });

        lv_searchedCourses.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                CourseModel clickedCourse = (CourseModel) parent.getItemAtPosition(position);
                selectedCourseId = clickedCourse.getCourseId();
                Toast.makeText(StudentHome.this, "Course selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setClickListeners() {
        for (Button button : Arrays.asList(btn_enrollSelectedCourse, btn_unEnrollSelectedCourse, btn_viewAllCourses, btn_viewMyCourses, btn_search)){
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.enrollCourseStudentHomeButton:
                boolean enrolled = courseDatabaseHelper.enrollStudent(selectedCourseId, currentId);
                break;

            case R.id.unEnrollCourseStudentHomeButton:
                courseDatabaseHelper.unEnrollStudent(selectedCourseId, currentId);
                break;

            case R.id.viewAllCourses:
                lv_myCourses.setVisibility(View.GONE);
                lv_searchedCourses.setVisibility(View.GONE);
                lv_allCourses.setVisibility(View.VISIBLE);

                courseDatabaseHelper = new CourseDatabaseHelper(StudentHome.this);
                ShowAllCoursesOnListView(courseDatabaseHelper);
                break;

            case R.id.viewMyCourses:
                lv_allCourses.setVisibility(View.GONE);
                lv_searchedCourses.setVisibility(View.GONE);
                lv_myCourses.setVisibility(View.VISIBLE);

                courseDatabaseHelper = new CourseDatabaseHelper(StudentHome.this);
                ShowMyCoursesOnListView(courseDatabaseHelper);
                break;

            case R.id.searchCourseStudentHomeButton:
                lv_allCourses.setVisibility(View.GONE);
                lv_myCourses.setVisibility(View.GONE);
                lv_searchedCourses.setVisibility(View.VISIBLE);

                searchCode += String.valueOf(et_courseCode.getText());
                searchName += String.valueOf(et_courseName.getText());
                searchDay += String.valueOf(et_courseDay.getText());
                ShowSearchedCoursesOnListView(courseDatabaseHelper);
                break;
        }
    }

    private void ShowAllCoursesOnListView(CourseDatabaseHelper courseDatabaseHelper) {

        allCoursesArrayAdapter = new ArrayAdapter<CourseModel>(StudentHome.this, android.R.layout.simple_list_item_1, courseDatabaseHelper.getCourses());
        lv_allCourses.setAdapter(allCoursesArrayAdapter);
    }

    private void ShowMyCoursesOnListView(CourseDatabaseHelper courseDatabaseHelper) {

        myCoursesArrayAdapter = new ArrayAdapter<CourseModel>(StudentHome.this, android.R.layout.simple_list_item_1, courseDatabaseHelper.getStudentCourses(currentId));
        lv_myCourses.setAdapter(myCoursesArrayAdapter);
    }

    private void ShowSearchedCoursesOnListView(CourseDatabaseHelper courseDatabaseHelper) {

        searchedCoursesArrayAdapter = new ArrayAdapter<CourseModel>(StudentHome.this, android.R.layout.simple_list_item_1, courseDatabaseHelper.getSearchedCourses(searchCode, searchName, searchDay));
        lv_searchedCourses.setAdapter(searchedCoursesArrayAdapter);
        searchCode = "";
        searchName = "";
        searchDay = "";
    }
}