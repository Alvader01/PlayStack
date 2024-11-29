package com.github.alvader01.View;

import com.github.alvader01.App;
import com.github.alvader01.Model.DAO.GameDAO;
import com.github.alvader01.Model.Entity.Game;
import com.github.alvader01.Model.Entity.User;
import com.github.alvader01.Model.Singleton.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateGameController extends Controller implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField platform;
    @FXML
    Button crear;
    @FXML
    ImageView back;

    @Override
    public void onOpen(Object input) throws IOException {
        if (input instanceof User) {
            UserSession.login((User) input);
        }
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Obtiene los valores de los campos y crea una nueva instancia de Game.
     *
     * @return Un objeto Game o null si los datos no son válidos.
     */
    public Game getGameValues() {
        User currentUser = UserSession.getCurrentUser();
        if (currentUser == null) {
            return null;
        }

        String gameName = name.getText().trim();
        String gamePlatform = platform.getText().trim();

        if (gameName.isEmpty() ||  gamePlatform.isEmpty()) {
            return null;
        }

        return new Game(gameName, gamePlatform);
    }

    /**
     * Crea un nuevo juego y lo guarda en la base de datos.
     */
    public void createGame() throws IOException {
        User currentUser = UserSession.getCurrentUser();
        if (currentUser == null) {
            AppController.ShowAlertsErrorUserNotActive();
            return;
        }

        Game game = getGameValues();

        if (game == null) {
            AppController.ShowAlertsErrorCreatingGame();
            return;
        }

        try (GameDAO gameDAO = new GameDAO()) {
            Game savedGame = gameDAO.saveGame(game, currentUser);

            if (savedGame != null) {
                AppController.ShowAlertsSuccessfullyCreateGame();
                changeSceneToGameConfig();
            } else {
                AppController.ShowAlertsErrorCreatingGame();
            }
        } catch (Exception e) {
            AppController.ShowAlertsErrorCreatingGame();
        }
    }

    /**
     * Cambia la escena a la configuración de juegos.
     *
     * @throws IOException Si ocurre un error al cambiar de escena.
     */
    public void changeSceneToGameConfig() throws IOException {
        App.currentController.changeScene(Scenes.GAMECONFIG, null);
    }
}
