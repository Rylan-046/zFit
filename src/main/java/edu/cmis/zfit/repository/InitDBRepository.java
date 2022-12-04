package edu.cmis.zfit.repository;

import java.io.IOException;
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
        try {
            createUserProfileTable();
            createUserCredentialsTable();
            createActivityTable();
            createBurnActivityTable();
            createConsumptionActivityTable();
        } catch (SQLException ex) {
            throw new IOException(ex);
        }
    }

    private void createUserProfileTable() throws SQLException {
        try (Statement statement = super.createConnection().createStatement()) {
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

    private void createUserCredentialsTable() throws SQLException {
        try (Statement statement = super.createConnection().createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS tblUserCredentials (" +
                            "name VARCHAR PRIMARY KEY, " +
                            "password VARCHAR " +
                            ")"
            );
        }
    }

    private void createActivityTable() throws SQLException {
        try (Statement statement = super.createConnection().createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS tblUserProfile (" +
                            "id VARCHAR PRIMARY KEY, " +
                            "userProfileId VARCHAR, " +
                            "calories INTEGER, " +
                            "heartRateInBpm INTEGER, " +
                            "heartRateVariability INTEGER, " +
                            "oxygenSaturationLevelPercentage VARCHAR, " +
                            "weightInLbs DECFLOAT, " +
                            "heightInInches INTEGER " +
                            ")"
            );
        }
    }

    private void createBurnActivityTable() throws SQLException {
        try (Statement statement = super.createConnection().createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS tblUserProfile (" +
                            "id VARCHAR PRIMARY KEY, " +
                            "calories INTEGER, " +
                            "heartRateInBpm INTEGER, " +
                            "heartRateVariability INTEGER, " +
                            "oxygenSaturationLevelPercentage VARCHAR, " +
                            "weightInLbs DECFLOAT, " +
                            "heightInInches INTEGER, " +
                            "burnActivityType VARCHAR, " +
                            "steps INTEGER, " +
                            "beginDate DATETIME, " +
                            "endDate DATETIME " +
                            ")"
            );
        }
    }

    private void createConsumptionActivityTable() throws SQLException {
        try (Statement statement = super.createConnection().createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS tblUserProfile (" +
                            "id VARCHAR PRIMARY KEY, " +
                            "calories INTEGER, " +
                            "heartRateInBpm INTEGER, " +
                            "heartRateVariability INTEGER, " +
                            "oxygenSaturationLevelPercentage VARCHAR, " +
                            "weightInLbs DECFLOAT, " +
                            "heightInInches INTEGER, " +
                            "type VARCHAR " +
                            ")"
            );
        }
    }
}
