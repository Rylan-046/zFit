package edu.cmis.zfit.service;

import edu.cmis.zfit.model.*;
import edu.cmis.zfit.repository.UserActivityRepository;
import edu.cmis.zfit.repository.UserProfileRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DefaultFitnessAnalysisService {
    private static final int CALORIES_TO_LB = 3500;
    private UserActivityRepository userActivityRepository;
    private UserProfileRepository userProfileRepository;


    public DefaultFitnessAnalysisService(UserActivityRepository userActivityRepository, UserProfileRepository userProfileRepository) {
        this.userActivityRepository = userActivityRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public FitnessAnalysis computeFitness(String userId, DateRange dateRange) throws IOException {

        List<Activity> activityList = userActivityRepository.fetch(userId, dateRange);


        return null;
    }

    public float getTargetBmi(float weightInLbs, float heightInInches) {
        return 0f;
    }

    // Compute overall status
    /*public HeartRating calculateHearRateStatus(UserProfile userProfile, float hrv) {

    }*/

    public float calculateBMI(float weightInLbs, float heightInInches) {
        return (float) (weightInLbs / Math.pow(heightInInches, 2) * 703);
    }

    private HeartRating calculateHrvStatus(UserProfile userProfile, float hrv) {
        HeartRating heartRating = null;

        if (userProfile.getAge() <= 25) {
            if (hrv >= 55 && hrv <= 105) {
                heartRating = HeartRating.GOOD;
            } else {
                heartRating = HeartRating.BAD;
            }
        } else if (userProfile.getAge() > 25 && userProfile.getAge() < 60) {
            if (hrv > 45 && hrv <= 85) {
                heartRating = HeartRating.GOOD;
            } else {
                heartRating = HeartRating.BAD;
            }
        } else if (userProfile.getAge() >= 60) {
            if (hrv >= 25 && hrv <= 45) {
                heartRating = HeartRating.GOOD;
            } else {
                heartRating = HeartRating.BAD;
            }
        }

        return heartRating;
    }

    private BloodPressureStage getBloodPressureStage(BloodPressure bloodPressure) {
        Iterator<BloodPressureStage> iterator = Arrays.stream(BloodPressureStage.values()).iterator();

        BloodPressureStage bloodPressureStage = null;
        BloodPressureStage element;

        while(bloodPressureStage == null && iterator.hasNext()) {
            element = iterator.next();
            if(element.getDiastolicRange().within(bloodPressure.diastolic()) &&
                    element.getSystolicRange().within(bloodPressure.systolic())) {
                bloodPressureStage = element;
            }
        }

        return bloodPressureStage;
    }

   /* private float lbsToKgs(float lbs) {
        // 0.453592 kg = 1 lb
        return lbs * 0.453592f;
    }

    private float inchesToCm(float inches) {
        // 2.54 cm = 1 inch
        return inches * 2.54f;
    }*/
}
