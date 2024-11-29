package com.github.alvader01.Model.DAO;

import com.github.alvader01.Model.Connection.ConnectionMariaDB;
import com.github.alvader01.Model.Entity.Game;
import com.github.alvader01.Model.Entity.GameInCategory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameInCategoryDAO implements DAO<GameInCategory, Integer> {
    private final static String ADDGAMEINCATEGORY = "INSERT INTO Holds (category_id, game_id) VALUES (?, ?)";
    private final static String DELETEGAMEFROMCATEGORY = "DELETE FROM Holds WHERE category_id = ? AND game_id = ?";
    private final static String FINDINCATEGORY = "SELECT COUNT(*) FROM Holds WHERE category_id = ? AND game_id = ?\n";
    private final static String FINDGAMEBYID = "SELECT id, name, platform FROM Game WHERE id = ?";
    private final static String FINDALLGAMEINCATEGORY = "SELECT g.id, g.name, g.platform FROM Game g JOIN Holds h ON g.id = h.game_id WHERE h.category_id = ?";

    public void addGameToCategory(int categoryId, int gameId) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(ADDGAMEINCATEGORY)) {
            ps.setInt(1, categoryId);
            ps.setInt(2, gameId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar el juego a la categoria", e);}
    }


    public boolean removeGameFromCategory(int categoryId, int gameId) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(DELETEGAMEFROMCATEGORY)) {
            ps.setInt(1, categoryId);
            ps.setInt(2, gameId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el juego de la categoria", e);
        }
    }

    public List<Game> findAllGamesInCategory(int categoryId) {
        List<Game> gameList = new ArrayList<>();
        System.out.println("categoryId: " + categoryId);
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDALLGAMEINCATEGORY)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String platform = rs.getString("platform");


                Game game = new Game(id, name, platform);
                gameList.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al encontrar las especies en el acuario", e);
        }

        return gameList;
    }


    @Override
    public GameInCategory save(GameInCategory entity) {
        return null;
    }

    @Override
    public GameInCategory update(GameInCategory entity) {
        return null;
    }

    @Override
    public GameInCategory delete(GameInCategory entity) throws SQLException {
        return null;
    }

    @Override
    public GameInCategory findById(Integer key) {
        return null;
    }

    @Override
    public List<GameInCategory> findAll() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    public static GameInCategoryDAO build() {
        return new GameInCategoryDAO();
    }

    public Game findGameById(int gameId) {
        Game game = null;
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDGAMEBYID)) {
            ps.setInt(1, gameId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String platform = rs.getString("platform");

                game = new Game(id, name, platform);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al encontrar el juego por ID", e);
        }

        return game;
    }


    public boolean isGameInCategory(int categoryId, int gameId) {
        Game game = findGameById(gameId);

        if (game != null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDINCATEGORY)) {
                ps.setInt(1, categoryId);
                ps.setInt(2, gameId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al verificar la asociación del juego a la categoria", e);
            }
        }

        return false;
    }

}



