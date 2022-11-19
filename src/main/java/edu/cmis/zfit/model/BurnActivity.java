package edu.cmis.zfit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.Instant;

@JsonTypeName("burnActivity")
public record BurnActivity(
         String id,
         int calories,
         int heartRateInBpm,
         int heartRateVariability,
         BloodPressure bloodPressure,
         int oxygenSaturationLevelPercentage,
         float weightInLbs,
         int heightInInches,
         BurnActivityType activityType,
         int steps,
         DateRange duration) implements Activity {

    @JsonProperty("type")
    public String type() {
        return "burnActivity";
    }

    public DateRange getDuration() {
        return duration;
    }

    @Override
    @JsonIgnore
    public Instant date() {
        Instant date = null;

        if(duration != null) {
            date = getDuration().begin();
        }
        return date;
    }
}