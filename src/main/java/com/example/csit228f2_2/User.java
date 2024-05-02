package com.example.csit228f2_2;

import java.net.MalformedURLException;

public class User {
    private int id;
    String username;
    String password;


    public User(int id, String username, String password) throws MalformedURLException {
        this.id=id;
        this.username = username;
        this.password = password;


    }

    public int getId() {
        return id;
    }
    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
