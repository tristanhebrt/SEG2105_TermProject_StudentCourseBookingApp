package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView username, password;
    Button createAccount, login;
    public int currentID;
    public boolean isStudent;
    public boolean isInstructor;
    public boolean isAdmin;
    StudentDatabaseHelper studentDatabaseHelper;
    InstructorDatabaseHelper instructorDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        isStudent = false;
        isInstructor = false;
        isAdmin = false;

        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);

        createAccount = (Button) findViewById(R.id.createAccount);
        login = (Button) findViewById(R.id.login);

        setClickListeners();
        studentDatabaseHelper = new StudentDatabaseHelper(MainActivity.this);
        instructorDatabaseHelper = new InstructorDatabaseHelper(MainActivity.this);
    }

    private void setClickListeners() {
        for (Button button : Arrays.asList(createAccount, login)) {
            button.setOnClickListener(this);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createAccount:
                Toast.makeText(this, "CREATE ACCOUNT", Toast.LENGTH_SHORT).show();
                openStudentOrInstructor();
                break;

            case R.id.login:
                String inputUsername = username.getText().toString();
                String inputPassword = password.getText().toString();

                if (inputUsername.equals("admin") && inputPassword.equals("admin123")) {     // if the <TextView username> is admin <TextView password> is admin123
                    Toast.makeText(MainActivity.this, "Welcome Admin! You are logged in as Admin", Toast.LENGTH_SHORT).show();     // notify "Welcome! You are logged in as Admin"
                    isAdmin = true;
                    openAdminHome();
                } else if (studentDatabaseHelper.checkStudentAccount(inputUsername, inputPassword)) {

                    Cursor data = studentDatabaseHelper.getStudentID(inputUsername, inputPassword);
                    int courseID = -1;
                    while(data.moveToNext()) courseID = data.getInt(0);
                    if(courseID > -1){
                        currentID = courseID;
                        Toast.makeText(this, "current ID is " + currentID, Toast.LENGTH_SHORT).show(); // debug
                    }

                    String firstName = "student"; // need to implement firstName getter

                    Toast.makeText(MainActivity.this, "Welcome" + firstName + " / " + inputUsername + "! You are logged in as a student" , Toast.LENGTH_SHORT).show();
                    isStudent = true;
                    openStudentHome();

                } else if (instructorDatabaseHelper.checkInstructorAccount(inputUsername, inputPassword)) {

                    Cursor data = instructorDatabaseHelper.getInstructorID(inputUsername, inputPassword);
                    int courseID = -1;
                    while(data.moveToNext()) courseID = data.getInt(0);
                    if(courseID > -1){
                        currentID = courseID;
                        Toast.makeText(this, "current ID is " + currentID, Toast.LENGTH_SHORT).show();  // debug
                    }

                    String firstName = "instructor"; // need to implement firstName getter

                    Toast.makeText(MainActivity.this, "Welcome" + firstName + " / " + inputUsername + "! You are logged in as a student" , Toast.LENGTH_SHORT).show();
                    isInstructor = true;
                    openInstructorHome();

                } else {
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public Integer getCurrentId(){
        return currentID;
    }

    public void openAdminHome() {
        Intent intent = new Intent(this, AdminHome.class);
        startActivity(intent);
    }

    public void openStudentHome() {
        Intent intent = new Intent(this, StudentHome.class);
        intent.putExtra("currentId", currentID);
        startActivity(intent);
    }

    public void openInstructorHome() {
        Intent intent = new Intent(this, InstructorHome.class);
        intent.putExtra("currentId", currentID);
        startActivity(intent);
    }

    public void openStudentOrInstructor(){
        Intent intent = new Intent(this, StudentOrInstructor.class);
        startActivity(intent);
    }
}