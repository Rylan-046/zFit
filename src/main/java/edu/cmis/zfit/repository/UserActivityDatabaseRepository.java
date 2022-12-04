package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.Activity;
import edu.cmis.zfit.model.ActivityType;
import edu.cmis.zfit.model.DateRange;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserActivityDatabaseRepository extends AbstractDatabaseRepository implements UserActivityRepository {

    public UserActivityDatabaseRepository(DBConnectionProperties dbConnectionProperties) {
        super(dbConnectionProperties);
    }

    @Override
    public void save(String userId, List<Activity> activityList) throws IOException {
        try (Statement statement = super.createConnection().createStatement()) {

        } catch (SQLException ex) {
            throw new IOException(ex);
        }
    }

    @Override
    public void delete(String userId) throws IOException {

    }

    @Override
    public List<Activity> fetch(String userId) throws IOException {
        return null;
    }

    @Override
    public List<Activity> fetch(String userId, DateRange dateRange) throws IOException {
        return null;
    }

    @Override
    public List<Activity> fetch(String userId, ActivityType activityType) throws IOException {
        return null;
    }

    @Override
    public List<Activity> fetch(String userId, ActivityType activityType, DateRange dateRange) throws IOException {
        return null;
    }
}
