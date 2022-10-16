package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.UserProfile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class UserFileRepository extends AbstractFileRepository implements UserRepository {
    private static final String FILE_SUFFIX = "profile.json";

    public UserFileRepository(Path basePath) {
        super(basePath);
    }

    @Override
    public void save(UserProfile userProfile) throws IOException {
        Path filePath = getFilePath(userProfile.getId(), FILE_SUFFIX);
        String json = getJsonMapper().writerWithDefaultPrettyPrinter().writeValueAsString(userProfile);

        System.out.println("Saving: " + json + " to " + filePath);

        delete(userProfile.getId());

        Files.writeString(filePath, json,
                StandardCharsets.UTF_8, StandardOpenOption.CREATE);
    }

    @Override
    public void delete(String userId) throws IOException {
        Path filePath = super.getFilePath(userId, FILE_SUFFIX);

        if (filePath.toFile().exists()) {
            Files.delete(filePath);
        }
    }

    @Override
    public UserProfile fetchById(String userId) throws IOException {
        Path filePath = (super.getFilePath(userId, FILE_SUFFIX));
//        System.out.println(Files.readString(filePath));

        return getJsonMapper().readValue(filePath.toFile(), UserProfile.class);
    }
}