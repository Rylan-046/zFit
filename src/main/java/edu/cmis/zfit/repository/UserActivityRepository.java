package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.Activity;
import edu.cmis.zfit.model.ActivityType;

import java.io.IOException;
import java.util.List;

public interface UserActivityRepository {

    void save(String userId, List<Activity> activityList) throws IOException;

    void delete(String userId) throws IOException;

    List<Activity> fetch(String userId) throws IOException;

    List<Activity> fetch(String userId, ActivityType activityType) throws IOException;
}