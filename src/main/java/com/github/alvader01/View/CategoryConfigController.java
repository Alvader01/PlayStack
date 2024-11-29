package com.github.alvader01.View;

import com.github.alvader01.App;
import com.github.alvader01.Model.DAO.CategoryDAO;
import com.github.alvader01.Model.Entity.Category;
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
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryConfigController extends Controller implements Initializable {
    @FXML
    Button crear;
    @FXML
    ImageView volver;
    @FXML
    TableView<Category> table;
    @FXML
    TableColumn<Category, Integer> Id;
    @FXML
    TableColumn<Category, String> name;
    @FXML
    TableColumn<Category, String> description;
    @FXML
    Button eliminar;
    @FXML
    ImageView info;
    @FXML
    Button anadir;


    private ObservableList<Category> categories;

    @Override
    public void onOpen(Object input) throws IOException {
        User currentUser = UserSession.getCurrentUser();
        if (currentUser == null) {
            return;
        }

        CategoryDAO cDAO = new CategoryDAO();
        List<Category> categories = cDAO.findByUser(currentUser.getUsername());
        this.categories = FXCollections.observableArrayList(categories);
        table.setItems(this.categories);
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategoryDAO cDAO = new CategoryDAO();
        table.setEditable(true);

        table.setRowFactory(tv -> {
            TableRow<Category> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Category category = table.getSelectionModel().getSelectedItem();
                if (event.getClickCount() == 3 && !row.isEmpty() && category != null) {
                    Category category1 = row.getItem();
                    try {
                        App.currentController.changeScene(Scenes.ADDGAMETOCATEGORY, category1.getId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });

        Id.setCellValueFactory(category -> new SimpleIntegerProperty(category.getValue().getId()).asObject());
        name.setCellValueFactory(category -> new SimpleStringProperty(category.getValue().getName()));
        description.setCellValueFactory(category -> new SimpleStringProperty(category.getValue().getDescription()));

        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> {
            if (event.getNewValue().equals(event.getOldValue())) {
                return;
            }
            Category category = event.getRowValue();
            category.setName(event.getNewValue());
            cDAO.update(category);
        });


        description.setCellFactory(column -> new TableCell<Category, String>() {

            private final Text text = new Text();
            private final TextField textField = new TextField();

            @Override
            public void startEdit() {
                super.startEdit();
                textField.setText(getItem());
                setGraphic(textField);
                setText(null);
                textField.selectAll();
                textField.setOnAction(event -> commitEdit(textField.getText()));
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setGraphic(text);
                setText(getItem());
            }

            @Override
            public void commitEdit(String newValue) {
                super.commitEdit(newValue);
                if (!newValue.equals(getItem())) {
                    Category category = getTableView().getItems().get(getIndex());
                    category.setDescription(newValue);
                    new CategoryDAO().update(category);
                }
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (!isEditing()) {
                        text.setText(item);
                        text.setWrappingWidth(description.getWidth() - 10);
                        setGraphic(text);
                        setText(null);
                    }
                }
            }
        });

        description.setOnEditCommit(event -> {
            if (event.getNewValue().equals(event.getOldValue())) {
                return;
            }
            Category category = event.getRowValue();
            category.setDescription(event.getNewValue());
            cDAO.update(category);
        });
    }

    public void changeSceneToMainPage() throws IOException{
        App.currentController.changeScene(Scenes.MAINPAGE,null);
    }

    public void ShowInfo(){
        AppController.ShowInformationCategory();
    }
    public void changeSceneToCreateCategory() throws IOException{
        App.currentController.changeScene(Scenes.CREATECATEGORY,null);
    }
    public void changeSceneToAddGameToCategory() throws IOException{
        App.currentController.changeScene(Scenes.ADDGAMETOCATEGORY,null);
    }
    public void changeSceneToDeleteCategory() throws IOException{
        App.currentController.changeScene(Scenes.DELETECATEGORY,null);
    }
}
