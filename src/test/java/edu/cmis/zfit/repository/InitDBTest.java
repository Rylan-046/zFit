package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.Gender;
import edu.cmis.zfit.model.UserProfile;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.*;
import java.time.Instant;

public class InitDBTest  {
    private DBConnectionProperties dbConnectionProperties = new DBConnectionProperties(
            "jdbc:h2:mem:zFit",
            null,
            null);

    @Test
    public void initDBTest() throws IOException {
        InitDBRepository.getInstance(dbConnectionProperties).init();

        try (Statement statement = createConnection().createStatement()) {
            statement.executeUpdate(
                    "INSERT INTO tblUserProfile(id, birthDate, gender, firstName, lastName) " +
                            "VALUES('509238', '" + Timestamp.from(Instant.now()) + "', 'FEMALE', 'Kinsley', 'Adams')"

            );

            try(ResultSet resultSet = statement.executeQuery("SELECT * FROM tblUserProfile")) {
                while(resultSet.next()) {
                    UserProfile userProfile = new UserProfile(
                            resultSet.getString("id"),
                            resultSet.getTimestamp("birthDate").toLocalDateTime().toLocalDate(),
                            Gender.valueOf(resultSet.getString("gender")),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName")
                            );

                    System.out.println(userProfile);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new IOException(ex);
        }
    }

    private Connection createConnection() {
        Connection connection = null;

        try {
            System.out.println("Connection to " + dbConnectionProperties.dbUrl());
            connection = DriverManager.getConnection(
                    dbConnectionProperties.dbUrl()
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return connection;
    }
}