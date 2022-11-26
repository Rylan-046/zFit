package edu.cmis.zfit.service;

import edu.cmis.zfit.model.ActivityType;
import edu.cmis.zfit.model.DateRange;
import edu.cmis.zfit.model.FitnessAnalysis;

import java.io.IOException;
import java.util.Queue;

public interface FitnessAnalysisService {
    FitnessAnalysis ComputeFitness(String userId, DateRange dateRange) throws IOException;
    Queue<ActivityType> getActivityQueue(String userId);

    void removeCurrentActivity(String userId);

    void resetQueue(String userId);
}