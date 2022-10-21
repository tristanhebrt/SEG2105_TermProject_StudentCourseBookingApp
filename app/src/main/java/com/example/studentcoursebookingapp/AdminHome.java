package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;

public class AdminHome extends AppCompatActivity implements View.OnClickListener {
    Button createCourse, editCourse, deleteCourse, deleteAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        createCourse = (Button) findViewById(R.id.createCourse);
        editCourse = (Button) findViewById(R.id.editCourse);
        deleteCourse = (Button) findViewById(R.id.deleteCourse);
        deleteAccounts = (Button) findViewById(R.id.deleteAccounts);

        setClickListeners();
    }

    private void setClickListeners() {
        for (Button button : Arrays.asList(createCourse, editCourse, deleteCourse,
                deleteAccounts)) {
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
            case R.id.deleteAccounts:
                Toast.makeText(AdminHome.this, "DELETE ACCOUNT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}