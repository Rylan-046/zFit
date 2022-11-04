package edu.cmis.zfit;

import edu.cmis.zfit.service.ServiceException;
import edu.cmis.zfit.service.ServiceFactory;
import edu.cmis.zfit.service.UserProfileService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class CreateAccountController {
    private UserProfileService userProfileService;

    @FXML
    private AnchorPane createAccountPane;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPasswd;

    @FXML
    private Button btnCreateAccount;

    @FXML
    public void initialize() throws ServiceException {
        userProfileService = ServiceFactory.getInstance().getUserProfileService();

        btnCreateAccount.setOnMouseEntered(event -> {
            btnCreateAccount.setStyle("-fx-background-color: -fx-shadow-highlight-color");
            btnCreateAccount.setCursor(Cursor.HAND);
        });

        btnCreateAccount.setOnMouseExited(event -> {
            btnCreateAccount.setStyle("-fx-background-color: #2289FF");
            btnCreateAccount.setCursor(Cursor.DEFAULT);
        });

        btnCreateAccount.setOnMousePressed(event -> {
            btnCreateAccount.setStyle("-fx-background-color: #FFFFFF");
        });

        btnCreateAccount.setOnMouseReleased(event -> {
            btnCreateAccount.setStyle("-fx-background-color: -fx-shadow-highlight-color");
        });
    }

    @FXML
    protected void onCreateAccountButtonClick () throws IOException {
        String username = txtUsername.getText();
        String passwd = txtPasswd.getText();

        System.out.println(username + " " + passwd);

        if(userProfileService.isUserIdAvailable(username)) {
            userProfileService.saveCredentials(username, passwd);
        }

        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-view.fxml")));
        createAccountPane.getChildren().setAll(pane);
    }
}
