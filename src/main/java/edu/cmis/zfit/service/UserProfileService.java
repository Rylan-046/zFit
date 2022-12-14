package edu.cmis.zfit.service;

import java.io.IOException;

public interface UserProfileService {
    boolean isUserIdAvailable(String userId);

    void saveCredentials(String userId, String passwd) throws IOException;

    boolean isValidCredentials(String userId, String passwd);
}