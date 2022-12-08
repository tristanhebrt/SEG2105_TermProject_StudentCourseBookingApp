package com.example.studentcoursebookingapp;
import java.util.regex.*;

public class StudentModel {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    // constructors

    public StudentModel(int id, String firstName, String lastName, String email, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // to string to be able to print

    @Override
    public String toString() {
        return "StudentModel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    // getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(email);
        Matcher matcher = pattern.matcher("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        if (matcher.find()) {
            return true;
        } else {return false;}
    }

    public void setEmail(String email) {
        if (isEmailValid(email)) {
            this.email = email;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(password);
        Matcher matcher = pattern.matcher("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        if (matcher.find()) {
            return true;
        }
        else{return false;}
    }

    public void setPassword(String password) {
        if (isPasswordValid(password)){
            this.password = password;
        }
    }
}






