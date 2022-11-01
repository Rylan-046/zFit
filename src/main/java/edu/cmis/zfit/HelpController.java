package edu.cmis.zfit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class HelpController {
    @FXML
    private AnchorPane helpPane;

    @FXML
    protected void onBackButtonClick() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("account-view.fxml")));
        helpPane.getChildren().setAll(pane);
    }
}