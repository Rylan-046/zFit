package edu.cmis.zfit.model;

public interface Activity {
    String getId();
    UserProfile getUserProfile();
    int getCalories();
    int getHeartRateInBpm();
    int getHeartRateVariability();
    int getOxygenSaturationLevelPercentage();
    float getWeightInLbs();
    int getHeightInInches();
}