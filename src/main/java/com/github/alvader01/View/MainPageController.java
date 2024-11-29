package com.github.alvader01.View;

import com.github.alvader01.App;
import com.github.alvader01.Model.Singleton.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController extends Controller implements Initializable {
    @FXML
    Button users;

    @FXML
    Button categories;

    @FXML
    Button game;

    @FXML
    Button logout;




    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void Logout() throws IOException{
        UserSession.logout();
        App.currentController.changeScene(Scenes.LOGIN,null);
    }

    public void changeSceneToUsersConfig() throws IOException{
        App.currentController.changeScene(Scenes.USERCONFIG,null);
    }
    public void changeSceneToCategoryConfig() throws IOException{
        App.currentController.changeScene(Scenes.CATEGORYCONFIG,null);
    }
    public void changeSceneToGameConfig() throws IOException{
        App.currentController.changeScene(Scenes.GAMECONFIG,null);
    }








}
