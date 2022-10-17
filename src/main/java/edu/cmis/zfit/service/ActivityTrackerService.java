package edu.cmis.zfit.service;

import edu.cmis.zfit.model.Activity;
import edu.cmis.zfit.model.UserActivities;

import java.util.List;

public interface ActivityTrackerService {
    UserActivities getUserActivities(String userId, boolean sortByDate) throws ServiceException;
    void addUserActivities(String userId, List<Activity> activities) throws ServiceException;
}