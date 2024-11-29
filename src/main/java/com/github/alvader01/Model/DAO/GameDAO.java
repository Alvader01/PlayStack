package com.github.alvader01.Model.DAO;

import com.github.alvader01.Model.Connection.ConnectionMariaDB;
import com.github.alvader01.Model.Entity.Category;
import com.github.alvader01.Model.Entity.Game;
import com.github.alvader01.Model.Entity.User;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDAO implements DAO<Game, Integer> {
    private final static String INSERT = "INSERT INTO game (id,name, platform, user_username) VALUES (?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE game SET name = ?, platform = ?  WHERE id = ?";
    private final static String FINDALL = "SELECT g.id, g.name, g.platform  FROM game AS g";
    private final static String FINDBYID = "SELECT g.id, g.name, g.platform FROM game AS g WHERE g.id = ?";
    private final static String FINDBYNAME = "SELECT g.id, g.name, g.platform FROM game AS g WHERE g.name = ?";
    private final static String FINDBYUSERNAME = "SELECT id, name, platform FROM game WHERE user_username = ?";
    private final static String DELETE = "DELETE FROM game WHERE id = ?";
    private final static String FINDINCATEGORY = "SELECT g.id, g.name, g.platform FROM game AS g WHERE g.id = ?";

    @Override
    public Game save(Game game) {
        return null;
    }

    public Game saveGame(Game game, User currentUser) {
        Game result = new Game();
        Game g = findById(game.getId());
        if (g == null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                ps.setInt(1, game.getId());
                ps.setString(2, game.getName());
                ps.setString(3, game.getPlatform());
                ps.setString(4, currentUser.getUsername());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error al guardar el juego", e);
            }
        }
        return result;
    }

    @Override
    public Game update(Game game) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            ps.setString(1, game.getName());
            ps.setString(2, game.getPlatform());
            ps.setInt(3, game.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el juego", e);
        }

        return game;
    }

    @Override
    public Game delete(Game game) throws SQLException {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            ps.setInt(1, game.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el juego", e);
        }

        return game;
    }

    @Override
    public Game findById(Integer id) {
        Game game = null;

        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    game = new Game();
                    game.setId(rs.getInt("id"));
                    game.setName(rs.getString("name"));
                    game.setPlatform(rs.getString("platform"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al encontrar el juego por ID", e);
        }

        return game;
    }

    @Override
    public List<Game> findAll() {
        List<Game> result = new ArrayList<>();

        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Game game = new Game();
                game.setId(rs.getInt("id"));
                game.setName(rs.getString("name"));
                game.setPlatform(rs.getString("platform"));


                result.add(game);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al encontrar todos los juegos", e);
        }

        return result;
    }

    @Override
    public void close() throws IOException {

    }

    /**
     * Retrieves a Game object from the database by its name.
     *
     * @param name The name of the Game object to retrieve.
     * @return The retrieved Game object, or null if not found.
     */
    public Game findGameByName(String name) {
        Game result = null;
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYNAME)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Game g = new Game();
                    g.setId(rs.getInt("id"));
                    g.setName(rs.getString("name"));
                    g.setPlatform(rs.getString("platform"));

                    result = g;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean exists(Integer id) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            return res.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static GameDAO build() {
        return new GameDAO();
    }

    public List<Game> findInCategory(Category category) {
        List<Game> gameInCategory = new ArrayList<>();

        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDINCATEGORY)) {
            ps.setInt(1, category.getId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Game game = new Game();
                    game.setId(rs.getInt("id"));
                    game.setName(rs.getString("name"));
                    game.setPlatform(rs.getString("platform"));
                    gameInCategory.add(game);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar juegos en las categorias", e);
        }

        return gameInCategory;
    }

    public List<Game> findGamesByUser(String username) {
        List<Game> games = new ArrayList<>();

        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYUSERNAME)) {
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Game game = new Game(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("platform")
                );
                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return games;
    }
}

