package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Arrays;

public class InstructorCourseOptions extends AppCompatActivity implements View.OnClickListener {
    Switch s_monday, s_tuesday, s_wednesday, s_thursday, s_friday;
    EditText et_mondayStart, et_mondayEnd, et_tuesdayStart, et_tuesdayEnd, et_wednesdayStart,
            et_wednesdayEnd, et_thursdayStart, et_thursdayEnd, et_fridayStart, et_fridayEnd, et_courseDescription, et_courseCapacity;
    Button btn_confirm, btn_unAssign;
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

        s_monday = (Switch) findViewById(R.id.mondaySwitch);
        s_tuesday = (Switch) findViewById(R.id.tuesdaySwitch);
        s_wednesday = (Switch) findViewById(R.id.wednesdaySwitch);
        s_thursday = (Switch) findViewById(R.id.thursdaySwitch);
        s_friday = (Switch) findViewById(R.id.fridaySwitch);

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
        btn_unAssign = (Button) findViewById(R.id.unAssignMyselfButton);

        Bundle extras = getIntent().getExtras();  // getting currentId and selectedCourseId from InstructorHome
        if (extras != null){ currentInstructorId = extras.getInt("currentId"); selectedCourseId = extras.getInt("selectedCourseId"); }

        setClickListeners();
    }

    private void setClickListeners() {
        for(Button button : Arrays.asList(btn_confirm, btn_unAssign)){
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.assignMyselfButton:
                mondayIsChecked = s_monday.isChecked();
                tuesdayIsChecked = s_tuesday.isChecked();
                wednesdayIsChecked = s_wednesday.isChecked();
                thursdayIsChecked = s_thursday.isChecked();
                fridayIsChecked = s_friday.isChecked();

                daysAndHours = daysAndHoursToString(mondayIsChecked, tuesdayIsChecked, wednesdayIsChecked, thursdayIsChecked, fridayIsChecked);

                try {
                    courseCapacity = Integer.parseInt(et_courseCapacity.getText().toString());
                }
                catch(Exception e) {
                    Toast.makeText(InstructorCourseOptions.this, "Please enter a valid course capacity.", Toast.LENGTH_SHORT).show();
                }

                courseDescription = et_courseDescription.getText().toString();
                instructorName = instructorDatabaseHelper.getInstructorName(currentInstructorId);

                if (daysAndHours == "error") {
                    Toast.makeText(InstructorCourseOptions.this, "Please enter a start time and end time for the selected days.", Toast.LENGTH_SHORT).show();
                }else if (daysAndHours == ""){
                    Toast.makeText(InstructorCourseOptions.this, "Please select at least one day.", Toast.LENGTH_SHORT).show();
                }else if (courseCapacity <= 0){
                    Toast.makeText(InstructorCourseOptions.this, "Please enter a valid course capacity.", Toast.LENGTH_SHORT).show();
                }else if (courseDescription == ""){
                    Toast.makeText(InstructorCourseOptions.this, "Please enter a course description.", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(InstructorCourseOptions.this, "Updated course information", Toast.LENGTH_SHORT).show();

                    System.out.println("Updated course information" +
                            selectedCourseId + "/ instructorName = " + instructorName + "/ currentInstructorId = " + currentInstructorId + "/ daysAndHours = " +
                            daysAndHours + "/ courseDescription = " + courseDescription + "/ courseCapacity = " + courseCapacity);

                    courseDatabaseHelper.removeInstructor(selectedCourseId);
                    courseDatabaseHelper.updateCourseInfo(selectedCourseId, instructorName, currentInstructorId, daysAndHours, courseDescription, courseCapacity);
                }
                break;
            case R.id.unAssignMyselfButton:
                courseDatabaseHelper.removeInstructor(selectedCourseId);
                Toast.makeText(this, "You were successfully un assigned from the course.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private String daysAndHoursToString(Boolean mondayIsChecked, Boolean tuesdayIsChecked, Boolean wednesdayIsChecked, Boolean thursdayIsChecked, Boolean fridayIsChecked){
        if(mondayIsChecked){
            String start = et_mondayStart.getText().toString();
            String end = et_mondayEnd.getText().toString();
            if (start.equals("") || end.equals("")) {
                return "error";
            }else{
                daysAndHours += "Monday/" + start + " to " + end + "/";
            }
        }

        if(tuesdayIsChecked){
            String start = et_tuesdayStart.getText().toString();
            String end = et_tuesdayEnd.getText().toString();
            if (start.equals("") || end.equals("")) {
                return "error";
            }else{
                daysAndHours += "Tuesday/" + start + " to " + end + "/";
            }
        }

        if(wednesdayIsChecked){
            String start = et_wednesdayStart.getText().toString();
            String end = et_wednesdayEnd.getText().toString();
            if (start.equals("") || end.equals("")) {
                return "error";
            }else{
                daysAndHours += "Wednesday/" + start + " to " + end + "/";
            }
        }

        if(thursdayIsChecked){
            String start = et_thursdayStart.getText().toString();
            String end = et_thursdayEnd.getText().toString();
            if (start.equals("") || end.equals("")) {
                return "error";
            }else{
                daysAndHours += "Thursday/" + start + " to " + end + "/";
            }
        }

        if(fridayIsChecked) {
            String start = et_fridayStart.getText().toString();
            String end = et_fridayEnd.getText().toString();
            if (start.equals("") || end.equals("")) {
                return "error";
            }else{
                daysAndHours += "Friday/" + start + " to " + end + "/";
            }
        }
        return daysAndHours;
    }
}