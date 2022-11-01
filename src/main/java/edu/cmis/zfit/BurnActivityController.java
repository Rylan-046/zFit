package edu.cmis.zfit;

import edu.cmis.zfit.model.Activity;
import edu.cmis.zfit.model.BurnActivity;
import edu.cmis.zfit.model.BurnActivityType;
import edu.cmis.zfit.model.DateRange;
import edu.cmis.zfit.service.ActivityTrackerService;
import edu.cmis.zfit.service.ServiceException;
import edu.cmis.zfit.service.ServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BurnActivityController {
    private ActivityTrackerService activityTrackerService;
    private final List<Activity> activityList = new ArrayList<>();

    @FXML
    private AnchorPane addBurnActivityPane;

    @FXML
    private TextField txtCalories;

    @FXML
    private TextField txtHeartRateInBpm;

    @FXML
    private TextField txtHearRateVariability;

    @FXML
    private TextField txtOxygenSaturationLevelPercentage;

    @FXML
    private TextField txtWeightInLbs;

    @FXML
    private TextField txtHeightInInches;

    @FXML
    private TextField txtSteps;

    @FXML
    private DateTimePicker dateStart;

    @FXML
    private DateTimePicker dateEnd;

    @FXML
    private ComboBox cmbBurnActivity;

    public void initialize() throws ServiceException {
        activityTrackerService = ServiceFactory.getInstance().getActivityTrackerService();
    }

    public void onAddActivityButtonClick() throws ServiceException, IOException {
        int calories = Integer.parseInt(txtCalories.getText());
        int heartRateInBpm = Integer.parseInt(txtHeartRateInBpm.getText());
        int hearRateVariability = Integer.parseInt(txtHearRateVariability.getText());
        int oxygenSaturationLevelPercentage = Integer.parseInt(txtOxygenSaturationLevelPercentage.getText());
        int weightInLbs = Integer.parseInt(txtWeightInLbs.getText());
        int heightInInches = Integer.parseInt(txtHeightInInches.getText());
        int steps = Integer.parseInt(txtSteps.getText());

        DateRange dateRange = DateTimePicker.getDateRange(dateStart.getDateTimeValue(), dateEnd.getDateTimeValue());

        BurnActivityType burnActivityType = null;

        String activity = cmbBurnActivity.getSelectionModel().getSelectedItem().toString();

        switch (activity) {
            case "Resting" -> burnActivityType = BurnActivityType.RESTING;
            case "Walking" -> burnActivityType = BurnActivityType.WALKING;
            case "Running" -> burnActivityType = BurnActivityType.RUNNING;
            case "Weight Lifting" -> burnActivityType = BurnActivityType.WEIGHT_LIFTING;
        }

        //TODO get userID automatically
        String userId = "lex@gmail.com";

        activityList.add(
                new BurnActivity(
                        UUID.randomUUID().toString(),
                        calories,
                        heartRateInBpm,
                        hearRateVariability,
                        oxygenSaturationLevelPercentage,
                        weightInLbs,
                        heightInInches,
                        burnActivityType,
                        steps,
                        dateRange)
        );

        activityTrackerService.addUserActivities(userId, activityList);
        changeToAccountPane();
    }

    public void onCancelButtonClick() throws IOException {
        changeToAccountPane();
    }

    private void changeToAccountPane() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("account-view.fxml")));
        addBurnActivityPane.getChildren().setAll(pane);
    }
}