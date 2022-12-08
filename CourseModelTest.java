package com.example.studentcoursebookingapp;

import static org.junit.Assert.*;

import org.junit.Test;

public class CourseModelTest {
    CourseModel courseModel = new CourseModel(3, "SEG2105",
            "Intro to Software Engineering", "Omar Badreddin",
            1234567, "Tuesdays 1-2:30, Wednesdays 2:30-4",
            "Principles of software engineering: Review of principles of object orientation. Object oriented analysis using UML. Frameworks and APIs. Introduction to the client-server architecture. Analysis, design and programming of simple servers and clients. Introduction to user interface technology.",
            30,4);

    @Test
    public void testGetCourseInstructorId() {
        assertEquals("Testing Instructor ID", 3, courseModel.getCourseId());
    }

    @Test
    public void testGetCourseDays() {
        assertEquals("Testing Course Days", "Tuesdays 1-2:30, Wednesdays 2:30-4", courseModel.getCourseDays());
    }

    @Test
    public void testGetCourseDescription() {
        assertEquals("Testing Course Description", "Principles of software engineering: Review of principles of object orientation. Object oriented analysis using UML. Frameworks and APIs. Introduction to the client-server architecture. Analysis, design and programming of simple servers and clients. Introduction to user interface technology.", courseModel.getCourseDescription());
    }

    @Test
    public void testGetCourseCapacity() {
        assertEquals("Testing Course Capacity", 30, courseModel.getCourseCapacity());
    }

    @Test
    public void testGetCourseEnrolled() {
        assertEquals("Testing Number of Students Enrolled", 4, courseModel.getCourseEnrolled());
    }
}