package edu.cmis.zfit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class AccountController {
    @FXML
    AnchorPane accountPane;

    @FXML
    public void initialize() {

    }

    @FXML
    protected void onMenuSignOutClick() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-view.fxml")));
        accountPane.getChildren().setAll(pane);
    }
}
