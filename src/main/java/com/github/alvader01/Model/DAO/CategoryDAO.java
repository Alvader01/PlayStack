package com.github.alvader01.Model.DAO;

import com.github.alvader01.Model.Connection.ConnectionMariaDB;
import com.github.alvader01.Model.Entity.Category;
import com.github.alvader01.Model.Entity.Game;
import com.github.alvader01.Model.Entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements DAO<Category, Integer> {
    private final static String INSERT="INSERT INTO Category (name, description, user_username) VALUES (?,?,?);";
    private final static String UPDATE = "UPDATE Category SET name = ?, description = ? WHERE id = ?";
    private final static String FINDALL = "SELECT c.id, c.name, c.description FROM Category AS c";
    private final static String FINDBYID = "SELECT c.id, c.name, c.description FROM Category AS c WHERE c.id = ?";
    private final static String DELETE = "DELETE FROM Category WHERE id = ?";
    private final static String FINDBYUSER = "SELECT * FROM category WHERE username = ?";




    public Category saveCategory(Category category, User currentUser) {
        Category result = new Category();
        Category c = findById(category.getId());
        if (c == null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                ps.setString(1, category.getName());
                ps.setString(2, category.getDescription());
                ps.setString(3, category.getDescription());
                ps.setString(4, currentUser.getUsername());
                ps.executeUpdate();

                if (category.getGames() != null) {
                    for (Game game : category.getGames()) {
                        GameDAO.build().save(game);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al guardar el usuario", e);
            }
        }
        return result;
    }


    @Override
    public Category save(Category entity) {
        return null;
    }


    @Override
    public Category update(Category category) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getId());

            ps.executeUpdate();

            if(category.getGames() != null) {
                List<Game> gameBefore = GameDAO.build().findInCategory(category);
                List<Game> gameAfter = category.getGames();

                List<Game> gameToBeRemoved = new ArrayList<>(gameBefore);
                gameToBeRemoved.removeAll(gameAfter);

                for(Game g : gameToBeRemoved) {
                    GameDAO.build().delete(g);
                }
                for(Game g : gameAfter) {
                    GameDAO.build().save(g);
                }
            }



        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el usuario", e);
        }

        return category;
    }



    @Override
    public Category delete(Category category) throws SQLException {
        if ( category!= null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
                ps.setInt(1, category.getId());
                ps.executeUpdate();
            }

        }
        return category;
    }


    @Override
    public Category findById(Integer id) {
        Category result = null;
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            ps.setString(1, id.toString());
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                result = new Category();
                result.setId(res.getInt("id"));
                result.setName(res.getString("name"));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }



    @Override
    public List<Category> findAll() {
        List<Category> result = new ArrayList<>();

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {

            ResultSet res = pst.executeQuery();
            while(res.next()){
                Category c = new Category();
                c.setId(res.getInt("id"));
                c.setName(res.getString("name"));
                c.setDescription(res.getString("description"));
                result.add(c);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }



    public List<Category> findByUser(String username) {
        List<Category> categories = new ArrayList<>();

        try (Connection connection = ConnectionMariaDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(FINDBYUSER)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));

                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar las categorias del usuario", e);
        }

        return categories;
    }

    @Override
    public void close() throws IOException {

    }

    public static CategoryDAO build(){
        return new CategoryDAO();
    }


    public boolean exists(Integer id) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            ps.setString(1, id.toString());
            ResultSet res = ps.executeQuery();
            return res.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
