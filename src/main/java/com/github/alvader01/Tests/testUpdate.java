package com.github.alvader01.Tests;

import com.github.alvader01.Model.DAO.UserDAO;
import com.github.alvader01.Model.Entity.User;

public class testUpdate {
        public static void main(String[] args) {
            User userToUpdate = new User();
            userToUpdate.setUsername("Alvader");
            userToUpdate.setName("Pedro");
            userToUpdate.setPassword("updated");
            userToUpdate.setEmail("updated@example.com");
            UserDAO userDAO = new UserDAO();
            User updatedUser = userDAO.update(userToUpdate);
            if (updatedUser != null) {
                System.out.println("Usuario actualizado correctamente:");
                System.out.println("Name: " + updatedUser.getName());
                System.out.println("Password: " + updatedUser.getPassword());
                System.out.println("Email: " + updatedUser.getEmail());
            } else {
                System.out.println("La actualización del usuario falló");
            }
        }
    }


