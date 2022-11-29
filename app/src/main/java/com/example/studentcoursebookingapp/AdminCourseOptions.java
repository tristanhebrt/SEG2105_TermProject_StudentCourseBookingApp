package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.util.Arrays;

public class AdminCourseOptions extends AppCompatActivity implements View.OnClickListener {
    Button btn_createCourse, btn_deleteCourse, btn_editCourse, btn_cancel;
    EditText et_courseCode, et_courseName;
    ListView lv_courseList;
    CourseDatabaseHelper courseDatabaseHelper;
    CourseDatabaseHelper courseDatabaseHelper2;
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

        lv_courseList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                CourseModel clickedCourse = (CourseModel) parent.getItemAtPosition(position);

                et_courseCode.setText(String.valueOf(clickedCourse.getCourseCode()));
                et_courseCode.isEnabled();
                et_courseName.setText(clickedCourse.getCourseName());
                et_courseName.isEnabled();
            }
        });

        courseDatabaseHelper = new CourseDatabaseHelper(AdminCourseOptions.this);
        courseDatabaseHelper2 = new CourseDatabaseHelper(AdminCourseOptions.this);

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

                CourseModel courseModel;

                try {
                    courseModel = new CourseModel(-1, et_courseCode.getText().toString(), et_courseName.getText().toString(), "",
                            -1, "", "", -1, "");
                    Toast.makeText(AdminCourseOptions.this, "Course created", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(AdminCourseOptions.this, "Invalid information", Toast.LENGTH_SHORT).show();
                    courseModel = new CourseModel(-1, "", "", "", -1,
                            "", "", -1, "");
                }

                if (courseDatabaseHelper.createCourse(courseModel)) { }
                else {
                    Toast.makeText(this, "Error has occurred please try again.", Toast.LENGTH_SHORT).show();
                }

                ShowCoursesOnListView(courseDatabaseHelper); // Updates the course list

                break;



            case R.id.deleteCourse:
                String courseCode = String.valueOf(et_courseCode.getText());
                String courseName = String.valueOf(et_courseName.getText());

                Cursor data = courseDatabaseHelper2.getCourseID(courseCode, courseName);

                int courseID = -1;

                while(data.moveToNext()) courseID = data.getInt(0);

                if(courseID > -1){
                    courseDatabaseHelper2.deleteCourse(courseID);
                    ShowCoursesOnListView(courseDatabaseHelper2);
                }

                Toast.makeText(this, "Deleted " + courseCode + " " + courseName, Toast.LENGTH_SHORT).show();
                break;

            case R.id.editCourse:
                Intent intent = new Intent(this, AdminEditCourse.class);
                intent.putExtra("courseCode", String.valueOf(et_courseCode.getText()));
                intent.putExtra("courseName", String.valueOf(et_courseName.getText()));

                startActivity(intent);
                break;

            case R.id.cancelCourseOptions:
                openAdminHome();
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