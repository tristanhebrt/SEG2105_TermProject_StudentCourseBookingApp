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
import android.widget.AdapterView.OnItemClickListener;

import java.util.Arrays;
import java.util.List;

public class AdminHome extends AppCompatActivity implements View.OnClickListener {
    Button btn_createCourse, btn_editCourse, btn_deleteCourse, btn_viewInstructors, btn_viewStudents;
    ListView lv_instructorList;
    DatabaseHelper databaseHelper;
    ArrayAdapter instructorArrayAdapter;

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

        lv_instructorList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                InstructorModel clickedInstructor = (InstructorModel) parent.getItemAtPosition(position);
                databaseHelper.deleteInstructorAccount(clickedInstructor);
                ShowInstructorsOnListView(databaseHelper);
                Toast.makeText(AdminHome.this, "Deleted instructor " + clickedInstructor.toString(), Toast.LENGTH_SHORT).show();
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

                databaseHelper = new DatabaseHelper(AdminHome.this);
                ShowInstructorsOnListView(databaseHelper);

                break;
            case R.id.viewStudents:
                Toast.makeText(AdminHome.this, "VIEW STUDENTS SUCCESSFUL", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void ShowInstructorsOnListView(DatabaseHelper databaseHelper) {
        instructorArrayAdapter = new ArrayAdapter<InstructorModel>(AdminHome.this, android.R.layout.simple_list_item_1, databaseHelper.getEveryone());
        lv_instructorList.setAdapter(instructorArrayAdapter);
    }
}