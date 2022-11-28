package edu.cmis.zfit;

import edu.cmis.zfit.model.Activity;
import edu.cmis.zfit.model.DateRange;
import edu.cmis.zfit.service.FitnessAnalysisService;
import edu.cmis.zfit.service.ServiceException;
import edu.cmis.zfit.service.ServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class AnalysisController {
    private FitnessAnalysisService serviceFactory;

    @FXML
    private AnchorPane analysisPane;

    @FXML
    private TextField txtHighestBurnActivity;

    @FXML
    private TextField txtLowestConsumptionActivity;

    @FXML
    private TextField txtBurntCalories;

    @FXML
    private TextField txtConsumedCalories;

    @FXML
    public void initialize() throws ServiceException, IOException {
        FitnessAnalysisService fitnessAnalysisService = ServiceFactory.getInstance().fitnessAnalysisService();

        //TODO get userID automatically
        String userId = "lex@gmail.com";
        Instant currentTime = Instant.now();

        Activity highestBurnActivity = fitnessAnalysisService.getBurnActivityWithHighestCalories(userId, new DateRange(
                currentTime.minus(14, ChronoUnit.DAYS),
                currentTime
        ));

        if(highestBurnActivity != null) {
            txtHighestBurnActivity.setText(String.valueOf(highestBurnActivity.activityType()));
            txtBurntCalories.setText(String.valueOf(highestBurnActivity.calories()));
        }

        Activity lowestConsumptionActivity = fitnessAnalysisService.getConsumptionActivityWithLowestCalories(userId, new DateRange(
                currentTime.minus(14, ChronoUnit.DAYS),
                currentTime
        ));

        if(lowestConsumptionActivity != null) {
            txtLowestConsumptionActivity.setText(String.valueOf(lowestConsumptionActivity.activityType()));
            txtConsumedCalories.setText(String.valueOf(lowestConsumptionActivity.calories()));
        }
    }

    public void onBackButtonClick() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("account-view.fxml")));
        analysisPane.getChildren().setAll(pane);
    }
}