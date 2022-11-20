package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Arrays;

public class InstructorCourseOptions extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    ToggleButton tb_monday, tb_tuesday, tb_wednesday, tb_thursday, tb_friday;
    EditText et_mondayStart, et_mondayEnd, et_tuesdayStart, et_tuesdayEnd, et_wednesdayStart,
            et_wednesdayEnd, et_thursdayStart, et_thursdayEnd, et_fridayStart, et_fridayEnd, et_courseDescription, et_courseCapacity;
    Button btn_confirm;
    Boolean mondayIsChecked = false, tuesdayIsChecked = false, wednesdayIsChecked = false, thursdayIsChecked = false, fridayIsChecked = false;

    String daysAndHours = "";
    int courseCapacity = 0;
    String courseDescription;
    int selectedCourseId = -1;
    int currentInstructorId;
    String instructorName;

    CourseDatabaseHelper courseDatabaseHelper = new CourseDatabaseHelper(this);
    InstructorDatabaseHelper instructorDatabaseHelper = new InstructorDatabaseHelper(this);
    InstructorHome instructorHome = new InstructorHome();
    MainActivity mainActivity = new MainActivity();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_course_options);

        tb_monday = (ToggleButton) findViewById(R.id.mondayToggleButton);
        tb_tuesday = (ToggleButton) findViewById(R.id.tuesdayToggleButton);
        tb_wednesday = (ToggleButton) findViewById(R.id.wednesdayToggleButton);
        tb_thursday = (ToggleButton) findViewById(R.id.thursdayToggleButton);
        tb_friday = (ToggleButton) findViewById(R.id.fridayToggleButton);

        et_mondayStart = (EditText) findViewById(R.id.mondayStartEditText);
        et_mondayEnd = (EditText) findViewById(R.id.mondayEndEditText);
        et_tuesdayStart = (EditText) findViewById(R.id.tuesdayStartEditText);
        et_tuesdayEnd = (EditText) findViewById(R.id.tuesdayEndEditText);
        et_wednesdayStart = (EditText) findViewById(R.id.wednesdayStartEditText);
        et_wednesdayEnd = (EditText) findViewById(R.id.wednesdayEndEditText);
        et_thursdayStart = (EditText) findViewById(R.id.thursdayStartEditText);
        et_thursdayEnd = (EditText) findViewById(R.id.thursdayEndEditText);
        et_fridayStart = (EditText) findViewById(R.id.fridayStartEditText);
        et_fridayEnd = (EditText) findViewById(R.id.fridayEndEditText);

        et_courseDescription = (EditText) findViewById(R.id.courseDescriptionEditText);

        et_courseCapacity = (EditText) findViewById(R.id.courseCapacityEditText);

        btn_confirm = (Button) findViewById(R.id.assignMyselfButton);
        setOnCheckedChangeListeners();

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daysAndHours = daysAndHoursToString(mondayIsChecked, tuesdayIsChecked, wednesdayIsChecked, thursdayIsChecked, fridayIsChecked);
                courseCapacity = Integer.parseInt(et_courseCapacity.getText().toString());
                courseDescription = et_courseDescription.getText().toString();

                Bundle extras = getIntent().getExtras();  // getting currentId and selectedCourseId from InstructorHome
                if (extras != null){ currentInstructorId = extras.getInt("currentId"); selectedCourseId = extras.getInt("selectedCourseId"); }

                // currentId = mainActivity.getCurrentId();
                instructorName = instructorDatabaseHelper.getInstructorName(currentInstructorId);
                // selectedCourseId = instructorHome.getSelectedCourseId();

                if (daysAndHours == "error") {
                    Toast.makeText(InstructorCourseOptions.this, "Please enter a start time and end time for the selected days.", Toast.LENGTH_SHORT).show();
                }else if (daysAndHours == ""){
                    Toast.makeText(InstructorCourseOptions.this, "Please select at least one day.", Toast.LENGTH_SHORT).show();
                }else if (courseCapacity == 0){
                    Toast.makeText(InstructorCourseOptions.this, "Please enter a course capacity.", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(InstructorCourseOptions.this, "Updated course information with the following: selectedCourseId = " +
                            selectedCourseId + "/ instructorName = " + instructorName + "/ currentInstructorId = " + currentInstructorId + "/ daysAndHours = " +
                            daysAndHours + "/ courseDescription = " + courseDescription + "/ courseCapacity = " + courseCapacity, Toast.LENGTH_SHORT).show();

                    System.out.println("Updated course information with the following: selectedCourseId = " +
                            selectedCourseId + "/ instructorName = " + instructorName + "/ currentInstructorId = " + currentInstructorId + "/ daysAndHours = " +
                            daysAndHours + "/ courseDescription = " + courseDescription + "/ courseCapacity = " + courseCapacity);

                    courseDatabaseHelper.removeInstructor(selectedCourseId);


                    courseDatabaseHelper.updateCourseInfo(selectedCourseId, instructorName, currentInstructorId, daysAndHours, courseDescription, courseCapacity);
                }
            }
        });
    }

    private void setOnCheckedChangeListeners() {
        for (ToggleButton toggleButton : Arrays.asList(tb_monday, tb_tuesday, tb_wednesday, tb_wednesday, tb_friday)){
            toggleButton.setOnCheckedChangeListener(this);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.mondayToggleButton:
                mondayIsChecked = !mondayIsChecked;
                break;
            case R.id.tuesdayToggleButton:
                tuesdayIsChecked = !tuesdayIsChecked;
                break;
            case R.id.wednesdayToggleButton:
                wednesdayIsChecked = !wednesdayIsChecked;
                break;
            case R.id.thursdayToggleButton:
                thursdayIsChecked = !thursdayIsChecked;
                break;
            case R.id.fridayToggleButton:
                fridayIsChecked = !fridayIsChecked;
                break;
        }
    }

    private String daysAndHoursToString(Boolean mondayIsChecked, Boolean tuesdayIsChecked, Boolean wednesdayIsChecked, Boolean thursdayIsChecked, Boolean fridayIsChecked){
        if(mondayIsChecked){
            String start = et_mondayStart.getText().toString();
            String end = et_mondayEnd.getText().toString();
            if (start != null && end != null) {
                daysAndHours += "Monday from " + start + " to " + end + ". ";
            }else{
                return "error";
            }
        }

        if(tuesdayIsChecked){
            String start = et_tuesdayStart.getText().toString();
            String end = et_tuesdayEnd.getText().toString();
            if (start != null && end != null) {
                daysAndHours += "Tuesday from " + start + " to " + end + ". ";
            }else{
                return "error";
            }
        }

        if(wednesdayIsChecked){
            String start = et_wednesdayStart.getText().toString();
            String end = et_wednesdayEnd.getText().toString();
            if (start != null && end != null) {
                daysAndHours += "Wednesday from " + start + " to " + end + ". ";
            }else{
                return "error";
            }
        }

        if(thursdayIsChecked){
            String start = et_thursdayStart.getText().toString();
            String end = et_thursdayEnd.getText().toString();
            if (start != null && end != null) {
                daysAndHours += "Thursday from " + start + " to " + end + ". ";
            }else{
                return "error";
            }
        }

        if(fridayIsChecked) {
            String start = et_fridayStart.getText().toString();
            String end = et_fridayEnd.getText().toString();
            if (start != null && end != null) {
                daysAndHours += "Friday from " + start + " to " + end + ". ";
            } else {
                return "error";
            }
        }

        return daysAndHours;
    }
}