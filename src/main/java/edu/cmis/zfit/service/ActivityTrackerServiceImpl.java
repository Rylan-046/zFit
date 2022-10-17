package edu.cmis.zfit.service;

import edu.cmis.zfit.model.Activity;
import edu.cmis.zfit.model.UserActivities;
import edu.cmis.zfit.model.UserProfile;
import edu.cmis.zfit.repository.UserActivitiesFileRepository;
import edu.cmis.zfit.repository.UserActivitiesRepository;
import edu.cmis.zfit.repository.UserFileRepository;
import edu.cmis.zfit.repository.UserRepository;
import edu.cmis.zfit.util.ActivityQuickSorter;
import edu.cmis.zfit.util.ActivitySorter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ActivityTrackerServiceImpl implements ActivityTrackerService {
    private static final String FILE_SUFFIX = "profile.json";
    private final UserActivitiesRepository userActivitiesRepository = new UserActivitiesFileRepository(getBasePath());
    private final UserRepository userRepository = new UserFileRepository(getBasePath());
    private final ActivitySorter activitySorter = new ActivityQuickSorter();

    @Override
    public UserActivities getUserActivities(String userId, boolean sortByDate) throws ServiceException {
        UserActivities userActivities;

        try {
            UserActivities tmpUserActivities = userActivitiesRepository.fetch(userId);
            if(sortByDate) {
                List<Activity> sortedActivityList = activitySorter.sortByDate((ArrayList<Activity>) tmpUserActivities.getActivityList());
                userActivities = new UserActivities(tmpUserActivities.getUserProfile());
                userActivities.add(sortedActivityList);
            } else {
                userActivities = tmpUserActivities;
            }

        } catch (IOException e) {
            String errMsg = "Retrieving activities for user='" + userId + "' failure!";
            System.out.println(errMsg);
            throw new ServiceException(errMsg, e);
        }

        return userActivities;
    }

    @Override
    public void addUserActivities(String userId, List<Activity> activities) throws ServiceException {
        try {
            UserProfile userProfile = userRepository.fetchById(userId);
            UserActivities userActivities = new UserActivities(userProfile);
            userActivities.add(activities);

            userActivitiesRepository.save(userActivities);
        } catch (IOException e) {
            String errMsg = "Saving activities for user='" + userId + "' failure!";
            System.out.println(errMsg);
            throw new ServiceException(errMsg, e);
        }
    }

    private Path getBasePath() {
        return Paths.get("");
    }
}