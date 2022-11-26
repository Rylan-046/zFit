package edu.cmis.zfit;

import edu.cmis.zfit.model.ActivityType;
import edu.cmis.zfit.service.FitnessAnalysisService;
import edu.cmis.zfit.service.ServiceException;
import edu.cmis.zfit.service.ServiceFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Queue;

public class AccountController {
    private FitnessAnalysisService fitnessAnalysisService = ServiceFactory.getInstance().fitnessAnalysisService();

    @FXML
    private AnchorPane accountPane;

    @FXML
    private ListView<String> todoQueue;

    public AccountController() throws ServiceException {
    }

    @FXML
    public void initialize() throws IOException {
        String userId = "lex@gmail.com";
        ArrayList<String> activities = new ArrayList<>();

        Queue<ActivityType> queue = fitnessAnalysisService.getActivityQueue(userId);

        for (int index = 0; index < queue.size(); index++) {
            activities.add(queue.peek().toString());
        }

        ObservableList<String> items = FXCollections.observableArrayList(activities);
        todoQueue.setItems(items);
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
