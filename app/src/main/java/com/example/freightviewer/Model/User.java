package com.example.freightviewer.Model;

public class User {
    public String username;
    public String password;
    public String token;

    public static String userToken;
    public static boolean serviceIsBound = false;
    public static boolean permissionAlreadyDenied = false;

    public String getToken() {
        return token;
    }

    public User(String userName, String userPassword) {
        this.username = userName;
        this.password = userPassword;
    }
}
