package edu.cmis.zfit;

import edu.cmis.zfit.model.Activity;
import edu.cmis.zfit.model.BloodPressure;
import edu.cmis.zfit.model.ConsumptionActivity;
import edu.cmis.zfit.model.ConsumptionActivityType;
import edu.cmis.zfit.service.ActivityTrackerService;
import edu.cmis.zfit.service.ServiceException;
import edu.cmis.zfit.service.ServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ConsumptionActivityController {
    private ActivityTrackerService activityTrackerService;
    private final List<Activity> activityList = new ArrayList<>();
    private final ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(Instant.now());

    @FXML
    private AnchorPane addConsumptionActivityPane;

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
    private DateTimePicker dateTime;

    @FXML
    private ComboBox cmbConsumptionActivity;

    @FXML
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

        System.out.println("dateTime : " + dateTime.getDateTimeValue());

        Instant instantDateTime = dateTime.getDateTimeValue().toInstant(zoneOffset).truncatedTo(ChronoUnit.MINUTES);

        LocalDateTime dateTime = LocalDateTime.ofInstant(instantDateTime, zoneOffset);
        System.out.println("Truncated dateTime : " + dateTime);

        ConsumptionActivityType consumptionActivityType;

        if(cmbConsumptionActivity.getSelectionModel().getSelectedItem().toString().equals("Eating")) {
            consumptionActivityType = ConsumptionActivityType.EATING;
        } else {
            consumptionActivityType = ConsumptionActivityType.DRINKING;
        }


        //TODO get userID automatically
        String userId = "lex@gmail.com";

        activityList.add(
                new ConsumptionActivity(
                        UUID.randomUUID().toString(),
                        calories,
                        heartRateInBpm,
                        hearRateVariability,
                        new BloodPressure(100,100),
                        oxygenSaturationLevelPercentage,
                        weightInLbs,
                        heightInInches,
                        //TODO More Advanced Time to UI
                        instantDateTime,
                        consumptionActivityType
                )
        );

        activityTrackerService.addUserActivities(userId, activityList);
        changeToAccountPane();
    }

    public void onCancelButtonClick() throws IOException {
        changeToAccountPane();
    }

    private void changeToAccountPane() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("account-view.fxml")));
        addConsumptionActivityPane.getChildren().setAll(pane);
    }
}