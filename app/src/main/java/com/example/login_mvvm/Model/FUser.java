package com.example.login_mvvm.Model;

public class FUser {
    String name;
    String email;
    String pass;
    String address;

    public FUser(String name, String email, String pass, String address) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.address = address;
    }

    public String getName() {
        return name;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
