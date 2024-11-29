package com.github.alvader01.View;

import com.github.alvader01.App;
import com.github.alvader01.Model.DAO.CategoryDAO;
import com.github.alvader01.Model.Entity.Category;
import com.github.alvader01.Model.Entity.User;
import com.github.alvader01.Model.Singleton.UserSession;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateCategoryController extends Controller implements Initializable {

    @FXML
    TextField name;
    @FXML
    private TextArea description;
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


    public Category getCategoryValues() {
        User currentUser = UserSession.getCurrentUser();
        if (currentUser == null) {
            return null;
        }

        String names = name.getText().trim();
        String descriptions = description.getText().trim();

        if (names.isEmpty() || descriptions.isEmpty()) {
            return null;
        }

        return new Category(names, descriptions);
    }

    public void createCategory() throws IOException {
        User currentUser = UserSession.getCurrentUser();
        if (currentUser == null) {
            AppController.ShowAlertsErrorUserNotActive();
            return;
        }

        Category category = getCategoryValues();

        if (category == null) {
            AppController.ShowAlertsErrorCreatingCategory();
            return;
        }

        try (CategoryDAO categoryDAO = new CategoryDAO()) {

            Category savedCategory = categoryDAO.saveCategory(category, currentUser);

            if (savedCategory != null) {
                AppController.ShowAlertsSuccessfullyCreateCategory();
                changeSceneToCategoryConfig();
            } else {
                AppController.ShowAlertsErrorCreatingCategory();
            }
        } catch (Exception e) {
            AppController.ShowAlertsErrorCreatingCategory();
        }
    }



    public void changeSceneToCategoryConfig() throws IOException{
        App.currentController.changeScene(Scenes.CATEGORYCONFIG,null);
    }


}
