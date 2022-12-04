package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.UserProfile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class UserProfileFileRepository extends AbstractFileRepository implements UserProfileRepository {
    private static final String PROFILE_FILE_SUFFIX = "profile.json";
    private static final String CREDENTIALS_FILE_SUFFIX = "credentials.json";


    public UserProfileFileRepository(Path basePath) {
        super(basePath);
    }

    @Override
    public void save(UserProfile userProfile) throws IOException {
        Path filePath = getFilePath(userProfile.id(), PROFILE_FILE_SUFFIX);
        String json = getJsonMapper().writerWithDefaultPrettyPrinter().writeValueAsString(userProfile);

        System.out.println("Saving: " + json + " to " + filePath);

        delete(userProfile.id());

        Files.writeString(filePath, json,
                StandardCharsets.UTF_8, StandardOpenOption.CREATE);
    }

    @Override
    public void saveCredentials(String userId, String passwd) throws IOException {
        Path filePath = (super.getFilePath("user", CREDENTIALS_FILE_SUFFIX));

        // Load existing credentials from json file to map
        Map<String, String> credentialsMap = fetchUserCredentialsList();

        // Add/Update map with user credentials
        credentialsMap.put(userId, passwd);

        // Convert map to json string
        String json = getJsonMapper().writerWithDefaultPrettyPrinter().writeValueAsString(credentialsMap);

        // Delete existing user credentials file
        if (filePath.toFile().exists()) {
            Files.delete(filePath);
        }

        // Write new credentials file
        Files.writeString(filePath, json,
                StandardCharsets.UTF_8, StandardOpenOption.CREATE);
    }

    @Override
    public void delete(String userId) throws IOException {
        Path filePath = super.getFilePath(userId, PROFILE_FILE_SUFFIX);

        if (filePath.toFile().exists()) {
            Files.delete(filePath);
        }
    }

    @Override
    public UserProfile fetchById(String userId) throws IOException {

        Path filePath = (super.getFilePath(userId, PROFILE_FILE_SUFFIX));

        try {
            return getJsonMapper().readValue(filePath.toFile(), UserProfile.class);
        } catch (FileNotFoundException e) {
            return null;
        }
    }


    @Override
    public Map<String, String> fetchUserCredentialsList() throws IOException {
        Path filePath = (super.getFilePath("user", CREDENTIALS_FILE_SUFFIX));

        if(!filePath.toFile().exists()) {
            createEmptyCredentialFile();
        }

        return getJsonMapper().readValue(filePath.toFile(), HashMap.class);
    }

    private void createEmptyCredentialFile() throws IOException {
        Path filePath = (super.getFilePath("user", CREDENTIALS_FILE_SUFFIX));

        Map<String, String> credentialMap = new HashMap<>();
        String json = getJsonMapper().writerWithDefaultPrettyPrinter().writeValueAsString(credentialMap);

        Files.writeString(filePath, json,
                StandardCharsets.UTF_8, StandardOpenOption.CREATE);
    }
}