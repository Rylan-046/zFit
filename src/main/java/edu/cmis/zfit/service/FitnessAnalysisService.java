package edu.cmis.zfit.service;

import edu.cmis.zfit.model.*;

import java.io.IOException;
import java.util.Queue;

public interface FitnessAnalysisService {
    FitnessAnalysis ComputeFitness(String userId, DateRange dateRange) throws IOException;
    Queue<ActivityType> getActivityQueue(String userId);

    void removeCurrentActivity(String userId);

    void resetQueue(String userId);

    ConsumptionActivity getConsumptionActivityWithLowestCalories(String userId, DateRange dateRange) throws IOException;

    BurnActivity getBurnActivityWithHighestCalories(String userId, DateRange dateRange) throws IOException;

    int getAverageConsumptionCalories(String userId, DateRange dateRange) throws IOException;

    int getAverageBurnCalories(String userId, DateRange dateRange) throws IOException;
}