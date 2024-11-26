package com.github.alvader01.Model.Singleton;

import com.github.alvader01.Model.Entity.User;

public class UserSession {

    private static UserSession _instance;
    private static User userLogged;

    private UserSession() {

    }

    public static UserSession getInstance() {
        if (_instance == null) {
            _instance = new UserSession();
        }
        return _instance;
    }


    public static void login(User user) {
        userLogged = user;
    }

    public static void logout() {
        userLogged = null;
    }

    public static User getCurrentUser() {
        return userLogged;
    }
}
