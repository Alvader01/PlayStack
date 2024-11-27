package com.github.alvader01.View;

public enum Scenes {
    LAYOUT("view/layout.fxml"),
    REGISTER("view/Register.fxml"),
    LOGIN("view/Login.fxml");

    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }


}
