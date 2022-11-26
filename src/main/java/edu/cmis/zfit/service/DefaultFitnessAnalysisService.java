package edu.cmis.zfit.service;

import edu.cmis.zfit.model.*;
import edu.cmis.zfit.repository.UserActivityRepository;
import edu.cmis.zfit.repository.UserProfileRepository;

import java.io.IOException;
import java.util.*;

public class DefaultFitnessAnalysisService implements FitnessAnalysisService {
    private static final int CALORIES_TO_LB = 3500;
    private UserActivityRepository userActivityRepository;
    private UserProfileRepository userProfileRepository;
    private Map<Integer, WeightRange> healthyWeightMap = new HashMap();
    private Map<String, Map<BurnActivityType, Integer>> userCaloriesPerActivityMap = new HashMap<>();
    private Map<String, Queue<ActivityType>> userActivityQueueMap = new HashMap<>();

    public DefaultFitnessAnalysisService(UserActivityRepository userActivityRepository, UserProfileRepository userProfileRepository) {
        this.userActivityRepository = userActivityRepository;
        this.userProfileRepository = userProfileRepository;

        /*Based on this chart:
        https://www.nhlbi.nih.gov/health/educational/healthdisp/pdf/tipsheets/Are-You-at-a-Healthy-Weight.pdf*/
        healthyWeightMap.put(58, new WeightRange(91, 115));
        healthyWeightMap.put(59, new WeightRange(94, 119));
        healthyWeightMap.put(60, new WeightRange(97, 123));
        healthyWeightMap.put(61, new WeightRange(100, 127));
        healthyWeightMap.put(62, new WeightRange(104, 131));
        healthyWeightMap.put(63, new WeightRange(107, 135));
        healthyWeightMap.put(64, new WeightRange(110, 140));
        healthyWeightMap.put(65, new WeightRange(114, 144));
        healthyWeightMap.put(66, new WeightRange(118, 148));
        healthyWeightMap.put(67, new WeightRange(121, 153));
        healthyWeightMap.put(68, new WeightRange(125, 158));
        healthyWeightMap.put(69, new WeightRange(128, 162));
        healthyWeightMap.put(70, new WeightRange(132, 167));
        healthyWeightMap.put(71, new WeightRange(136, 172));
        healthyWeightMap.put(72, new WeightRange(140, 177));
        healthyWeightMap.put(73, new WeightRange(144, 182));
        healthyWeightMap.put(74, new WeightRange(148, 186));
        healthyWeightMap.put(75, new WeightRange(152, 192));
        healthyWeightMap.put(76, new WeightRange(156, 197));
    }

    @Override
    public FitnessAnalysis ComputeFitness(String userId, DateRange dateRange) throws IOException {
        List<Activity> activityList = userActivityRepository.fetch(userId, dateRange);

        return null;
    }

    @Override
    public Queue<ActivityType> getActivityQueue(String userId) {
        Queue<ActivityType> activityQueue;

        try {
            activityQueue = userActivityQueueMap.get(userId);

            if(activityQueue == null) {
                computeCalorieAveragesPerActivity(userId);

                // Populate activity queue to reach target weight
                populateActivityQueue(userId);
            }
        } catch (IOException ex) {
            activityQueue = new PriorityQueue<>();
        }

        if (activityQueue == null) {
            activityQueue = new PriorityQueue<>();
            activityQueue.add(BurnActivityType.RUNNING);
        }

        return userActivityQueueMap.get(userId);
    }

    @Override
    public void removeCurrentActivity(String userId) {
        userActivityQueueMap.get(userId).remove();
    }

    @Override
    public void resetQueue(String userId) {
        userActivityQueueMap.remove(userId);
    }

    public void setActivityQueue(String userId, Queue<ActivityType> activityQueue) {
        activityQueue = userActivityQueueMap.put(userId, activityQueue);
    }

    public int getTargetWeightDelta(float weightInLbs, int heightInInches) {
        return getTargetWeightDelta((int) weightInLbs, healthyWeightMap.get(heightInInches));
    }

    private int getTargetWeightDelta(int weightInLbs, WeightRange healthyWeightRange) {
        int targetWeight = 0;

        if(weightInLbs < healthyWeightRange.lower()) {
            targetWeight = healthyWeightRange.lower();
        } else if(weightInLbs > healthyWeightRange.upper()) {
            targetWeight = healthyWeightRange.upper();
        }

        System.out.println("targetWeight:" +  +targetWeight + ", weightInLbs: " + weightInLbs);

        return weightInLbs - targetWeight;
    }

    // Compute overall status
    /*public HeartRating calculateHearRateStatus(UserProfile userProfile, float hrv) {

    }*/

    public float calculateBMI(float weightInLbs, int heightInInches) {
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

    private void computeCalorieAveragesPerActivity(String userId) throws IOException {
        for(BurnActivityType burnActivityType : BurnActivityType.values()) {
            System.out.println("BURN: " + burnActivityType);
            computeCalorieAveragesPerActivity(userId, burnActivityType);
        }
    }

    private void computeCalorieAveragesPerActivity(String userId, BurnActivityType activityType) throws IOException {
        int totalCalories = 0;

        List<Activity> activityList = userActivityRepository.fetch(userId, activityType);

        for(Activity activity : activityList) {
            totalCalories += activity.calories();
        }

        if (totalCalories > 0) {

            Map<BurnActivityType, Integer> caloriesPerActivityMap = new HashMap<>();
            caloriesPerActivityMap.put(activityType, totalCalories / activityList.size());

            userCaloriesPerActivityMap.put(userId, caloriesPerActivityMap);
        }
    }

    private void populateActivityQueue(String userId) throws IOException {
        List<Activity> activityList = userActivityRepository.fetch(userId);
        Activity lastActivity = activityList.get(activityList.size() - 1);

        int targetWeightDelta = getTargetWeightDelta(lastActivity.weightInLbs(), lastActivity.heightInInches());
        System.out.println("targetWeightDelta: " + targetWeightDelta);
        int targetCaloriesDelta = targetWeightDelta * CALORIES_TO_LB;

        if (this.userActivityQueueMap.get(userId) == null) {
            userActivityQueueMap.put(userId, new PriorityQueue<>());
        }

        if(targetCaloriesDelta < 0) {
            // Under Weight //TODO
        } else if (targetCaloriesDelta > 0) {
            populateActivityQueueForOverweight(userId, targetCaloriesDelta);
        } else {
            // Healthy weight (NOOP)
        }
    }

    private void populateActivityQueueForUnderweight(String userId, int targetCaloriesDelta) throws IOException {
        // TODO implement this?
    }

    private void populateActivityQueueForOverweight(String userId, int targetCaloriesDelta) throws IOException {
        ActivityType largestActivity = null;
        Map<BurnActivityType, Integer> caloriesPerActivityMap = userCaloriesPerActivityMap.get(userId);

        for(Map.Entry<BurnActivityType, Integer> entry : caloriesPerActivityMap.entrySet()) {
            if ((entry.getValue() <= targetCaloriesDelta &&
                    (largestActivity == null || entry.getValue() > caloriesPerActivityMap.get(largestActivity)))) {
                largestActivity = entry.getKey();
            }
        }

        System.out.println("largestActivity: " + caloriesPerActivityMap.get(largestActivity) + ", targetCaloriesDelta: " + targetCaloriesDelta);

        if (largestActivity != null) {
            int numActivityReps = targetCaloriesDelta / caloriesPerActivityMap.get(largestActivity);

            System.out.println("numReps: " + numActivityReps);

            // loop through and add the largest calorie burning activity to queue
            for(int index = 0; index < numActivityReps; index++) {
                System.out.println("add activity: " + largestActivity);
                userActivityQueueMap.get(userId).add(largestActivity);
            }

            int calorieRemainder = targetCaloriesDelta % caloriesPerActivityMap.get(largestActivity);

            System.out.println("targetCalories remainder: " + calorieRemainder);

            System.out.println("queue: " + userActivityQueueMap.get(userId));

            populateActivityQueueForOverweight(userId, calorieRemainder);
        }
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
