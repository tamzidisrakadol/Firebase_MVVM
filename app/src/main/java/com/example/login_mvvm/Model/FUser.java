package com.example.login_mvvm.Model;

public class FUser {
    // model name should be same as database table name

    String name;
    String email;
    String address;
    String profileImg;

    public FUser(String name, String email, String address,String profileImg) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.profileImg = profileImg;
    }

    public FUser() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
