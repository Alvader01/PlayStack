package com.github.alvader01.View;

import com.github.alvader01.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController extends Controller implements Initializable {
    @FXML
    private BorderPane borderPane;
    private Controller centerController;

    @FXML
    static Alert alert= new Alert(Alert.AlertType.ERROR);

    @FXML
    static Alert alert2= new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    static Alert alertInfoRegister= new Alert(Alert.AlertType.INFORMATION);

    @Override
    public void onOpen(Object input) throws IOException {
        //Al abrir este controlador que cargue main en el centro
        changeScene(Scenes.LOGIN,null);
    }

    public void changeScene(Scenes scene,Object data) throws IOException {
        View view = loadFXML(scene);
        borderPane.setCenter(view.scene);
        this.centerController = view.controller;
        this.centerController.onOpen(data);
    }

    public void openModal(Scenes scene, String title,Controller parent, Object data) throws IOException {
        View view = loadFXML(scene);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.stage);
        Scene _scene = new Scene(view.scene);
        stage.setScene(_scene);
        view.controller.onOpen(parent);
        stage.showAndWait();

    }


    @Override
    public void onClose(Object output) {
        //nothing to do
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static View loadFXML(Scenes scenes) throws IOException {
        String url = scenes.getURL();
        FXMLLoader loader = new FXMLLoader(App.class.getResource(url));
        Parent p = loader.load();
        Controller c = loader.getController();
        View view = new View();
        view.scene=p;
        view.controller=c;
        return view;
    }
    public static void ShowAlertsErrorLogin(){
        alert.setTitle("Error");
        alert.setContentText("Usuario o contraseña incorrectos");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorLoginPassword(){
        alert.setTitle("Error");
        alert.setContentText("La contraseña es incorrecta");
        alert.showAndWait();
    }
    public static void ShowAlertsInvalidEmail(){
        alert.setTitle("Error");
        alert.setContentText("El email tiene que ser valido, por ejemplo: 1rT8j@example.com");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorRegister(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo registrar el usuario");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorLogin2(){
        alert.setTitle("Error");
        alert.setContentText("Necesitas introducir los campos de usuario y contraseña");
        alert.showAndWait();

    }
    public static void ShowAlertsSuccessfullyRegister(){
        alert2.setTitle("Exito");
        alert2.setContentText("Usuario registrado con exito");
        alert2.showAndWait();
    }


    public static void ShowAlertsUserAlreadyExists(){
        alert.setTitle("Error");
        alert.setContentText("El usuario ya existe");
        alert.showAndWait();
    }
    public static void  ShowInformation(){
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setContentText("No puedes crear usuarios con el mismo usuario");
        alertInfoRegister.showAndWait();

    }

    public static void  ShowInformationUser(){
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para editar una categoria haz doble click en el campo que quieras editar");
        alertInfoRegister.showAndWait();

    }
    public static void  ShowInformationCategory(){
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para editar una categoria haz doble click en el campo que quieras editar");
        alertInfoRegister.showAndWait();
    }
    public static void  ShowInformationGame(){
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para editar un juego haz doble click en el campo que quieras editar");
        alertInfoRegister.showAndWait();
    }

    public static void ShowAlertsSuccessfullyDeleteUser(){
        alert2.setTitle("Exito");
        alert2.setContentText("Usuario eliminado con exito");
        alert2.showAndWait();
    }
    public static void ShowAlertsErrorDeleteUser(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo eliminar el usuario");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorUserNotActive(){
        alert.setTitle("Error");
        alert.setContentText("No hay un usuario activo. Inicia sesión nuevamente.");
        alert.showAndWait();
    }
    public static void ShowAlertsSuccessfullyDeleteCategory(){
        alert2.setTitle("Exito");
        alert2.setContentText("Categoria eliminada con exito");
        alert2.showAndWait();
    }
    public static void ShowAlertsSuccessfullyDeleteGame(){
        alert2.setTitle("Exito");
        alert2.setContentText("Juego eliminada con exito");
        alert2.showAndWait();
    }
    public static void ShowAlertsErrorCreatingCategory(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo registrar la categoria");
        alert.showAndWait();
    }
    public static void ShowAlertsGameAlreadyExistsInCategory(){
        alert.setTitle("Error");
        alert.setContentText("El juego ya esta en la categoria");
        alert.showAndWait();
    }
    public static void ShowAlertsSuccessfullyAddGameToCategory(){
        alert2.setTitle("Exito");
        alert2.setContentText("Juego añadido con exito");
        alert2.showAndWait();
    }
    public static void  ShowInformationAddGame(){
        alertInfoRegister.getDialogPane().setPrefHeight(200);
        alertInfoRegister.getDialogPane().setPrefWidth(300);
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para añadir un juego a una categoria selecciona que juegodeseas añadir" +
                "y a que categoria deseas añadirlo. Para eliminarla lo mismo pero pulsando el boton eliminar");
        alertInfoRegister.showAndWait();
    }
    public static void ShowAlertsErrorCreatingGame(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo registrar el juego");
        alert.showAndWait();
    }

    public static void ShowAlertsGameNotFound(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo encontrar el juego");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorDeleteCategory(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo eliminar la categoria");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorDeleteGame(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo eliminar el juego");
        alert.showAndWait();
    }
    public static void  ShowInformationDeleteCategory(){
        alertInfoRegister.getDialogPane().setPrefHeight(200);
        alertInfoRegister.getDialogPane().setPrefWidth(300);
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para borrar la categoria que deseas eligela en la lista y pulsa el boton de eliminar");
        alertInfoRegister.showAndWait();

    }
    public static void  ShowInformationDeleteGame(){
        alertInfoRegister.getDialogPane().setPrefHeight(200);
        alertInfoRegister.getDialogPane().setPrefWidth(300);
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para borrar el juego que deseas eligela en la lista y pulsa el boton de eliminar");
        alertInfoRegister.showAndWait();

    }

    public static void ShowAlertsCategoryAlreadyExists(){
        alert.setTitle("Error");
        alert.setContentText("La categoria ya existe");
        alert.showAndWait();
    }
    public static void ShowAlertsSuccessfullyCreateCategory(){
        alert2.setTitle("Exito");
        alert2.setContentText("Categoria registrada con exito");
        alert2.showAndWait();
    }
    public static void ShowAlertsSuccessfullyCreateGame(){
        alert2.setTitle("Exito");
        alert2.setContentText("Juego registrado con exito");
        alert2.showAndWait();
    }


}
