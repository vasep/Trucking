package com.example.freightviewer.Model;

public class User {
    public String username;
    public String password;
    public String token;

    public static String userToken;

    public String getToken() {
        return token;
    }

    public User(String userName, String userPassword) {
        this.username = userName;
        this.password = userPassword;
    }
}
