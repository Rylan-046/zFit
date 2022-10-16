package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.Activity;
import edu.cmis.zfit.model.DateRange;
import edu.cmis.zfit.model.UserActivities;

import java.io.IOException;

public interface UserActivitiesRepository {

    void save(UserActivities activities) throws IOException;

    void delete(String userId) throws IOException;

    UserActivities fetch(String userId) throws IOException;

    UserActivities fetch(String userId, DateRange dateRange) throws IOException;

    UserActivities fetch(String userId, DateRange dateRange, Activity activityType) throws IOException;
}