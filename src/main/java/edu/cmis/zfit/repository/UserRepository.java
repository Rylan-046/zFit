package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.UserProfile;

import java.io.IOException;

public interface UserRepository {

    void save(UserProfile userProfile) throws IOException;

    void delete(String userId) throws IOException;

    UserProfile fetchById(String userId) throws IOException;
}