package com.github.alvader01.Tests;



import com.github.alvader01.Model.DAO.UserDAO;
import com.github.alvader01.Model.Entity.User;

import java.sql.SQLException;



public class testDelete {
    public static void main(String[] args) throws SQLException {
        UserDAO userDAO = new UserDAO();
        User u1 = new User("user1", "User One", "password1", "user1@example.com", null);
        User u2 = new User("user2", "User Two", "password2", "user2@example.com", null);
        userDAO.delete(u1);
        userDAO.delete(u2);
    }
}