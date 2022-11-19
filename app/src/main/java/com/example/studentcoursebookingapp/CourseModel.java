package com.example.studentcoursebookingapp;

public class CourseModel {
    private int courseId;

    private String courseCode;
    private String courseName;

    private String courseInstructor;
    private int courseInstructorId;

    private String courseDays;
    private String courseHours;
    private String courseDescription;

    private int courseCapacity;
    private int courseEnrolled;

    // constructors
    public CourseModel(int courseId, String courseCode, String courseName, String courseInstructor, int courseInstructorId, String courseDays, String courseHours,
                       String courseDescription, int courseCapacity, int courseEnrolled) {

        this.courseId = courseId;

        this.courseCode = courseCode;
        this.courseName = courseName;

        this.courseInstructor = courseInstructor;
        this.courseInstructorId = courseInstructorId;

        this.courseDays = courseDays;
        this.courseHours = courseHours;
        this.courseDescription = courseDescription;

        this.courseCapacity = courseCapacity;
        this.courseEnrolled = courseEnrolled;
    }

    // to string


    @Override
    public String toString() {
        return "CourseModel{" +
                "courseId=" + courseId +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseInstructor='" + courseInstructor + '\'' +
                ", courseInstructorId='" + courseInstructorId +
                ", courseDays='" + courseDays + '\'' +
                ", courseHours='" + courseHours + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                ", courseCapacity=" + courseCapacity +
                ", courseEnrolled=" + courseEnrolled +
                '}';
    }

    // getters and setters

    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseInstructor() {
        return courseInstructor;
    }
    public void setCourseInstructor(String courseInstructor) {this.courseInstructor = courseInstructor;}

    public int getCourseInstructorId(){ return courseInstructorId; }
    public void setCourseInstructorId(int courseInstructorId){ this.courseInstructorId = courseInstructorId; }

    public String getCourseDays() {
        return courseDays;
    }
    public void setCourseDays(String courseDays) {
        this.courseDays = courseDays;
    }

    public String getCourseHours() {
        return courseHours;
    }
    public void setCourseHours(String courseHours) {
        this.courseHours = courseHours;
    }

    public String getCourseDescription() {
        return courseDescription;
    }
    public void setCourseDescription(String courseDescription) {this.courseDescription = courseDescription;}

    public int getCourseCapacity() {
        return courseCapacity;
    }
    public void setCourseCapacity(int courseCapacity) {
        this.courseCapacity = courseCapacity;
    }

    public int getCourseEnrolled() {
        return courseEnrolled;
    }
    public void setCourseEnrolled(int courseEnrolled) {
        this.courseEnrolled = courseEnrolled;
    }
}
