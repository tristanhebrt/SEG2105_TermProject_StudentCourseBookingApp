package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView username, password;
    Button createAccount, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);

        createAccount = (Button) findViewById(R.id.createAccount);
        login = (Button) findViewById(R.id.login);

        setClickListeners();
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
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin123")) {     // if the <TextView username> is admin <TextView password> is admin123
                    //correct
                    Toast.makeText(MainActivity.this, "Welcome! You are logged in as Admin", Toast.LENGTH_SHORT).show();     // notify "LOGIN SUCCESSFUL"
                    openAdminHome();
                } else {                                                                                                     // if not
                    //incorrect
                    Toast.makeText(MainActivity.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();         // notify "LOGIN FAILED"
                }
                break;
        }
    }

    public void openAdminHome() {
        Intent intent = new Intent(this, AdminHome.class);
        startActivity(intent);
    }

    public void openStudentOrInstructor(){
        Intent intent = new Intent(this, StudentOrInstructor.class);
        startActivity(intent);
    }
}