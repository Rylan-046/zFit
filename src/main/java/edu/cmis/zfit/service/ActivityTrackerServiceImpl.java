package edu.cmis.zfit.service;

import edu.cmis.zfit.model.Activity;
import edu.cmis.zfit.repository.ActivityFileRepository;
import edu.cmis.zfit.repository.ActivityRepository;
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
    private final ActivityRepository activityRepository = new ActivityFileRepository(getBasePath());
    private final UserRepository userRepository = new UserFileRepository(getBasePath());
    private final ActivitySorter activitySorter = new ActivityQuickSorter();

    @Override
    public List<Activity> getUserActivities(String userId, boolean sortByDate) throws ServiceException {
        List<Activity> activityList;

        try {
            List<Activity> tmpActivityList = activityRepository.fetch(userId);

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
        try {
            activityRepository.save(userId, activityList );
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