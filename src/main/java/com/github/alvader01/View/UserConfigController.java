package com.github.alvader01.View;

import com.github.alvader01.App;

import com.github.alvader01.Model.DAO.UserDAO;
import com.github.alvader01.Model.Entity.User;
import com.github.alvader01.Model.Singleton.UserSession;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.github.alvader01.View.RegisterController.isValidEmail;

public class UserConfigController extends Controller implements Initializable {

    @FXML
    Button borrar;
    @FXML
    ImageView volver;
    @FXML
    TableView<User> table;
    @FXML
    TableColumn<User, String> username;
    @FXML
    TableColumn<User, String> name;
    @FXML
    TableColumn<User, String> email;
    @FXML
    ImageView info;

    private ObservableList<User> users;


    @Override
    public void onOpen(Object input) throws IOException {
        User currentUser = UserSession.getCurrentUser();
        if (currentUser != null) {
            this.users = FXCollections.observableArrayList(currentUser);
            table.setItems(this.users);
        } else {
            changeSceneToLogin();
        }
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserDAO uDAO = new UserDAO();
        table.setEditable(true);
        username.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getUsername()));
        name.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getName()));
        email.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getEmail()));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        email.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> {
            if (event.getNewValue().equals(event.getOldValue())) {
                return;
            }
            User user = event.getRowValue();
            user.setName(event.getNewValue());
            uDAO.update(user);
        });

        email.setOnEditCommit(event -> {
            if (event.getNewValue().equals(event.getOldValue())) {
                return;
            }

            String newEmail = event.getNewValue();
            if (isValidEmail(newEmail)) {
                User user = event.getRowValue();
                user.setEmail(newEmail);
                uDAO.update(user);
            } else {
                AppController.ShowAlertsInvalidEmail();
                table.refresh();
            }
        });
    }

    public void deleteCurrentUser() throws IOException {
        User currentUser = UserSession.getCurrentUser();
        if (currentUser != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Eliminar usuario");
            alert.setContentText("¿Estás seguro de que deseas eliminar tu cuenta?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                UserDAO uDAO = new UserDAO();
                try {
                    uDAO.delete(currentUser);
                    UserSession.logout();
                    changeSceneToLogin();
                } catch (SQLException e) {
                   AppController.ShowAlertsErrorDeleteUser();
                }
            }
        }
    }

    public void changeSceneToLogin() throws IOException {
        App.currentController.changeScene(Scenes.LOGIN, null);
    }
    public void changeSceneToMainPage() throws IOException {
        App.currentController.changeScene(Scenes.MAINPAGE, null);

    }


    public void ShowInfo(){
        AppController.ShowInformationUser();
    }







}
