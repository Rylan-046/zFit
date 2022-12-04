package edu.cmis.zfit.service;

import edu.cmis.zfit.repository.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ServiceFactory {
    private UserActivityRepository userActivityRepository;
    private UserProfileRepository userProfileRepository;
    private UserProfileService userProfileService;
    private ActivityTrackerService activityTrackerService;
    private FitnessAnalysisService fitnessAnalysisService;

    public static ServiceFactory getInstance() throws ServiceException {
        return ServiceFactory.getInstance(PersistenceType.FILE);
    }

    public static ServiceFactory getInstance(PersistenceType persistenceType) throws ServiceException {
        if(persistenceType == PersistenceType.DATABASE) {
            try {
                InitDBRepository.getInstance(new DBConnectionProperties()).init();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new ServiceException(ex);
            }
        }
        return new ServiceFactory(persistenceType);
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

    private ServiceFactory(PersistenceType persistenceType) throws ServiceException {
        try {
            if(persistenceType == PersistenceType.DATABASE) {
                DBConnectionProperties dbConnectionProperties = new DBConnectionProperties();
                userActivityRepository = new UserActivityDatabaseRepository(dbConnectionProperties);
                userProfileRepository = new UserProfileDatabaseRepository(dbConnectionProperties);
            } else {
                userActivityRepository = new UserActivityFileRepository(getBasePath());
                userProfileRepository = new UserProfileFileRepository(getBasePath());
            }

            userProfileService = new UserProfileDefaultService(userProfileRepository);
            activityTrackerService = new ActivityTrackerServiceImpl(userActivityRepository, userProfileRepository);
            fitnessAnalysisService = new DefaultFitnessAnalysisService(userActivityRepository, userProfileRepository);
        } catch (IOException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    private Path getBasePath() {
        String path = "";

        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        System.out.println(absolutePath);
        return Path.of(path);
    }

    public enum PersistenceType {
        DATABASE,
        FILE;
    }
}
