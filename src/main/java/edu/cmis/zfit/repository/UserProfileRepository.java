package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.UserProfile;

import java.io.IOException;
import java.util.Map;

public interface UserProfileRepository {
    void save(UserProfile userProfile) throws IOException;

    void delete(String userId) throws IOException;

    UserProfile fetchById(String userId) throws IOException;

    void saveCredentials(String userId, String passwd) throws IOException;

    Map<String, String> fetchUserCredentialsList() throws IOException;
}