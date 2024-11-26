package com.github.alvader01.Tests;

import com.github.alvader01.Model.DAO.UserDAO;
import com.github.alvader01.Model.Entity.User;

public class testFindById {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        User userFound = userDAO.findByUsername("Alvader");
        System.out.println("User found: " + userFound);

    }
}

