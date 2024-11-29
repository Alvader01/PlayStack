package com.github.alvader01.View;

public enum Scenes {
    LAYOUT("view/layout.fxml"),
    REGISTER("view/Register.fxml"),
    USERCONFIG("view/UserConfig.fxml"),
    CATEGORYCONFIG("view/CategoryConfig.fxml"),
    GAMECONFIG("view/GameConfig.fxml"),
    MAINPAGE("view/Main.fxml"),
    ADDGAMETOCATEGORY("view/AddGameToCategory.fxml"),
    CREATECATEGORY("view/CreateCategory.fxml"),
    DELETECATEGORY("view/DeleteCategory.fxml"),
    CREATEGAME("view/CreateGame.fxml"),
    DELETEGAME("view/DeleteGame.fxml"),
    LOGIN("view/Login.fxml");

    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }


}
