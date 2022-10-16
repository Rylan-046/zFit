package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.ActivityType;
import edu.cmis.zfit.model.UserActivities;

import java.io.IOException;

public interface UserActivitiesRepository {

    void save(UserActivities activities) throws IOException;

    void delete(String userId) throws IOException;

    UserActivities fetch(String userId) throws IOException;

    UserActivities fetch(String userId, ActivityType activityType) throws IOException;
}