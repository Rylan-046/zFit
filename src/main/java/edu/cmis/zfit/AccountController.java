package edu.cmis.zfit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class AccountController {
    @FXML
    private AnchorPane accountPane;

    @FXML
    public void initialize() {

    }

    @FXML
    protected void onMenuSignOutClick() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-view.fxml")));
        accountPane.getChildren().setAll(pane);
    }

    public void onAddBurnActivityClick() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add-burn-activity-view.fxml")));
        accountPane.getChildren().setAll(pane);
    }

    public void onAddConsumptionActivityClick() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add-consumption-activity-view.fxml")));
        accountPane.getChildren().setAll(pane);
    }

    public void onMenuAboutClick() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("help-view.fxml")));
        accountPane.getChildren().setAll(pane);
    }
}
