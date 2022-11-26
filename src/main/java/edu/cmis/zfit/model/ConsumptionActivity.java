package edu.cmis.zfit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.Instant;

@JsonTypeName("consumptionActivity")
public record ConsumptionActivity(
     String id,
     int calories,
     int heartRateInBpm,
     int heartRateVariability,
     BloodPressure bloodPressure,
     int oxygenSaturationLevelPercentage,
     float weightInLbs,
     int heightInInches,
     Instant date,
     ConsumptionActivityType activityType) implements Activity {

    @JsonProperty("type")
    public String type() {
        return "consumptionActivity";
    }

    @Override
    public Instant date() {
        return date;
    }

    @Override
    public ConsumptionActivityType activityType() {
        return activityType;
    }

    @Override
    public int compareTo(Activity activity) {
        return this.date().compareTo(activity.date());
    }
}