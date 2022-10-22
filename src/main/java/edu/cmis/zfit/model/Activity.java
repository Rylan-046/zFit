package edu.cmis.zfit.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY, property = "type") @JsonSubTypes({
        @JsonSubTypes.Type(value = BurnActivity.class, name = "burnActivity"),
        @JsonSubTypes.Type(value = ConsumptionActivity.class, name = "consumptionActivity")
})
public interface Activity {
    String getId();
    void setId(String id);
    int getCalories();
    void setCalories(int calories);
    int getHeartRateInBpm();
    void setHeartRateInBpm(int heartRateInBpm);
    int getHeartRateVariability();
    void setHeartRateVariability(int heartRateVariability);
    int getOxygenSaturationLevelPercentage();
    void setOxygenSaturationLevelPercentage(int oxygenSaturationLevelPercentage);
    float getWeightInLbs();
    void setWeightInLbs(float weightInLbs);
    int getHeightInInches();
    void setHeightInInches(int heightInInches);
    Instant getDate();
    ActivityType getActivityType();
}