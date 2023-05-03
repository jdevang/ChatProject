package com.psu.dxj5305.chatproject.models;

import com.google.type.Date;

public class User {
    String username;

    public User(){}
    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
