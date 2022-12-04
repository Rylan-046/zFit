package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.Gender;
import edu.cmis.zfit.model.UserProfile;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserProfileDatabaseRepository extends AbstractDatabaseRepository implements UserProfileRepository {
    public UserProfileDatabaseRepository(DBConnectionProperties dbConnectionProperties) {
        super(dbConnectionProperties);
    }

    @Override
    public void save(UserProfile userProfile) throws IOException {
        boolean profileExists = fetchById(userProfile.id()) != null;
        String sql;

        if (profileExists) {
            sql = "UPDATE tblUserProfile" +
                    "SET id = ?, birthDate = ?, gender = ?, firstName = ?, lastName = ?";
        } else {
            sql = "INSERT INTO tblUserProfile" +
                    "(id, birthDate, gender, firstName, lastName) " +
                    "VALUES (?, ?, ?, ?, ?) ";

        }

        try (PreparedStatement statement = super.createConnection().prepareStatement(sql)) {
            statement.setString(1, userProfile.id());
            statement.setDate(2, Date.valueOf(userProfile.birthDate()));
            statement.setString(3, userProfile.gender().name());
            statement.setString(4, userProfile.firstName());
            statement.setString(5, userProfile.lastName());

        } catch (SQLException ex) {
            throw new IOException(ex);
        }
    }

    @Override
    public void delete(String userId) throws IOException {
        String sql = "DELETE FROM tblUserProfile WHERE id = ?";

        try (PreparedStatement statement = super.createConnection().prepareStatement(sql)) {
            statement.setString(1, userId);

        } catch (SQLException ex) {
            throw new IOException(ex);
        }
    }

    @Override
    public UserProfile fetchById(String userId) throws IOException {
        UserProfile userProfile = null;
        String sql = "SELECT * FROM tblUserProfile WHERE id = ?";

        try (PreparedStatement statement = super.createConnection().prepareStatement(sql)) {
            statement.setString(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    userProfile = new UserProfile(
                            resultSet.getString("id"),
                            resultSet.getTimestamp("birthDate").toLocalDateTime().toLocalDate(),
                            Gender.valueOf(resultSet.getString("gender")),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName")
                    );
                }
            }
        } catch (SQLException ex) {
            throw new IOException(ex);
        }

        return userProfile;
    }

    @Override
    public void saveCredentials(String userId, String passwd) throws IOException {
        boolean credentialsExist = fetchUserCredentialsList().get(userId) != null;
        String sql;

        if (credentialsExist) {
            sql = "UPDATE tblUserCredentials" +
                    "SET name = ?, passwd = ?";
        } else {
            sql = "INSERT INTO tblUserCredentials" +
                    "(name, passwd) " +
                    "VALUES (?, ?) ";

        }

        try (PreparedStatement statement = super.createConnection().prepareStatement(sql)) {
            statement.setString(1, userId);
            statement.setString(2, passwd);

            System.out.println("**** " + userId);
            System.out.println("**** " + passwd);

        } catch (SQLException ex) {
            throw new IOException(ex);
        }
    }

    @Override
    public Map<String, String> fetchUserCredentialsList() throws IOException {
        System.out.println("HERERE");
        String sql = "SELECT * FROM tblUserCredentials";
        Map<String, String> userCredentialsMap = new HashMap<>();

        try (Statement statement = super.createConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    System.out.println("HAS " + resultSet.getString("name") + " " + resultSet.getString("passwd"));
                    userCredentialsMap.put(resultSet.getString("name"), resultSet.getString("passwd"));
                }
            }
        } catch (SQLException ex) {
            throw new IOException(ex);
        }

        return userCredentialsMap;
    }
}
