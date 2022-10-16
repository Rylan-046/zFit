package edu.cmis.zfit.model;

public class BurnActivity implements Activity {
    private String id;
    private int calories;
    private int heartRateInBpm;
    private int heartRateVariability;
    private int oxygenSaturationLevelPercentage;
    private float weightInLbs;
    private int heightInInches;
    private BurnActivityType type;
    private int steps;
    private DateRange duration;

    public BurnActivity() {
    }

    public BurnActivity(
            String id,
            int calories,
            int heartRateInBpm,
            int heartRateVariability,
            int oxygenSaturationLevelPercentage,
            float weightInLbs,
            int heightInInches,
            BurnActivityType type,
            int steps,
            DateRange duration) {
        this.id = id;
        this.calories = calories;
        this.heartRateInBpm = heartRateInBpm;
        this.heartRateVariability = heartRateVariability;
        this.oxygenSaturationLevelPercentage = oxygenSaturationLevelPercentage;
        this.weightInLbs = weightInLbs;
        this.heightInInches = heightInInches;
        this.type = type;
        this.steps = steps;
        this.duration = duration;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public int getHeartRateInBpm() {
        return heartRateInBpm;
    }

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
    public BurnActivityType getType() {
        return type;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public DateRange getDuration() {
        return duration;
    }

    public void setDuration(DateRange duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "BurnActivity{" +
                "id='" + id + '\'' +
                ", calories=" + calories +
                ", heartRateInBpm=" + heartRateInBpm +
                ", heartRateVariability=" + heartRateVariability +
                ", oxygenSaturationLevelPercentage=" + oxygenSaturationLevelPercentage +
                ", weightInLbs=" + weightInLbs +
                ", heightInInches=" + heightInInches +
                ", type=" + type +
                ", steps=" + steps +
                ", duration=" + duration +
                '}';
    }
}