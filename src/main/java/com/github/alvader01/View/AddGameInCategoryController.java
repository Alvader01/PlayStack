package com.github.alvader01.View;

import com.github.alvader01.App;
import com.github.alvader01.Model.DAO.CategoryDAO;
import com.github.alvader01.Model.DAO.GameDAO;
import com.github.alvader01.Model.DAO.GameInCategoryDAO;
import com.github.alvader01.Model.Entity.Category;
import com.github.alvader01.Model.Entity.Game;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddGameInCategoryController extends Controller implements Initializable {
    @FXML
    private ComboBox<String> CategoryComboBox;
    @FXML
    private ComboBox<String> GamesComboBox;
    @FXML
    private ComboBox<String> DeleteGameComboBox;
    @FXML
    private TableView<Game> GameTableView;
    @FXML
    private TableColumn<Game, String> GameNameColumn;
    @FXML
    private TableColumn<Game, String> GamePlatformColumn;

    @FXML
    private ImageView back;

    @FXML
    private ImageView info;
    @FXML
    private Button AddButton;
    @FXML
    private Button DeleteButton;



    private int currentCategoryId;
    private Map<String, Integer> categoryNameToIdMap;

    @Override
    public void onOpen(Object input) throws IOException {
        if (input instanceof Integer) {
            currentCategoryId = (Integer) input;
            loadGameInCategory(currentCategoryId);
        }
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Category> categories = CategoryDAO.build().findAll();

        categoryNameToIdMap = new HashMap<>();
        for (Category category : categories) {
           categoryNameToIdMap.put(category.getName(), category.getId());
        }

        List<String> categoryNames = categories.stream().map(Category::getName).collect(Collectors.toList());
        CategoryComboBox.setItems(FXCollections.observableArrayList(categoryNames));

        List<Game> game = GameDAO.build().findAll();
        List<String> speciesNames = game.stream().map(Game::getName).collect(Collectors.toList());
        GamesComboBox.setItems(FXCollections.observableArrayList(speciesNames));

        CategoryComboBox.setOnAction(event -> {
            String selectedCategoryName = CategoryComboBox.getValue();
            if (selectedCategoryName != null) {
                try {
                    int selectedCategoryId = categoryNameToIdMap.get(selectedCategoryName);
                    onOpen(selectedCategoryId);
                    loadGameInCategory(selectedCategoryId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        GameNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        GamePlatformColumn.setCellValueFactory(new PropertyValueFactory<>("platform"));
    }


    private void loadGameInCategory(int categoryId) {
        GameInCategoryDAO gameInCategoryDAO = new GameInCategoryDAO();
        List<Game> gameInCategory = gameInCategoryDAO.findAllGamesInCategory(categoryId);
        if (!gameInCategory.isEmpty()) {
            List<String> gameNames = gameInCategory.stream().map(Game::getName).collect(Collectors.toList());
            DeleteGameComboBox.setItems(FXCollections.observableArrayList(gameNames));
            GameTableView.setItems(FXCollections.observableArrayList(gameInCategory));
        } else {
            DeleteGameComboBox.getItems().clear();
            GameTableView.getItems().clear();
        }
    }



    public void addGameToCategory() {
        String selectedCategoryName = CategoryComboBox.getValue();
        String gameName = GamesComboBox.getValue();
        CategoryDAO categoryDAO = new CategoryDAO();

        if (categoryNameToIdMap.containsKey(selectedCategoryName)) {
            int categoryId = categoryNameToIdMap.get(selectedCategoryName);
            Category category = categoryDAO.findById(categoryId);
            GameDAO gameDAO = new GameDAO();
            Game game = gameDAO.findGameByName(gameName);

            if (category != null && game != null) {
                GameInCategoryDAO gameInCategoryDAO = GameInCategoryDAO.build();
                if (gameInCategoryDAO.isGameInCategory(categoryId, game.getId())) {
                    AppController.ShowAlertsGameAlreadyExistsInCategory();
                } else {
                    gameInCategoryDAO.addGameToCategory(categoryId, game.getId());
                    AppController.ShowAlertsSuccessfullyAddGameToCategory();
                }
                loadGameInCategory(categoryId);
            } else {
                AppController.ShowAlertsGameNotFound();
            }
        }
    }



    public void deleteGameFromCategory() {
        String selectedCategoryName = CategoryComboBox.getValue();
        String gameName = DeleteGameComboBox.getValue();
        CategoryDAO categoryDAO = new CategoryDAO();

        if (categoryNameToIdMap.containsKey(selectedCategoryName)) {
            int categoryId = categoryNameToIdMap.get(selectedCategoryName);
            Category category = categoryDAO.findById(categoryId);
            GameDAO gameDAO = new GameDAO();
            Game game = gameDAO.findGameByName(gameName);

            if (category != null && game != null) {
                GameInCategoryDAO gameInCategoryDAO = GameInCategoryDAO.build();
                boolean result = gameInCategoryDAO.removeGameFromCategory(categoryId, game.getId());
                if (result) {
                    loadGameInCategory(categoryId);
                }
            }
        }
    }


    public void changeSceneToCategoryConfig() throws IOException {
        App.currentController.changeScene(Scenes.CATEGORYCONFIG, null);
    }

    public void ShowInfo() {
        AppController.ShowInformationAddGame();
    }
}
