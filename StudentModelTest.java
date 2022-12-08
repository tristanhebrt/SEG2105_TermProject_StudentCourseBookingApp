package com.example.studentcoursebookingapp;

import static org.junit.Assert.*;

import org.junit.Test;

public class StudentModelTest {
    StudentModel studentModel = new StudentModel(300,"Cedric",
            "Dimatulac", "cdima078@uottawa.ca",
            "NerdCedric", "superduperSh9zue");

    @Test
    public void testGetId() {
        assertEquals("Testing Instructor ID",300,studentModel.getId());
    }

    @Test
    public void testSetId() {
        studentModel.setId(studentModel.getId()+1);
        assertEquals("Testing Setting Instructor ID", 301, studentModel.getId());
    }

    @Test
    public void testGetFirstName() {
        assertEquals("Testing First Name", "Cedric", studentModel.getFirstName());
    }


    @Test
    public void testGetLastName() {
        assertEquals("Testing First Name", "Dimatulac", studentModel.getLastName());
    }

    @Test
    public void testToString() {
        assertEquals("StudentModel{" +
                "id=300, firstName='Cedric', lastName='Dimatulac', email='cdima078@uottawa.ca', username='NerdCedric', password='superduperSh9zue'}",
                studentModel.toString());
    }
}