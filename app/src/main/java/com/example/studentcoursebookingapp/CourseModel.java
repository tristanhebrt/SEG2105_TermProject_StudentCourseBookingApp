package com.example.studentcoursebookingapp;

public class CourseModel {
    private int courseId;

    private String courseCode;
    private String courseName;

    private String courseInstructor;
    private int courseInstructorId;

    private String courseDaysAndHours;
    private String courseDescription;

    private int courseCapacity;
    private String courseEnrolled;

    // constructors
    public CourseModel(int courseId, String courseCode, String courseName, String courseInstructor, int courseInstructorId, String courseDaysAndHours,
                       String courseDescription, int courseCapacity, String courseEnrolled) {

        this.courseId = courseId;

        this.courseCode = courseCode;
        this.courseName = courseName;

        this.courseInstructor = courseInstructor;
        this.courseInstructorId = courseInstructorId;

        this.courseDaysAndHours = courseDaysAndHours;
        this.courseDescription = courseDescription;

        this.courseCapacity = courseCapacity;
        this.courseEnrolled = courseEnrolled;
    }

    // to string


    @Override
    public String toString() {
        return  "Course code : " + courseCode +
                "\nCourse name : " + courseName +
                "\nCourse instructor : " + courseInstructor +
                "\nCourse days and hours : " + courseDaysAndHours +
                "\nCourse description : " + courseDescription +
                "\nCourse capacity : " + courseCapacity;

              //  "CourseModel{" +
              //  "courseId=" + courseId +
              //  ", courseCode='" + courseCode + '\'' +
              //  ", courseName='" + courseName + '\'' +
              //  ", courseInstructor='" + courseInstructor + '\'' +
              //  ", courseInstructorId='" + courseInstructorId +
              //  ", courseDays='" + courseDaysAndHours + '\'' +
              //  ", courseDescription='" + courseDescription + '\'' +
              //  ", courseCapacity=" + courseCapacity +
              //  ", courseEnrolled=" + courseEnrolled +
              //  '}';
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
        return courseDaysAndHours;
    }
    public void setCourseDays(String courseDays) {
        this.courseDaysAndHours = courseDays;
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

    public String getCourseEnrolled() {
        return courseEnrolled;
    }
    public void setCourseEnrolled(String courseEnrolled) {
        this.courseEnrolled = courseEnrolled;
    }
}
