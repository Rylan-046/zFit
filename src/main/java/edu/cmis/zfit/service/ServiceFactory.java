package edu.cmis.zfit.service;

import edu.cmis.zfit.repository.UserActivityFileRepository;
import edu.cmis.zfit.repository.UserActivityRepository;
import edu.cmis.zfit.repository.UserProfileFileRepository;
import edu.cmis.zfit.repository.UserProfileRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ServiceFactory {
    private UserActivityRepository userActivityRepository;
    private UserProfileRepository userProfileRepository;
    private UserProfileService userProfileService;
    private ActivityTrackerService activityTrackerService;
    private FitnessAnalysisService fitnessAnalysisService;

    public ServiceFactory() throws ServiceException {
        try {
            userActivityRepository = new UserActivityFileRepository(getBasePath());
            userProfileRepository = new UserProfileFileRepository(getBasePath());
            userProfileService = new UserProfileDefaultService(userProfileRepository);
            activityTrackerService = new ActivityTrackerServiceImpl(userActivityRepository, userProfileRepository);
            fitnessAnalysisService = new DefaultFitnessAnalysisService(userActivityRepository, userProfileRepository);
        } catch (IOException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    public static ServiceFactory getInstance() throws ServiceException {
        return new ServiceFactory();
    }

    public UserProfileService getUserProfileService() {
        return userProfileService;
    }

    public FitnessAnalysisService fitnessAnalysisService() {
        return fitnessAnalysisService;
    }

    public ActivityTrackerService getActivityTrackerService() {
        return activityTrackerService;
    }

    private Path getBasePath() {
        String path = "";

        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        System.out.println(absolutePath);
        return Path.of(path);
    }

}
