package edu.cmis.zfit.service;

import edu.cmis.zfit.model.Activity;

import java.util.List;

public interface ActivityTrackerService {
    List<Activity> getUserActivities(String userId, boolean sortByDate) throws ServiceException;
    void addUserActivities(String userId, List<Activity> activityList) throws ServiceException;
}