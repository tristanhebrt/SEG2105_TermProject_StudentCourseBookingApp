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
import java.util.Objects;

public class InstructorCourseOptions extends InstructorHome implements CompoundButton.OnCheckedChangeListener {
    ToggleButton tb_monday, tb_tuesday, tb_wednesday, tb_thursday, tb_friday;
    EditText et_mondayStart, et_mondayEnd, et_tuesdayStart, et_tuesdayEnd, et_wednesdayStart,
            et_wednesdayEnd, et_thursdayStart, et_thursdayEnd, et_fridayStart, et_fridayEnd, et_courseDescription, et_courseCapacity;
    Button btn_confirm;
    Boolean mondayIsChecked = false, tuesdayIsChecked = false, wednesdayIsChecked = false, thursdayIsChecked = false, fridayIsChecked = false;

    String daysAndHours = "";
    int courseCapacity = 0;
    String courseDescription = "";
    public int selectedCourseId = -1;
    public int userId;
    String instructorName = "";

    CourseDatabaseHelper courseDatabaseHelper;
    InstructorDatabaseHelper instructorDatabaseHelper;
    MainActivity mainActivity;

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