package edu.cmis.zfit.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.cmis.zfit.model.Activity;
import edu.cmis.zfit.model.ActivityType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class UserActivityFileRepository extends AbstractFileRepository implements UserActivityRepository {
    private static final String FILE_SUFFIX = "activities.json";

    public UserActivityFileRepository(Path basePath) {
        super(basePath);
    }

    @Override
    public void save(String userId, List<Activity> activityList) throws IOException {
        List<Activity> existingActivityList;

        if (Files.exists(super.getFilePath(userId, FILE_SUFFIX))) {
            existingActivityList = fetch(userId);
            existingActivityList.addAll(activityList);
            delete(userId);
        } else {
            existingActivityList = activityList;
        }

        Path filePath = getFilePath(userId, FILE_SUFFIX);

        String json = getJsonMapper().writerWithDefaultPrettyPrinter().writeValueAsString(existingActivityList);

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
    public List<Activity> fetch(String userId) throws IOException {
        Path filePath = (super.getFilePath(userId, FILE_SUFFIX));

        return getJsonMapper().readValue(filePath.toFile(), new TypeReference<List<Activity>>(){});
    }

    @Override
    public List<Activity> fetch(String userId, ActivityType activityType) throws IOException {
        List<Activity> activityList = fetch(userId);
        List<Activity> filteredActivityList = new ArrayList<>();

        for(Activity activity : activityList) {
            if(activity.activityType() == activityType) {
                filteredActivityList.add(activity);
            }
        }

        return filteredActivityList;
    }
}