package edu.cmis.zfit.service;

import edu.cmis.zfit.model.DateRange;
import edu.cmis.zfit.model.FitnessAnalysis;

public interface FitnessAnalysisService {
    FitnessAnalysis ComputeFitness(String userId, DateRange dateRange);
}