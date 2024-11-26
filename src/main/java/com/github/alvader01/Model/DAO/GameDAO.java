package com.github.alvader01.Model.DAO;

import com.github.alvader01.Model.Connection.ConnectionMariaDB;
import com.github.alvader01.Model.Entity.Category;
import com.github.alvader01.Model.Entity.Game;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDAO implements DAO<Game, Integer> {
    private final static String FINDINCATEGORY="SELECT g.id, g.name, g.platform FROM game AS g WHERE g.id = ?";
    @Override
    public Game save(Game entity) {
        return null;
    }

    @Override
    public Game update(Game entity) {
        return null;
    }

    @Override
    public Game delete(Game entity) throws SQLException {
        return null;
    }

    @Override
    public Game findById(Integer key) {
        return null;
    }

    @Override
    public List<Game> findAll() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
    public static GameDAO build(){
        return new GameDAO();
    }
    public List<Game> findInCategory(Category category) {
        List<Game> gameInCategory = new ArrayList<>();

        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDINCATEGORY)) {
            ps.setInt(1, category.getId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Game game = new Game();
                    gameInCategory.add(game);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar juegos en las categorias", e);
        }

        return gameInCategory;
    }
}
