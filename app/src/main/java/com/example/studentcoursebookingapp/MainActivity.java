package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        Button createAccount = (Button) findViewById(R.id.createAccount);
        Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {                                                              // when the <Button login> is clicked
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin123")){     // if the <TextView username> is admin <TextView password> is admin123
                    //correct
                    Toast.makeText(MainActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();     // notify "LOGIN SUCCESSFUL"
                    setContentView(R.layout.activity_admin_home);                                                          // bring to AdminHome screen
                }else{                                                                                                     // if not
                    //incorrect
                    Toast.makeText(MainActivity.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();         // notify "LOGIN FAILED"
                }
            }
        });
    }
}