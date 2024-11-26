package com.github.alvader01.View;

import java.io.IOException;

import com.github.alvader01.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}