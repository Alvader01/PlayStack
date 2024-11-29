package com.github.alvader01.View;

import com.github.alvader01.App;
import com.github.alvader01.Model.DAO.GameDAO;
import com.github.alvader01.Model.Entity.Game;
import com.github.alvader01.Model.Entity.User;
import com.github.alvader01.Model.Singleton.UserSession;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameConfigController extends Controller implements Initializable {
    @FXML
    Button crear;
    @FXML
    ImageView volver;
    @FXML
    TableView<Game> table;
    @FXML
    TableColumn<Game, Integer> Id;
    @FXML
    TableColumn<Game, String> name;
    @FXML
    TableColumn<Game, String> platform;
    @FXML
    Button eliminar;
    @FXML
    ImageView info;
    @FXML
    Button anadir;

    private ObservableList<Game> games;

    @Override
    public void onOpen(Object input) throws IOException {
        User currentUser = UserSession.getCurrentUser();
        if (currentUser == null) {
            return;
        }

        GameDAO gameDAO = new GameDAO();
        List<Game> games = gameDAO.findGamesByUser(currentUser.getUsername());
        this.games = FXCollections.observableArrayList(games);
        table.setItems(this.games);
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GameDAO gameDAO = new GameDAO();
        table.setEditable(true);


        table.setRowFactory(tv -> {
            TableRow<Game> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Game game = table.getSelectionModel().getSelectedItem();
                if (event.getClickCount() == 3 && !row.isEmpty() && game != null) {
                    Game game1 = row.getItem();
                    try {
                        App.currentController.changeScene(Scenes.ADDGAMETOCATEGORY, game1.getId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });


        Id.setCellValueFactory(game -> new SimpleIntegerProperty(game.getValue().getId()).asObject());
        name.setCellValueFactory(game -> new SimpleStringProperty(game.getValue().getName()));
        platform.setCellValueFactory(game -> new SimpleStringProperty(game.getValue().getPlatform()));


        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> {
            if (event.getNewValue().equals(event.getOldValue())) {
                return;
            }
            Game game = event.getRowValue();
            game.setName(event.getNewValue());
            gameDAO.update(game);
        });
        platform.setCellFactory(TextFieldTableCell.forTableColumn());
        platform.setOnEditCommit(event -> {
            if (event.getNewValue().equals(event.getOldValue())) {
                return;
            }
            Game game = event.getRowValue();
            game.setPlatform(event.getNewValue());
            gameDAO.update(game);
        });


    }

    public void changeSceneToMainPage() throws IOException {
        App.currentController.changeScene(Scenes.MAINPAGE, null);
    }

    public void ShowInfo() {
        AppController.ShowInformationGame();
    }

    public void changeSceneToCreateGame() throws IOException {
        App.currentController.changeScene(Scenes.CREATEGAME, null);
    }

    public void changeSceneToAddGameToCategory() throws IOException {
        App.currentController.changeScene(Scenes.ADDGAMETOCATEGORY, null);
    }

    public void changeSceneToDeleteGame() throws IOException {
        App.currentController.changeScene(Scenes.DELETEGAME, null);
    }
}