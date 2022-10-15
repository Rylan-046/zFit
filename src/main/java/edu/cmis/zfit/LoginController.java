package edu.cmis.zfit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    AnchorPane loginPane;

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink linkCreateAccount = new Hyperlink();

    @FXML
    public void initialize() {
        btnLogin.setOnMouseEntered(event -> btnLogin.setStyle("-fx-background-color: -fx-shadow-highlight-color"));
        btnLogin.setOnMouseExited(event -> btnLogin.setStyle("-fx-background-color: #2289FF"));

        linkCreateAccount.setOnMouseClicked(event -> linkCreateAccount.setStyle("-fx-text-fill: lightblue"));
    }

    @FXML
    protected void onLoginButtonClick() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("account-view.fxml")));
        loginPane.getChildren().setAll(pane);
    }

    @FXML
    protected void onCreateAccountLinkClick() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("create-account-view.fxml")));
        loginPane.getChildren().setAll(pane);
    }
}