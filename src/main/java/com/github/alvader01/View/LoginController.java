package com.github.alvader01.View;

import com.github.alvader01.App;
import com.github.alvader01.Model.DAO.UserDAO;
import com.github.alvader01.Model.Entity.User;
import com.github.alvader01.Model.Singleton.UserSession;

import com.github.alvader01.Utils.PasswordHasher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {
    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    Button button;

    @FXML
    Button buttonRegister;

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public User getValues(){
        User user = new User();
        String usernames = username.getText();
        String passwords = password.getText();
        if (!usernames.isEmpty() && !passwords.isEmpty()) {
            user.setUsername(usernames);
            user.setPassword(passwords);
            return user;
        } else {
            return null;
        }
    }

    /**
     * Logs in the user based on the provided credentials.
     * Displays error alerts if the login attempt fails due to incorrect credentials or other issues.
     *
     * @throws IOException If an I/O exception occurs while logging in.
     */
    public void Login() throws IOException {
        User user = getValues();
        UserDAO uDAO = new UserDAO();

        if (user == null) {
            AppController.ShowAlertsErrorLogin2();
        } else {
            User userFromDB = uDAO.findByUsername(user.getUsername());

            if (userFromDB != null) {
                String hashedInputPassword = PasswordHasher.hashPassword(user.getPassword());

                if (hashedInputPassword != null && hashedInputPassword.equals(userFromDB.getPassword())) {
                    UserSession.login(userFromDB);
                    changeSceneToMainPage();
                } else {
                    AppController.ShowAlertsErrorLoginPassword();
                }
            } else {
                AppController.ShowAlertsErrorLogin();
            }
        }
    }

    public void changeSceneToMainPage() throws IOException{
        App.currentController.changeScene(Scenes.MAINPAGE,null);
    }

    public void changeSceneToRegister() throws IOException{
        App.currentController.changeScene(Scenes.REGISTER,null);
    }


}
