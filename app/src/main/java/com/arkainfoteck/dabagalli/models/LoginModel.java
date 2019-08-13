package com.arkainfoteck.dabagalli.models;

public class LoginModel {
    String name,password,email,user_id,location;

    public LoginModel(String name, String password, String email, String user_id, String location) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.user_id = user_id;
        this.location = location;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
