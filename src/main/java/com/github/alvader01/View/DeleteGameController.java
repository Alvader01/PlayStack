package com.github.alvader01.View;

import com.github.alvader01.App;

import com.github.alvader01.Model.DAO.GameDAO;
import com.github.alvader01.Model.Entity.Game;
import com.github.alvader01.Model.Singleton.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DeleteGameController extends Controller implements Initializable {
    @FXML
    private ComboBox<String> GameComboBox;
    @FXML
    private TableView<Game> GameTableView;
    @FXML
    private TableColumn<Game, Integer> IdColumn;
    @FXML
    private TableColumn<Game, String> NameColumn;
    @FXML
    private TableColumn<Game, String> PlatformColumn;
    @FXML
    private ImageView back;
    @FXML
    private ImageView info;

    @FXML
    private Button deleteButton;

    private ObservableList<Game> games;
    private List<Game> allGames;

    @Override
    public void onOpen(Object input) throws IOException {
        loadUserGames();
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PlatformColumn.setCellValueFactory(new PropertyValueFactory<>("platform"));


        loadUserGames();

        GameComboBox.setOnAction(event -> {
            String selectedGameName = GameComboBox.getValue();
            if (selectedGameName != null) {
                Game selectedGame = findGameByName(selectedGameName);
                if (selectedGame != null) {
                    GameTableView.setItems(FXCollections.observableArrayList(selectedGame));
                }
            }
        });
    }

    /**
     * Carga los juegos asociados al usuario y llena el ComboBox y TableView.
     */
    private void loadUserGames() {
        try {
            String username = UserSession.getCurrentUser().getUsername();
            GameDAO gDAO = new GameDAO();
            allGames = gDAO.findGamesByUser(username);
            games = FXCollections.observableArrayList(allGames);


            List<String> gameNames = allGames.stream()
                    .map(Game::getName)
                    .collect(Collectors.toList());
            GameComboBox.setItems(FXCollections.observableArrayList(gameNames));


            GameTableView.setItems(games);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca un juego por su nombre en los juegos cargados.
     *
     * @param name el nombre del juego
     * @return el objeto Game correspondiente o null si no se encuentra
     */
    private Game findGameByName(String name) {
        return allGames.stream()
                .filter(game -> game.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Elimina el juego seleccionado de la base de datos y actualiza la UI.
     */
    public void deleteGame() {
        String selectedGameName = GameComboBox.getValue();
        if (selectedGameName != null) {
            Game gameToDelete = findGameByName(selectedGameName);
            if (gameToDelete != null) {
                try {
                    GameDAO gDAO = new GameDAO();
                    gDAO.delete(gameToDelete);

                    allGames.remove(gameToDelete);
                    games.remove(gameToDelete);


                    List<String> updatedGameNames = allGames.stream()
                            .map(Game::getName)
                            .collect(Collectors.toList());
                    GameComboBox.setItems(FXCollections.observableArrayList(updatedGameNames));
                    GameTableView.setItems(games);

                    AppController.ShowAlertsSuccessfullyDeleteGame();

                } catch (SQLException e) {
                    e.printStackTrace();
                    AppController.ShowAlertsErrorDeleteGame();
                }
            }
        }
    }

    public void ShowInfo() {
        AppController.ShowInformationDeleteGame();
    }

    public void changeSceneToGameConfig() throws IOException {
        App.currentController.changeScene(Scenes.GAMECONFIG, null);
    }
}


