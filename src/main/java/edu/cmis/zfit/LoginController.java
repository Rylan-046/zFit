package edu.cmis.zfit;

import edu.cmis.zfit.service.ServiceException;
import edu.cmis.zfit.service.ServiceFactory;
import edu.cmis.zfit.service.UserProfileService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    private UserProfileService userProfileService = ServiceFactory.getInstance().getUserProfileService();
    @FXML
    private AnchorPane loginPane;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink linkCreateAccount = new Hyperlink();

    public LoginController() throws ServiceException {
    }

    @FXML
    public void initialize() {
        btnLogin.setOnMouseEntered(event -> {
            btnLogin.setStyle("-fx-background-color: -fx-shadow-highlight-color");
            btnLogin.setCursor(Cursor.HAND);
        });

        btnLogin.setOnMouseExited(event -> {
            btnLogin.setStyle("-fx-background-color: #2289FF");
            btnLogin.setCursor(Cursor.DEFAULT);
        });

        btnLogin.setOnMousePressed(event -> {
            btnLogin.setStyle("-fx-background-color: #FFFFFF");
        });

        btnLogin.setOnMouseReleased(event -> {
            btnLogin.setStyle("-fx-background-color: -fx-shadow-highlight-color");
        });

        linkCreateAccount.setOnMouseClicked(event -> linkCreateAccount.setStyle("-fx-text-fill: lightblue"));
    }

    @FXML
    protected void onSignInButtonClick() throws IOException {
        String username = txtUsername.getText();
        String passwd = txtPassword.getText();

        if(userProfileService.isValidCredentials(username, passwd)) {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("account-view.fxml")));
            loginPane.getChildren().setAll(pane);
        }
    }

    @FXML
    protected void onCreateAccountLinkClick() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("create-account-view.fxml")));
        loginPane.getChildren().setAll(pane);
    }
}