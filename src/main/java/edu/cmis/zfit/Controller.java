package edu.cmis.zfit;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onZFitButtonClick() {
        welcomeText.setText("Welcome to zFit!");
    }
}