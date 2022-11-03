package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

public class AdminHome extends AppCompatActivity implements View.OnClickListener {
    Button btn_createCourse, btn_editCourse, btn_deleteCourse, btn_viewInstructors, btn_viewStudents;
    ListView lv_instructorList, lv_studentList;
    InstructorDatabaseHelper instructorDatabaseHelper;
    StudentDatabaseHelper studentDatabaseHelper;
    ArrayAdapter instructorArrayAdapter, studentArrayAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btn_createCourse = (Button) findViewById(R.id.createCourse);
        btn_editCourse = (Button) findViewById(R.id.editCourse);
        btn_deleteCourse = (Button) findViewById(R.id.deleteCourse);
        btn_viewInstructors = (Button) findViewById(R.id.viewInstructors);
        btn_viewStudents = (Button) findViewById(R.id.viewStudents);
        setClickListeners();

        lv_instructorList = (ListView) findViewById(R.id.instructorList);
        lv_studentList = (ListView) findViewById(R.id.studentList);

        lv_instructorList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                InstructorModel clickedInstructor = (InstructorModel) parent.getItemAtPosition(position);
                instructorDatabaseHelper.deleteInstructorAccount(clickedInstructor);
                ShowInstructorsOnListView(instructorDatabaseHelper);
                Toast.makeText(AdminHome.this, "Deleted instructor " + clickedInstructor.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        lv_studentList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                StudentModel clickedStudent = (StudentModel) parent.getItemAtPosition(position);
                studentDatabaseHelper.deleteStudentAccount(clickedStudent);
                ShowStudentsOnListView(studentDatabaseHelper);
                Toast.makeText(AdminHome.this, "Deleted student " + clickedStudent.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setClickListeners() {
        for (Button button : Arrays.asList(btn_createCourse, btn_editCourse, btn_deleteCourse,
                btn_viewInstructors, btn_viewStudents)) {
            button.setOnClickListener(this);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createCourse:
                Toast.makeText(AdminHome.this, "CREATE COURSE SUCCESSFUL", Toast.LENGTH_SHORT).show();
                break;
            case R.id.editCourse:
                Toast.makeText(AdminHome.this, "EDIT COURSE SUCCESSFUL", Toast.LENGTH_SHORT).show();
                break;
            case R.id.deleteCourse:
                Toast.makeText(AdminHome.this, "DELETE COURSE SUCCESSFUL", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewInstructors:
                lv_studentList.setVisibility(View.GONE);
                lv_instructorList.setVisibility(View.VISIBLE);
                instructorDatabaseHelper = new InstructorDatabaseHelper(AdminHome.this);
                ShowInstructorsOnListView(instructorDatabaseHelper);

                break;
            case R.id.viewStudents:
                lv_instructorList.setVisibility(View.GONE);
                lv_studentList.setVisibility(View.VISIBLE);
                studentDatabaseHelper = new StudentDatabaseHelper(AdminHome.this);
                ShowStudentsOnListView(studentDatabaseHelper);
                break;
        }

    }

    private void ShowInstructorsOnListView(InstructorDatabaseHelper instructorDatabaseHelper) {

        instructorArrayAdapter = new ArrayAdapter<InstructorModel>(AdminHome.this, android.R.layout.simple_list_item_1, instructorDatabaseHelper.getInstructors());
        lv_instructorList.setAdapter(instructorArrayAdapter);
    }

    private void ShowStudentsOnListView(StudentDatabaseHelper studentDatabaseHelper) {

        studentArrayAdapter = new ArrayAdapter<StudentModel>(AdminHome.this, android.R.layout.simple_list_item_1, studentDatabaseHelper.getStudents());
        lv_studentList.setAdapter(studentArrayAdapter);
    }
}