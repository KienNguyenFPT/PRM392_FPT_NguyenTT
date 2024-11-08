package com.example.lab10_vannhat.model;

public class Trainee {
    private long id;
    private String name;
    private  String email;
    private String phone;
    private  String gender;

    public Trainee(long id, String name, String email, String phone, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }

    public Trainee(String name, String email, String phone, String gender) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }


    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
