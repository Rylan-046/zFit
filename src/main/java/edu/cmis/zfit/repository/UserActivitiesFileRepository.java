package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.Activity;
import edu.cmis.zfit.model.ActivityType;
import edu.cmis.zfit.model.UserActivities;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class UserActivitiesFileRepository extends AbstractFileRepository implements UserActivitiesRepository {
    private static final String FILE_SUFFIX = "activities.json";

    protected UserActivitiesFileRepository(Path basePath) {
        super(basePath);
    }

    @Override
    public void save(UserActivities newUserActivities) throws IOException {
        UserActivities userActivities;

        if (Files.exists(super.getFilePath(newUserActivities.getUserProfile().getId(), FILE_SUFFIX))) {
            userActivities = fetch(newUserActivities.getUserProfile().getId());
            userActivities.add(newUserActivities.getActivityList());
            delete(userActivities.getUserProfile().getId());
        } else {
            userActivities = newUserActivities;
        }

        Path filePath = getFilePath(newUserActivities.getUserProfile().getId(), FILE_SUFFIX);

        String json = getJsonMapper().writerWithDefaultPrettyPrinter().writeValueAsString(userActivities);

        System.out.println("Saving: " + json + " to " + filePath);

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
    public UserActivities fetch(String userId) throws IOException {
        Path filePath = (super.getFilePath(userId, FILE_SUFFIX));

        return getJsonMapper().readValue(filePath.toFile(), UserActivities.class);
    }

    @Override
    public UserActivities fetch(String userId, ActivityType activityType) throws IOException {
        Path filePath = (super.getFilePath(userId, FILE_SUFFIX));

        UserActivities userActivities = getJsonMapper().readValue(filePath.toFile(), UserActivities.class);
        UserActivities filteredUserActivities = new UserActivities(userActivities.getUserProfile());

        for(Activity activity : userActivities.getActivityList()) {
            if(activity.getType() == activityType) {
                filteredUserActivities.add(activity);
            }
        }

        return filteredUserActivities;
    }
}