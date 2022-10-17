package edu.cmis.zfit.model;

import java.time.Instant;

public class ConsumptionActivity implements Activity {
    private String id;
    private int calories;
    private int heartRateInBpm;
    private int heartRateVariability;
    private int oxygenSaturationLevelPercentage;
    private float weightInLbs;
    private int heightInInches;
    private Instant date;
    private ConsumptionActivityType type;

    public ConsumptionActivity() {
    }

    public ConsumptionActivity(
            String id,
            int calories,
            int heartRateInBpm,
            int heartRateVariability,
            int oxygenSaturationLevelPercentage,
            float weightInLbs,
            int heightInInches,
            Instant date,
            ConsumptionActivityType type) {
        this.id = id;
        this.calories = calories;
        this.heartRateInBpm = heartRateInBpm;
        this.heartRateVariability = heartRateVariability;
        this.oxygenSaturationLevelPercentage = oxygenSaturationLevelPercentage;
        this.weightInLbs = weightInLbs;
        this.heightInInches = heightInInches;
        this.date = date;
        this.type = type;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int getCalories() {
        return calories;
    }

    @Override
    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public int getHeartRateInBpm() {
        return heartRateInBpm;
    }

    @Override
    public void setHeartRateInBpm(int heartRateInBpm) {
        this.heartRateInBpm = heartRateInBpm;
    }

    @Override
    public int getHeartRateVariability() {
        return heartRateVariability;
    }

    @Override
    public void setHeartRateVariability(int heartRateVariability) {
        this.heartRateVariability = heartRateVariability;
    }

    @Override
    public int getOxygenSaturationLevelPercentage() {
        return oxygenSaturationLevelPercentage;
    }

    @Override
    public void setOxygenSaturationLevelPercentage(int oxygenSaturationLevelPercentage) {
        this.oxygenSaturationLevelPercentage = oxygenSaturationLevelPercentage;
    }

    @Override
    public float getWeightInLbs() {
        return weightInLbs;
    }

    @Override
    public void setWeightInLbs(float weightInLbs) {
        this.weightInLbs = weightInLbs;
    }

    @Override
    public int getHeightInInches() {
        return heightInInches;
    }

    @Override
    public void setHeightInInches(int heightInInches) {
        this.heightInInches = heightInInches;
    }

    @Override
    public Instant getDate() {
        return date;
    }
    public void setDate(Instant date) {
        this.date = date;
    }

    @Override
    public ConsumptionActivityType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ConsumptionActivity{" +
                "id='" + id + '\'' +
                ", calories=" + calories +
                ", heartRateInBpm=" + heartRateInBpm +
                ", heartRateVariability=" + heartRateVariability +
                ", oxygenSaturationLevelPercentage=" + oxygenSaturationLevelPercentage +
                ", weightInLbs=" + weightInLbs +
                ", heightInInches=" + heightInInches +
                ", date=" + date +
                ", type=" + type +
                '}';
    }
}