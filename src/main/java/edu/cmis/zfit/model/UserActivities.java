package edu.cmis.zfit.model;

import java.util.ArrayList;
import java.util.List;

public class UserActivities {
    private UserProfile userProfile;
    private List<Activity> activityList;

    public UserActivities() {
    }

    public UserActivities(UserProfile userProfile) {
        this.userProfile = userProfile;
        activityList = new ArrayList<>();
    }

    public void add(Activity activity) {
        activityList.add(activity);
    }

    public void add(List<Activity> activities) {
        activityList.addAll(activities);
    }

    public boolean remove(String activityId) {
        Activity targetActivity = null;

        int index = 0;
        while(index < activityList.size() && targetActivity == null) {
            if(activityList.get(index).getId().equals(activityId)) {
                targetActivity = activityList.get(index);
            }

            index++;
        }

        if(targetActivity != null) {
            activityList.remove(targetActivity);
        }

        return targetActivity != null;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    @Override
    public String toString() {
        return "UserActivities{" +
                "userProfile=" + userProfile +
                ", activityList=" + activityList +
                '}';
    }
}