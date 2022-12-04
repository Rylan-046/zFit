package edu.cmis.zfit.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDBRepository extends AbstractDatabaseRepository {

    protected InitDBRepository(DBConnectionProperties dbConnectionProperties) {
        super(dbConnectionProperties);
    }

    public static InitDBRepository getInstance(DBConnectionProperties dbConnectionProperties) {
        return new InitDBRepository(dbConnectionProperties);
    }

    // Executes DDL(Data Definition Language) statements to create database tables to initialize database
    public void init() throws IOException {
        Connection connection = super.createConnection();
        try {
            createUserProfileTable(connection);
            createUserCredentialsTable(connection);
            createActivityTable(connection);
            createBurnActivityTable(connection);
            createConsumptionActivityTable(connection);
        } catch (SQLException ex) {
            throw new IOException(ex);
        }
    }

    private void createUserProfileTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS tblUserProfile (" +
                            "id VARCHAR PRIMARY KEY, " +
                            "birthDate TIMESTAMP, " +
                            "gender VARCHAR, " +
                            "firstName VARCHAR , " +
                            "lastName VARCHAR " +
                            ")"
            );
        }
    }

    private void createUserCredentialsTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS tblUserCredentials (" +
                            "name VARCHAR PRIMARY KEY, " +
                            "passwd VARCHAR " +
                            ")"
            );
        }
    }

    private void createActivityTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS tblActivity (" +
                            "id VARCHAR PRIMARY KEY, " +
                            "userId VARCHAR, " +
                            "calories INTEGER, " +
                            "heartRateInBpm INTEGER, " +
                            "heartRateVariability INTEGER, " +
                            "systolic INTEGER, " +
                            "diastolic INTEGER, " +
                            "oxygenSaturationLevelPercentage INTEGER, " +
                            "weightInLbs DECFLOAT, " +
                            "heightInInches INTEGER " +
                            ")"
            );
        }
    }

    private void createBurnActivityTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS tblBurnActivity (" +
                            "id VARCHAR PRIMARY KEY, " +
                            "type VARCHAR, " +
                            "steps INTEGER, " +
                            "beginDate DATETIME, " +
                            "endDate DATETIME, " +
                            "FOREIGN KEY (id) REFERENCES tblActivity(id) " +
                            ")"
            );
        }
    }

    private void createConsumptionActivityTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS tblConsumptionActivity (" +
                            "id VARCHAR PRIMARY KEY, " +
                            "endDate DATETIME, " +
                            "type VARCHAR, " +
                            "FOREIGN KEY (id) REFERENCES tblActivity(id) " +
                            ")"
            );
        }
    }
}
