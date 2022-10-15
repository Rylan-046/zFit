package edu.cmis.zfit.model;

public class ConsumptionActivity implements Activity {
    private String id;
    private UserProfile userProfile;
    private int calories;
    private int heartRateInBpm;
    private int heartRateVariability;
    private int oxygenSaturationLevelPercentage;
    private float weightInLbs;
    private int heightInInches;
    private BurnActivityType type;
    private int steps;
    private DateRange duration;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public UserProfile getUserProfile() {
        return userProfile;
    }

    @Override
    public int getCalories() {
        return calories;
    }

    @Override
    public int getHeartRateInBpm() {
        return heartRateInBpm;
    }

    @Override
    public int getHeartRateVariability() {
        return heartRateVariability;
    }

    @Override
    public int getOxygenSaturationLevelPercentage() {
        return oxygenSaturationLevelPercentage;
    }

    @Override
    public float getWeightInLbs() {
        return weightInLbs;
    }

    @Override
    public int getHeightInInches() {
        return heightInInches;
    }

    public BurnActivityType getType() {
        return type;
    }

    public int getSteps() {
        return steps;
    }

    public DateRange getDuration() {
        return duration;
    }
}