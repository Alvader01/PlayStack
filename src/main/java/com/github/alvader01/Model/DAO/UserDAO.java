package com.github.alvader01.Model.DAO;

import com.github.alvader01.Model.Connection.ConnectionMariaDB;
import com.github.alvader01.Model.Entity.Category;
import com.github.alvader01.Model.Entity.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User, String> {
    private final static String INSERT="INSERT INTO user (username,name,password,email) VALUES (?,?,?,?)";
    private final static String UPDATE="UPDATE user SET name = ?, password = ?, email = ? WHERE username = ?";
    private final static String FINDALL="SELECT u.username, u.name, u.password, u.email FROM user AS u";
    private final static String FINDBYUSERNAME="SELECT u.username,u.name, u.password,u.email FROM user AS u WHERE u.username = ?";
    private final static String DELETE="DELETE FROM user WHERE username = ?";

    /**
     * Saves a User object in the database.
     *
     * @param user   The User object to be saved.
     * @return         A newly created User object.
     */
    @Override
    public User save(User user) {
        User result = new User();
        User u = findByUsername(user.getUsername());
        if (u == null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getEmail());
                ps.executeUpdate();

                if (user.getCategories() != null) {
                    for (Category category : user.getCategories()) {
                        CategoryDAO.build().save(category);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al guardar el usuario", e);
            }
        }
        return result;
    }

    /**
     * Updates a User object in the database with the provided data.
     *
     * @param user   The User object containing the updated data.
     * @return         The updated User object.
     */
    @Override
    public User update(User user) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            User lastUser = findByUsername(user.getUsername());

            String nameToUpdate;
            if (user.getName() == null || user.getName().isEmpty()) {
                nameToUpdate = lastUser.getName();
            } else {
                nameToUpdate = user.getName();
            }
            ps.setString(1, nameToUpdate);

            String passwordToUpdate;
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                passwordToUpdate = lastUser.getPassword();
            } else {
                passwordToUpdate = user.getPassword();
            }
            ps.setString(2, passwordToUpdate);

            String emailToUpdate;
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                emailToUpdate = lastUser.getEmail();
            } else {
                emailToUpdate = user.getEmail();
            }
            ps.setString(3, emailToUpdate);

            ps.setString(4, user.getUsername());

            ps.executeUpdate();

            if (user.getCategories() != null) {
                List<Category> categoriesBefore = CategoryDAO.build().findByUser(user.getUsername());
                List<Category> categoriesAfter = user.getCategories();

                List<Category> categoriesToBeDeleted = new ArrayList<>(categoriesBefore);
                categoriesToBeDeleted.removeAll(categoriesAfter);

                for (Category category : categoriesToBeDeleted) {
                    CategoryDAO.build().delete(category);
                }
                for (Category category : categoriesAfter) {
                    CategoryDAO.build().save(category);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el usuario", e);
        }

        return user;
    }

    /**
     * Deletes a User object from the database.
     *
     * @param user   The User object to be deleted.
     * @return         The deleted User object.
     * @throws SQLException if a database access error occurs
     */
    @Override
    public User delete(User user) throws SQLException {
        if (user != null && user.getUsername() != null) {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
                pst.setString(1, user.getUsername());
                pst.executeUpdate();
            }

        }
        return user;
    }

    /**
     * Retrieves a User object from the database by its username.
     *
     * @param key   The username of the User object to retrieve.
     * @return         The retrieved User object, or null if not found.
     */
    @Override
    public User findById(String key) {
        return null;
    }

    public User findByUsername(String username) {
        User result = null;
        Connection conn = ConnectionMariaDB.getConnection();

        try {
            if (conn == null || conn.isClosed()) {
                System.out.println("Conexión cerrada, intentando reconectar...");
                conn = ConnectionMariaDB.getConnection();
                if (conn == null || conn.isClosed()) {
                    System.out.println("No se pudo obtener una conexión.");
                    return null;
                }
            }


            PreparedStatement ps = conn.prepareStatement(FINDBYUSERNAME);
            ps.setString(1, username);
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                result = new User();
                result.setUsername(res.getString("username"));
                result.setName(res.getString("name"));
                result.setPassword(res.getString("password"));
                result.setEmail(res.getString("email"));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Retrieves all User objects from the database.
     *
     * @return         A list of all User objects found in the database.
     */
    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {

            ResultSet res = pst.executeQuery();
            while(res.next()){
                User u = new User();
                u.setUsername(res.getString("username"));
                u.setName(res.getString("name"));
                u.setEmail(res.getString("email"));
                result.add(u);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Checks if a User with the specified username exists in the database.
     *
     * @param username   The username of the User to check for existence.
     * @return         True if a User with the specified username exists, false otherwise.
     */
    public boolean exists(String username) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYUSERNAME)) {
            ps.setString(1, username);
            ResultSet res = ps.executeQuery();

            return res.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void close() throws IOException {

    }


}
