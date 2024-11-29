package com.github.alvader01.View;

import com.github.alvader01.App;
import com.github.alvader01.Model.DAO.CategoryDAO;
import com.github.alvader01.Model.Entity.Category;
import com.github.alvader01.Model.Singleton.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DeleteCategoryController extends Controller implements Initializable {
    @FXML
    private ComboBox<String> CategoryComboBox;
    @FXML
    private TableView<Category> CategoryTableView;
    @FXML
    private TableColumn<Category, Integer> IdColumn;
    @FXML
    private TableColumn<Category, String> NameColumn;
    @FXML
    private TableColumn<Category, String> DescriptionColumn;
    @FXML
    private ImageView back;
    @FXML
    private ImageView info;

    @FXML
    private Button deleteButton;

    private ObservableList<Category> categories;
    private List<Category> allCategories;

    @Override
    public void onOpen(Object input) throws IOException {

        loadUserCategories();
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));


        DescriptionColumn.setCellFactory(column -> {
            return new TableCell<>() {
                private final Text text;

                {
                    text = new Text();
                    text.wrappingWidthProperty().bind(DescriptionColumn.widthProperty().subtract(10));
                    text.getStyleClass().add("text-cell");
                    setGraphic(text);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        text.setText(null);
                    } else {
                        text.setText(item);
                    }
                }
            };
        });


        loadUserCategories();


        CategoryComboBox.setOnAction(event -> {
            String selectedCategoryName = CategoryComboBox.getValue();
            if (selectedCategoryName != null) {
                Category selectedCategory = findCategoryByName(selectedCategoryName);
                if (selectedCategory != null) {
                    CategoryTableView.setItems(FXCollections.observableArrayList(selectedCategory));
                }
            }
        });
    }

    /**
     * Load categories associated with the current user and populate ComboBox and TableView.
     */
    private void loadUserCategories() {
        try {
            String username = UserSession.getCurrentUser().getUsername();
            CategoryDAO cDAO = new CategoryDAO();
            allCategories = cDAO.findByUser(username);
            categories = FXCollections.observableArrayList(allCategories);


            List<String> categoryNames = allCategories.stream()
                    .map(Category::getName)
                    .collect(Collectors.toList());
            CategoryComboBox.setItems(FXCollections.observableArrayList(categoryNames));


            CategoryTableView.setItems(categories);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Find a category by its name from the loaded categories.
     *
     * @param name the name of the category
     * @return the matching Category object or null if not found
     */
    private Category findCategoryByName(String name) {
        return allCategories.stream()
                .filter(category -> category.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Deletes the selected category from the database and updates the UI.
     */
    public void deleteCategory() {
        String selectedCategoryName = CategoryComboBox.getValue();
        if (selectedCategoryName != null) {
            Category categoryToDelete = findCategoryByName(selectedCategoryName);
            if (categoryToDelete != null) {
                try {
                    CategoryDAO cDAO = new CategoryDAO();
                    cDAO.delete(categoryToDelete);


                    allCategories.remove(categoryToDelete);
                    categories.remove(categoryToDelete);

                    List<String> updatedCategoryNames = allCategories.stream()
                            .map(Category::getName)
                            .collect(Collectors.toList());
                    CategoryComboBox.setItems(FXCollections.observableArrayList(updatedCategoryNames));
                    CategoryTableView.setItems(categories);

                    AppController.ShowAlertsSuccessfullyDeleteCategory();

                } catch (SQLException e) {
                    e.printStackTrace();
                    AppController.ShowAlertsErrorDeleteCategory();
                }
            }
        }
    }

    public void ShowInfo() {
        AppController.ShowInformationDeleteCategory();
    }

    public void changeSceneToCategoryConfig() throws IOException {
        App.currentController.changeScene(Scenes.CATEGORYCONFIG, null);
    }
}


