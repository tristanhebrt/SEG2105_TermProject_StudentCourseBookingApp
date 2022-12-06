package com.example.studentcoursebookingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CourseDatabaseHelper extends SQLiteOpenHelper {

    MainActivity mainActivity = new MainActivity();
    StudentDatabaseHelper studentDatabaseHelper;

    public static final String TABLE_NAME = "COURSE_TABLE";
    public static final String COLUMN_COURSE_ID = "ID";

    public static final String COLUMN_COURSE_CODE = "COURSE_CODE";
    public static final String COLUMN_COURSE_NAME = "COURSE_NAMETEXT";

    public static final String COLUMN_COURSE_INSTRUCTOR = "COURSE_INSTRUCTOR";
    public static final String COLUMN_COURSE_INSTRUCTOR_ID = "COURSE_INSTRUCTOR_ID";

    public static final String COLUMN_COURSE_DAYS_AND_HOURS = "COURSE_DAYS_AND_HOURS";
    public static final String COLUMN_COURSE_DESCRIPTION = "COURSE_DESCRIPTION";

    public static final String COLUMN_COURSE_CAPACITY = "COURSE_CAPACITY";
    public static final String COLUMN_COURSE_ENROLLED = "COURSE_ENROLLED";

    public CourseDatabaseHelper(@Nullable Context context) {
        super(context, "course.db", null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + COLUMN_COURSE_CODE + " INTEGER, "
                + COLUMN_COURSE_NAME + " TEXT, "

                + COLUMN_COURSE_INSTRUCTOR + " TEXT, "
                + COLUMN_COURSE_INSTRUCTOR_ID + " INTEGER, "

                + COLUMN_COURSE_DAYS_AND_HOURS + " TEXT, "
                + COLUMN_COURSE_DESCRIPTION + " TEXT, "

                + COLUMN_COURSE_CAPACITY + " INTEGER, "
                + COLUMN_COURSE_ENROLLED + " TEXT )";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // drop older table

        onCreate(sqLiteDatabase); // create table again
    }

    public boolean createCourse(CourseModel courseModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(String.valueOf(COLUMN_COURSE_CODE), courseModel.getCourseCode());
        cv.put(COLUMN_COURSE_NAME, courseModel.getCourseName());

        long insert = db.insert(TABLE_NAME, null, cv);
        return insert != -1;
    }

    public boolean deleteCourse(int courseID){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_ID + " = '" + courseID + "'";

        Cursor cursor = db.rawQuery(queryString, null);
        return cursor.moveToFirst();
    }

    public void removeInstructor(int courseID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " +
                TABLE_NAME + " SET " +
                COLUMN_COURSE_INSTRUCTOR + " = '" + "" + "' , " +
                COLUMN_COURSE_INSTRUCTOR_ID + " = " + -1 + " , " +
                COLUMN_COURSE_DAYS_AND_HOURS + " = '" + "" + "' , " +
                COLUMN_COURSE_DESCRIPTION + " = '" + "" + "' , " +
                COLUMN_COURSE_ENROLLED + " = '" + "" + "' , " +
                COLUMN_COURSE_CAPACITY + " = " + -1 + " WHERE " +
                COLUMN_COURSE_ID + " = '" + courseID + "' ";

        db.execSQL(query);
    }

    public List<String> getStudentsInCourse(int selectedCourseId){
        List<String> returnList = new ArrayList<>();

        // get data from database
        String queryString = " SELECT * FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_ID + " = '" + selectedCourseId + "' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String courseEnrolled = cursor.getString(8);

                if (courseEnrolled != null) {
                    List<String> enrolledStudents = new ArrayList<String>(Arrays.asList(courseEnrolled.split("/")));

                    for (int i = 1; i < enrolledStudents.size(); i++){  // go through the enrolled students list

                        int studentId = Integer.parseInt(enrolledStudents.get(i));
                        System.out.println("studentId :" + studentId);

                        try {
                            studentDatabaseHelper = new StudentDatabaseHelper(null);

                            String studentInfo = studentDatabaseHelper.getStudentInfo(studentId);

                            System.out.println("studentInfo :" + studentInfo);

                            returnList.add(studentInfo + "Student ID : " + studentId);

                        }catch (Exception e){
                            System.out.println("error trying studentDatabaseHelper.getStudentInfo(studentId)");
                        }
                    }
                }
            }while (cursor.moveToNext());

        }else{
            // failed
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<CourseModel> getCourses(){
        List<CourseModel> returnList = new ArrayList<>();

        // get data from database
        String queryString = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int courseId = cursor.getInt(0);

                String courseCode = cursor.getString(1);
                String courseName = cursor.getString(2);

                String courseInstructor = cursor.getString(3);
                int courseInstructorId = cursor.getInt(4);

                String courseDaysAndHours = cursor.getString(5);

                String courseDescription = cursor.getString(6);

                int courseCapacity = cursor.getInt(7);

                String courseEnrolled = cursor.getString(8);


                CourseModel newCourse = new CourseModel(courseId, courseCode, courseName, courseInstructor, courseInstructorId, courseDaysAndHours,
                        courseDescription, courseCapacity, courseEnrolled);
                returnList.add(newCourse);

            }while (cursor.moveToNext());
        }else{
            // failed
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<CourseModel> getMyCourses(int currentId){
        List<CourseModel> returnList = new ArrayList<>();

        // get data from database

        String queryString = " SELECT * FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_INSTRUCTOR_ID + " = '" + currentId + "' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int courseId = cursor.getInt(0);
                String courseCode = cursor.getString(1);
                String courseName = cursor.getString(2);

                String courseInstructor = cursor.getString(3);
                int courseInstructorId = cursor.getInt(4);

                String courseDaysAndHours = cursor.getString(5);

                String courseDescription = cursor.getString(6);

                int courseCapacity = cursor.getInt(7);
                String courseEnrolled = cursor.getString(8);

                CourseModel newCourse = new CourseModel(courseId, courseCode, courseName, courseInstructor, courseInstructorId, courseDaysAndHours,
                        courseDescription, courseCapacity, courseEnrolled);
                returnList.add(newCourse);

            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
        return returnList;
    }

    public List<CourseModel> studentGetAllCourses(){
        List<CourseModel> returnList = new ArrayList<>();
        List<String> returnListString = new ArrayList<>();

        // get data from database
        String queryString = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_COURSE_INSTRUCTOR_ID + " <> 0 ";  // only select courses with an instructor

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int courseId = cursor.getInt(0);

                String courseCode = cursor.getString(1);
                String courseName = cursor.getString(2);

                String courseInstructor = cursor.getString(3);
                int courseInstructorId = cursor.getInt(4);

                String courseDaysAndHours = cursor.getString(5);

                String courseDescription = cursor.getString(6);

                int courseCapacity = cursor.getInt(7);

                String courseEnrolled = cursor.getString(8);


                CourseModel newCourse = new CourseModel(courseId, courseCode, courseName, courseInstructor, courseInstructorId, courseDaysAndHours,
                        courseDescription, courseCapacity, courseEnrolled);
                returnList.add(newCourse);

                String newCourseString = "Course code : " + courseCode +
                        "\nCourse name : " + courseName +
                        "\nCourse instructor : " + courseInstructor +
                        "\nCourse days and hours : " + courseDaysAndHours +
                        "\nCourse description : " + courseDescription +
                        "\nCourse capacity : " + courseCapacity;
                returnListString.add(newCourseString);

            }while (cursor.moveToNext());
        }else{
            // failed
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<CourseModel> getStudentCourses(int currentId){
        List<CourseModel> returnList = new ArrayList<>();
        List<Integer> studentCoursesId = getCourseIdByStudentId(currentId);

        for (int i = 0; i < studentCoursesId.size(); i++) {

            String queryString = " SELECT * FROM " +
                    TABLE_NAME + " WHERE " +
                    COLUMN_COURSE_ID + " = '" + studentCoursesId.get(i) + "'";

            //  + "' AND '" + COLUMN_COURSE_DAYS_AND_HOURS + "' != null "


            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryString, null);

            if (cursor.moveToFirst()) {
                do {
                    int courseId = cursor.getInt(0);
                    String courseCode = cursor.getString(1);
                    String courseName = cursor.getString(2);

                    String courseInstructor = cursor.getString(3);
                    int courseInstructorId = cursor.getInt(4);

                    String courseDaysAndHours = cursor.getString(5);

                    String courseDescription = cursor.getString(6);

                    int courseCapacity = cursor.getInt(7);
                    String courseEnrolled = cursor.getString(8);

                    CourseModel newCourse = new CourseModel(courseId, courseCode, courseName, courseInstructor, courseInstructorId, courseDaysAndHours,
                            courseDescription, courseCapacity, courseEnrolled);
                    returnList.add(newCourse);

                } while (cursor.moveToNext());

            } else {
                // failure don't add anything
            }
            cursor.close();   // cleanup
            db.close();
        }
        return returnList;
    }

    public List<Integer> getCourseIdByStudentId(int studentId){
        List<Integer> returnList = new ArrayList<>();

        String queryString = " SELECT * FROM " +
                TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String courseEnrolled = cursor.getString(8);
                if (courseEnrolled != null) {
                    List<String> enrolledStudents = new ArrayList<String>(Arrays.asList(courseEnrolled.split("/")));
                    if (enrolledStudents.contains(Integer.toString(studentId))) {
                        returnList.add(cursor.getInt(0));
                    }
                }

            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
        return returnList;
    }

    public List<CourseModel> getSearchedCourses(String searchCode, String searchName, String searchDay){
        List<CourseModel> returnList = new ArrayList<>();  // Creates returnList of type CourseModel

        // get data from database

        int searchId;

        if (searchDay != ""){
            try {
                searchId = (getCourseIdByDay(searchDay));
            } catch (Exception e) {
                searchId = -1;
            }

        } else {
            searchId = -1;
        }


        String queryString = " SELECT * FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_CODE + " = '" + searchCode + "' OR " +
                COLUMN_COURSE_NAME + " = '" + searchName + "' OR " +
                COLUMN_COURSE_ID + " = '" + searchId + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int courseId = cursor.getInt(0);
                String courseCode = cursor.getString(1);
                String courseName = cursor.getString(2);

                String courseInstructor = cursor.getString(3);
                int courseInstructorId = cursor.getInt(4);

                String courseDaysAndHours = cursor.getString(5);

                String courseDescription = cursor.getString(6);

                int courseCapacity = cursor.getInt(7);
                String courseEnrolled = cursor.getString(8);

                CourseModel newCourse = new CourseModel(courseId, courseCode, courseName, courseInstructor, courseInstructorId, courseDaysAndHours,
                        courseDescription, courseCapacity, courseEnrolled);
                returnList.add(newCourse);

            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
        return returnList;
    }

    public int getCourseIdByDay(String searchCourseDay){
        searchCourseDay = searchCourseDay.substring(0,1).toUpperCase() + searchCourseDay.substring(1).toLowerCase();

        String queryString = " SELECT * FROM " +
                TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String courseDaysAndHours = cursor.getString(5);
                List<String> courseDays = new ArrayList<String>(Arrays.asList(courseDaysAndHours.split("/")));
                if (courseDays.contains(searchCourseDay)){ return cursor.getInt(0); }

            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
        return -1;
    }

    // Method to get a courses day, start time and end time ( returns a list that can contain more than one day )
    public List<String> getCourseDaysAndHoursList(int courseId){
        List<String> courseDaysAndHoursList = new ArrayList<>();
        List<String> courseDaysAndHoursFinalList = new ArrayList<>();

        String queryString = " SELECT * FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_ID + " = '" + courseId + "'";   // Defines where to look

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String courseDaysAndHours = cursor.getString(5);    // Select the COLUMN_COURSE_DAYS_AND_HOURS

                try {
                    courseDaysAndHoursList = Arrays.asList(courseDaysAndHours.split("/"));  // Split the days and hours into a list
                } catch (Exception e) {

                }

                for (int i = 0; i < courseDaysAndHoursList.size(); i++) {  // go through the course list
                    if (i % 2 == 0){  // if even (day)
                        try {
                            courseDaysAndHoursFinalList.add(courseDaysAndHoursList.get(i + 1));  // add day to final list
                        }catch (Exception e){

                        }
                    } else {  // if odd (hours)
                        String hours = courseDaysAndHoursList.get(i+1);   // turn hours into a String
                        String [] fromTo = hours.split(" to ", 2);  // split String at "to"

                        courseDaysAndHoursFinalList.add(fromTo[0]);  // add start hour to final list
                        courseDaysAndHoursFinalList.add(fromTo[1]);  // add end hour to final list
                    }
                }

            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }

        cursor.close();   // cleanup
        db.close();


        return courseDaysAndHoursFinalList;  // return list where day, start time and end time are separated
    }

    public boolean checkStudentCourseOverlap(int selectedCourseId, int studentId){
        // find course from selectedCourseId, select COLUMN_COURSE_DAYS_AND_HOURS from the selected course, turn into organised list
        List<String> selectedCourseDaysAndHoursList = new ArrayList<String>(getCourseDaysAndHoursList(selectedCourseId));


        int maxCourseId = 100;

        for (int i = 0; i < selectedCourseDaysAndHoursList.size(); i++) {  // go through the selected course day and time
            if (i % 3 == 0){  // go through the days of the selected course
                String selectedDayOfWeek = selectedCourseDaysAndHoursList.get(i);

                System.out.println("selected: " + (selectedCourseDaysAndHoursList.get(i)).toString());

                for (int courseId = 0; courseId < maxCourseId; courseId ++){  // go through all course ids
                    if(courseId != selectedCourseId) {
                        List<String> checkingDay = new ArrayList<>(getCourseDaysAndHoursList(courseId));  // get checked course day and time

                        for (int j = 0; j < checkingDay.size(); j++) {  // go through the day and time list
                            if (j % 3 == 0) {  // go through the days
                                double start = Double.parseDouble(checkingDay.get(j + 1));   // get class start time
                                double end = Double.parseDouble(checkingDay.get(j + 2));    // get class end time

                                String dayOfWeek = checkingDay.get(j); // declare an assign value to dayOfWeek
                                System.out.println("test overlap: " + selectedDayOfWeek + " compare to " + dayOfWeek);


                                if (selectedDayOfWeek.equals(dayOfWeek)) {  // if selected course has a class on the same day
                                    double selectedStart = Double.parseDouble(selectedCourseDaysAndHoursList.get(i + 1));  // get selected class start time
                                    double selectedEnd = Double.parseDouble(selectedCourseDaysAndHoursList.get(i + 2));   // get selected class end time
                                    System.out.println("selected: " + selectedStart + " : " + selectedEnd);


                                    System.out.println("test overlap: " + start + " : " + end);
                                    System.out.println(end + " < " + selectedStart + " || " + start + " > " +selectedEnd);

                                    if (end > selectedStart || start < selectedEnd) {  // if there is no overlap
                                        if (checkIfStudentIsEnrolled(studentId, courseId)) {  // if the student isn't enrolled in the overlapping class
                                            System.out.println(checkIfStudentIsEnrolled(studentId, courseId));
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("returning false");
        return false;
    }


    public Cursor getCourseID(String courseCode, String courseName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " +
                COLUMN_COURSE_ID + " FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_CODE + " = '" + courseCode + "' AND " +
                COLUMN_COURSE_NAME + " = '" + courseName + "' ";

        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public int getCourseInstructorId(int courseId){

        String queryString = " SELECT * FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_ID + " = " + courseId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int courseInstructorId = cursor.getInt(4);
                return courseInstructorId;
            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
        return -1;
    }

    public void updateCourseCode(int courseID, String newCourseCode){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " +
                TABLE_NAME + " SET " +
                COLUMN_COURSE_CODE + " = '" + newCourseCode + "' WHERE " +
                COLUMN_COURSE_ID + " = '" + courseID + "'";

        db.execSQL(query);
    }

    public void updateCourseName(int courseID, String newCourseName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " +
                TABLE_NAME + " SET " +
                COLUMN_COURSE_NAME + " = '" + newCourseName + "' WHERE " +
                COLUMN_COURSE_ID + " = '" + courseID + "'";

        db.execSQL(query);
    }

    public void updateCourseInfo(int courseID, String courseInstructor, int courseInstructorId, String courseDaysAndHours, String courseDescription, int courseCapacity){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " +
                TABLE_NAME + " SET " +
                COLUMN_COURSE_INSTRUCTOR + " = '" + courseInstructor + "' , " +
                COLUMN_COURSE_INSTRUCTOR_ID + " = '" + courseInstructorId + "' , " +
                COLUMN_COURSE_DAYS_AND_HOURS + " = '" + "/" + courseDaysAndHours + "' , " +
                COLUMN_COURSE_DESCRIPTION + " = '" + courseDescription + "' , " +
                COLUMN_COURSE_ENROLLED + " = '" + "/" + "' , " +
                COLUMN_COURSE_CAPACITY + " = '" + courseCapacity + "' WHERE " +
                COLUMN_COURSE_ID + " = '" + courseID + "' ";

        db.execSQL(query);
    }

    public boolean checkIfStudentIsEnrolled(int studentId, int courseId){
        List<String> enrolledStudentsList = new ArrayList<>();

        String queryString = " SELECT * FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_ID + " = '" + courseId + "'";   // Defines where to look

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String enrolledStudentsString = cursor.getString(8);    // Select the COLUMN_COURSE_ENROLLED
                enrolledStudentsList = Arrays.asList(enrolledStudentsString.split("/"));  // Split the student ids into a list
                if (enrolledStudentsList.contains(Integer.toString(studentId))){  // if student id is in the course's
                    return true;
                }

            }while (cursor.moveToNext());

        }else{
            // failure don't add anything
        }

        cursor.close();   // cleanup
        db.close();

        return false;
    }

    public boolean enrollStudent(int courseId, int studentId){

        String queryString = " SELECT * FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_ID + " = " + courseId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        String enrolled = "";
        if(cursor.moveToFirst()){
            do{
                if (!checkIfStudentIsEnrolled(studentId, courseId)) {  // if the student isn't already enrolled and there is no overlap
                    enrolled = cursor.getString(8);  // get the course's enrolled student ids String
                    enrolled += Integer.toString(studentId) + "/";  // add the current student's id to the course's enrollment String

                    SQLiteDatabase db2 = this.getReadableDatabase();
                    Cursor cursor2 = db2.rawQuery(queryString, null);

                    String queryString2 = " UPDATE " +
                            TABLE_NAME + " SET " +
                            COLUMN_COURSE_ENROLLED + " = '" + enrolled + "' WHERE " +
                            COLUMN_COURSE_ID + " = '" + courseId + "' ";
                    db2.execSQL(queryString2);  // replace old enrollment String with the new one

                    cursor2.close();   // cleanup
                    db2.close();

                    return true;
                }

            }while (cursor.moveToNext());


        }else{
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();

        return false;
    }

    public void unEnrollStudent(int courseId, int studentId) {

        String queryString = " SELECT * FROM " +
                TABLE_NAME + " WHERE " +
                COLUMN_COURSE_ID + " = " + courseId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String courseEnrolled = cursor.getString(8);  // Get all the enrolled students in the selected course as a String
                if (courseEnrolled != null) {
                    List<String> enrolledStudents = new ArrayList<String>(Arrays.asList(courseEnrolled.split("/")));  // Turn that String into a list
                    if (enrolledStudents.contains(Integer.toString(studentId))) {    // If the current student's id is in the list

                        enrolledStudents.remove(Integer.toString(studentId));   // Remove the current student's id from the list

                        String enrolledStudentsString = enrolledStudents.stream().collect(Collectors.joining("/", "", "/"));  // Turn the List into a String

                        String query = " UPDATE " +     // Replace the old course enrolled with the new one
                                TABLE_NAME + " SET " +
                                COLUMN_COURSE_ENROLLED + " = '" + enrolledStudentsString + "' WHERE " +
                                COLUMN_COURSE_ID + " = '" + courseId + "' ";
                        db.execSQL(query);

                    } else {

                    }
                }

            } while (cursor.moveToNext());

        } else {
            // failure don't add anything
        }
        cursor.close();   // cleanup
        db.close();
    }
}


























