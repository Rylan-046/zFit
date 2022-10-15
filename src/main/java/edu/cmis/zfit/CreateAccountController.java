package edu.cmis.zfit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class CreateAccountController {
    @FXML
    AnchorPane createAccountPane;

    @FXML
    private Button btnCreateAccount;

    @FXML
    public void initialize() {
        btnCreateAccount.setOnMouseEntered(event -> btnCreateAccount.setStyle("-fx-background-color: -fx-shadow-highlight-color"));
        btnCreateAccount.setOnMouseExited(event -> btnCreateAccount.setStyle("-fx-background-color: #2289FF"));
    }

    @FXML
    protected void onCreateAccountButtonClick () throws IOException {
        //TODO Save credentials to file

        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-view.fxml")));
        createAccountPane.getChildren().setAll(pane);
    }
}
