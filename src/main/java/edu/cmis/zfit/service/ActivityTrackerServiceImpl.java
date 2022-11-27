package edu.cmis.zfit.service;

import edu.cmis.zfit.model.Activity;
import edu.cmis.zfit.repository.UserActivityRepository;
import edu.cmis.zfit.repository.UserProfileRepository;
import edu.cmis.zfit.util.ActivityQuickSorter;
import edu.cmis.zfit.util.ActivitySorter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ActivityTrackerServiceImpl implements ActivityTrackerService {
    private UserActivityRepository userActivityRepository;
    private UserProfileRepository userProfileRepository;
    private final ActivitySorter activitySorter = new ActivityQuickSorter();

    public ActivityTrackerServiceImpl(UserActivityRepository userActivityRepository, UserProfileRepository userProfileRepository) {
        this.userActivityRepository = userActivityRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public List<Activity> getUserActivities(String userId, boolean sortByDate) throws ServiceException {
        List<Activity> activityList;

        try {
            List<Activity> tmpActivityList = userActivityRepository.fetch(userId);

            if(sortByDate) {
                activityList = activitySorter.sortByDate((ArrayList<Activity>) tmpActivityList);
            } else {
                activityList = tmpActivityList;
            }

        } catch (IOException e) {
            String errMsg = "Retrieving activities for user='" + userId + "' failure!";
            System.out.println(errMsg);
            throw new ServiceException(errMsg, e);
        }

        return activityList;
    }

    @Override
    public void addUserActivities(String userId, List<Activity> activityList) throws ServiceException {

        validateActivities(activityList);

        try {
            userActivityRepository.save(userId, activityList);
        } catch (IOException e) {
            String errMsg = "Saving activities for user='" + userId + "' failure!";
            System.out.println(errMsg);
            throw new ServiceException(errMsg, e);
        }
    }

    private void validateActivities(List<Activity> activityList) throws IllegalArgumentException {
        for (Activity activity: activityList) {
            int minHeight = 58;
            int maxHeight = 76;
            if (activity.heightInInches() < minHeight || activity.heightInInches() > maxHeight) {
                throw new IllegalArgumentException("Invalid height: height must be between " + minHeight + " and " + maxHeight);
            }
        }
    }

    private Path getBasePath() {
        return Paths.get("");
    }
}