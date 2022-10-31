package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;

public class InstructorAccountCreator extends AppCompatActivity {

    // references
    EditText et_firstName, et_lastName, et_email, et_username, et_password;
    Button btn_createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_account_creator);

        et_firstName = findViewById(R.id.firstName);
        et_lastName = findViewById(R.id.lastName);
        et_email = findViewById(R.id.email);
        et_username = findViewById(R.id.username);
        et_password = findViewById(R.id.password);

        btn_createAccount = (Button) findViewById(R.id.createAccount);

        // Button listener
        btn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InstructorModel instructorModel = new InstructorModel(-1, et_firstName.getText().toString(), et_lastName.getText().toString(),
                            et_email.getText().toString(), et_username.getText().toString(), et_password.getText().toString());
                    Toast.makeText(InstructorAccountCreator.this, "ACCOUNT CREATED", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(InstructorAccountCreator.this, "INVALID INFORMATION", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}