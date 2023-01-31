package com.example.myapplication;

public class User {
    public String username, gender, email;
    public int age;

    public User(){

    }

    public User(String username, int age, String gender, String email) {
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.email = email;
    }

}
