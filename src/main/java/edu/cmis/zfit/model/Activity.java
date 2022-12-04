package edu.cmis.zfit.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY, property = "type") @JsonSubTypes({
        @JsonSubTypes.Type(value = BurnActivity.class, name = "burnActivity"),
        @JsonSubTypes.Type(value = ConsumptionActivity.class, name = "consumptionActivity")
})

// TODO considering capturing blood pressure
public interface Activity extends Comparable<Activity> {
    String id();
    String userId();
    int calories();
    int heartRateInBpm();
    int heartRateVariability();
    BloodPressure bloodPressure();
    int oxygenSaturationLevelPercentage();
    float weightInLbs();
    int heightInInches();
    Instant date();
    ActivityType activityType();
    int compareTo(Activity activity);
}