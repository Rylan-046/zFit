package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class UserActivityDatabaseRepository extends AbstractDatabaseRepository implements UserActivityRepository {

    public UserActivityDatabaseRepository(DBConnectionProperties dbConnectionProperties) {
        super(dbConnectionProperties);
    }

    @Override
    public void save(String userId, List<Activity> activityList) throws IOException {
        String sql = "MERGE INTO tblActivity" +
                "(id, userId, calories, heartRateInBpm, heartRateVariability, systolic, diastolic, oxygenSaturationLevelPercentage, weightInLbs, heightInInches) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        Connection connection = super.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Activity activity : activityList) {
                statement.setString(1, activity.id());
                statement.setString(2, userId);
                statement.setInt(3, activity.calories());
                statement.setInt(4, activity.heartRateInBpm());
                statement.setInt(5, activity.heartRateVariability());
                statement.setInt(6, activity.bloodPressure().systolic());
                statement.setInt(7, activity.bloodPressure().diastolic());
                statement.setInt(8, activity.oxygenSaturationLevelPercentage());
                statement.setFloat(9, activity.weightInLbs());
                statement.setInt(10, activity.heightInInches());

                statement.addBatch();
            }

            statement.executeBatch();

        } catch (SQLException ex) {
            throw new IOException(ex);
        }

        saveBurnActivityTable(connection, activityList);
        saveConsumptionActivityTable(connection, activityList);

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String userId) throws IOException {
        try {
            /*deleteActivityFromBurnActivityTable(userId);
            deleteActivityFromConsumptionActivityTable(userId);*/

            String sql = "DELETE FROM tblActivity WHERE userId = ?";

            try (PreparedStatement statement = super.createConnection().prepareStatement(sql)) {
                statement.setString(1, userId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new IOException(ex);
        }
    }

    @Override
    public List<Activity> fetch(String userId) throws IOException {
        String sql =
                "SELECT * " +
                "FROM tblActivity " +
                "LEFT JOIN tblBurnActivity ON tblActivity.id = tblBurnActivity.id " +
                "LEFT JOIN tblConsumptionActivity ON tblActivity.id = tblConsumptionActivity.id " +
                "WHERE userId = ?";


        return fetch(sql, userId, null);
    }

    @Override
    public List<Activity> fetch(String userId, DateRange dateRange) throws IOException {
        String sql =
                "SELECT * " +
                        "FROM tblActivity " +
                        "LEFT JOIN tblBurnActivity ON tblActivity.id = tblBurnActivity.id " +
                        "LEFT JOIN tblConsumptionActivity ON tblActivity.id = tblConsumptionActivity.id " +
                        "WHERE userId = ? AND " +
                        "(tblConsumptionActivity.endDate = null AND tblBurnActivity.endDate >= ? AND tblBurnActivity.endDate < ?" +
                            "OR tblBurnActivity.endDate = null AND tblBurnActivity.endDate >= ? AND tblBurnActivity.endDate < ?)";

        return fetch(sql, userId, dateRange);
    }

    @Override
    public List<Activity> fetch(String userId, ActivityType activityType) throws IOException {
        String joinTbl = activityType instanceof BurnActivityType ? "tblBurnActivity" : "tblConsumptionActivity";
        String sql =
                "SELECT * " +
                "FROM tblActivity " +
                "INNER JOIN " + joinTbl + " ON tblActivity.id = " + joinTbl + ".id " +
                "WHERE userId = ? AND " + joinTbl + ".type = ?";

        System.out.println(sql);

        return fetch(sql, activityType, userId, null);
    }

    @Override
    public List<Activity> fetch(String userId, ActivityType activityType, DateRange dateRange) throws IOException {
        String joinTbl = activityType instanceof BurnActivityType ? "tblBurnActivity" : "tblConsumptionActivity";
        String sql =
                "SELECT * " +
                "FROM tblActivity " +
                "INNER JOIN " + joinTbl + " ON tblActivity.id = " + joinTbl + ".id " +
                "WHERE userId = ? AND " + joinTbl + ".type = ? AND endDate >= ? AND endDate < ?";

        return fetch(sql, activityType, userId, dateRange);
    }

    public void saveBurnActivityTable(Connection connection, List<Activity> activityList) throws IOException {
        String sql = "INSERT INTO tblBurnActivity" +
                "(id, type, steps, beginDate, endDate) " +
                "VALUES (?, ?, ?, ?, ?) ";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Activity activity : activityList) {
                if (activity.activityType() instanceof BurnActivityType) {
                    BurnActivity burnActivity = (BurnActivity) activity;
                    statement.setString(1, activity.id());
                    statement.setString(2, burnActivity.activityType().name());
                    statement.setInt(3, burnActivity.steps());
                    statement.setDate(4, Date.valueOf(LocalDate.ofInstant(
                            burnActivity.getDuration().begin(), ZoneId.systemDefault()
                    )));
                    statement.setDate(5, Date.valueOf(LocalDate.ofInstant(
                            burnActivity.getDuration().end(), ZoneId.systemDefault()
                    )));

                    statement.addBatch();
                }
            }

            statement.executeBatch();

        } catch (SQLException ex) {
            throw new IOException(ex);
        }
    }

    public void saveConsumptionActivityTable(Connection connection, List<Activity> activityList) throws IOException {
        String sql = "INSERT INTO tblConsumptionActivity" +
                "(id, type, endDate) " +
                "VALUES (?, ?, ?) ";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Activity activity : activityList) {
                if (activity.activityType() instanceof ConsumptionActivityType) {
                    ConsumptionActivity consumptionActivity = (ConsumptionActivity) activity;
                    statement.setString(1, activity.id());
                    statement.setString(2, consumptionActivity.activityType().name());
                    statement.setDate(3, Date.valueOf(LocalDate.ofInstant(
                            activity.date(), ZoneId.systemDefault()
                    )));

                    statement.addBatch();
                }
            }

            statement.executeBatch();

        } catch (SQLException ex) {
            throw new IOException(ex);
        }
    }

    private void deleteActivityFromBurnActivityTable(String userId) throws SQLException {
        String sql = "DELETE FROM tblBurnActivity WHERE userId = ?";

        try (PreparedStatement statement = super.createConnection().prepareStatement(sql)) {
            statement.setString(1, userId);

        }
    }

    private void deleteActivityFromConsumptionActivityTable(String userId) throws SQLException {
        String sql = "DELETE FROM tblConsumptionActivity WHERE userId = ?";

        try (PreparedStatement statement = super.createConnection().prepareStatement(sql)) {
            statement.setString(1, userId);
        }
    }

    private List<Activity> fetch(String sql, ActivityType activityType, String userId, DateRange dateRange) throws IOException {
        List<Activity> activityList = new ArrayList<>();

        try (PreparedStatement statement = super.createConnection().prepareStatement(sql)) {
            statement.setString(1, userId);
            statement.setString(2, activityType.name());
            if (dateRange != null) {
                statement.setDate(3, Date.valueOf(LocalDate.ofInstant(dateRange.begin(), ZoneId.systemDefault())));
                statement.setDate(4, Date.valueOf(LocalDate.ofInstant(dateRange.end(), ZoneId.systemDefault())));
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.println("------------------ Executing query");
                while (resultSet.next()) {
                    activityList.add(activityType instanceof BurnActivityType ?
                            toBurnActivity(resultSet):
                            toConsumptionActivity(resultSet));
                }
            }
        } catch (SQLException ex) {
            throw new IOException(ex);
        }

        return activityList;
    }

    private List<Activity> fetch(String sql, String userId, DateRange dateRange) throws IOException {
        List<Activity> activityList = new ArrayList<>();

        Connection connection = super.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userId);
            if (dateRange != null) {
                statement.setDate(2, Date.valueOf(LocalDate.ofInstant(dateRange.begin(), ZoneId.systemDefault())));
                statement.setDate(3, Date.valueOf(LocalDate.ofInstant(dateRange.end(), ZoneId.systemDefault())));
                statement.setDate(4, Date.valueOf(LocalDate.ofInstant(dateRange.begin(), ZoneId.systemDefault())));
                statement.setDate(5, Date.valueOf(LocalDate.ofInstant(dateRange.end(), ZoneId.systemDefault())));
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.println("------------------ Executing query");
                while (resultSet.next()) {
                    System.out.println("calories: " + resultSet.getInt("calories"));
                    if (resultSet.getString("tblBurnActivity.type") != null) {
                        activityList.add(toBurnActivity(resultSet));
                    } else if (resultSet.getString("tblConsumptionActivity.type") != null){
                        activityList.add(toConsumptionActivity(resultSet));
                    }
                }
            }

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new IOException(ex);
        }

        return activityList;
    }

    private BurnActivity toBurnActivity(ResultSet resultSet) throws SQLException {
        System.out.println("to burn activity");

        return new BurnActivity(
                resultSet.getString("id"),
                resultSet.getString("userId"),
                resultSet.getInt("calories"),
                resultSet.getInt("heartRateInBpm"),
                resultSet.getInt("heartRateVariability"),
                new BloodPressure(
                        resultSet.getInt("systolic"),
                        resultSet.getInt("diastolic")),
                resultSet.getInt("oxygenSaturationLevelPercentage"),
                resultSet.getFloat("weightInLbs"),
                resultSet.getInt("heightInInches"),
                BurnActivityType.valueOf(resultSet.getString("tblBurnActivity.type")),

                resultSet.getInt("steps"),
                new DateRange(
                        resultSet.getTimestamp("beginDate").toInstant(),
                        resultSet.getTimestamp("tblBurnActivity.endDate").toInstant()
                )
        );
    }

    private ConsumptionActivity toConsumptionActivity(ResultSet resultSet) throws SQLException {
        System.out.println("to consumption activity");

        return new ConsumptionActivity(
                resultSet.getString("id"),
                resultSet.getString("userId"),
                resultSet.getInt("calories"),
                resultSet.getInt("heartRateInBpm"),
                resultSet.getInt("heartRateVariability"),
                new BloodPressure(
                        resultSet.getInt("systolic"),
                        resultSet.getInt("diastolic")),
                resultSet.getInt("oxygenSaturationLevelPercentage"),
                resultSet.getFloat("weightInLbs"),
                resultSet.getInt("heightInInches"),
                resultSet.getTimestamp("tblConsumptionActivity.endDate").toInstant(),
                ConsumptionActivityType.valueOf(resultSet.getString("tblConsumptionActivity.type"))
        );
    }
}
