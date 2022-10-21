package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;

public class StudentOrInstructor extends AppCompatActivity implements View.OnClickListener {
    Button studentBtn, instructorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_or_instructor);

        studentBtn = (Button) findViewById(R.id.studentBtn);
        instructorBtn = (Button) findViewById(R.id.instructorBtn);

        setClickListeners();
    }

    private void setClickListeners() {
        for (Button button : Arrays.asList(studentBtn, instructorBtn)) {
            button.setOnClickListener(this);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.studentBtn:
                openStudentAccountCreator();
                Toast.makeText(this, "STUCK AT STUDENT", Toast.LENGTH_SHORT).show();   // DEBUG
                break;
            case R.id.instructorBtn:
                Toast.makeText(this, "STUCK AT INSTRUCTOR", Toast.LENGTH_SHORT).show();
                openInstructorAccountCreator();
                break;
        }
    }

    public void openStudentAccountCreator() {
        Toast.makeText(this, "FUNCTION DID NOTHING", Toast.LENGTH_SHORT).show();    // DEBUG

        Intent intent = new Intent(this, StudentAccountCreator.class);
        startActivity(intent);
    }

    public void openInstructorAccountCreator(){
        Intent intent = new Intent(this, InstructorAccountCreator.class);
        startActivity(intent);
    }
}