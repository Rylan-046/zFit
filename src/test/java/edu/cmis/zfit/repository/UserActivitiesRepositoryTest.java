package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class UserActivitiesRepositoryTest {
    private UserActivitiesFileRepository userActivitiesFileRepository;
    private UserProfile userProfile;
    private UserActivities userActivities;

    @BeforeEach
    public void setup() throws IOException {
        System.out.println("**** BEGIN SETUP ****");
        System.out.println(getBasePath());

        userActivitiesFileRepository = new UserActivitiesFileRepository(getBasePath());

        userProfile = new UserProfile(
                "billy@gmail.com",
                LocalDate.parse("1998-02-01"),
                Gender.FEMALE,
                "Billy",
                "Jenkins"
        );

        userActivities = new UserActivities(userProfile);
        Instant currentTime = Instant.now();

        userActivities.add(
                new BurnActivity(
                        UUID.randomUUID().toString(),
                        250,
                        125,
                        65,
                        100,
                        624,
                        38,
                        BurnActivityType.RUNNING,
                        5200,
                        new DateRange(
                                currentTime,
                                currentTime.plus(20, ChronoUnit.MINUTES)
                        )
                )
        );

        userActivities.add(
                new BurnActivity(
                        UUID.randomUUID().toString(),
                        5,
                        75,
                        65,
                        95,
                        625,
                        38,
                        BurnActivityType.RESTING,
                        0,
                        new DateRange(
                                currentTime.plus(10, ChronoUnit.HOURS),
                                currentTime.plus(18, ChronoUnit.HOURS)
                        )
                )
        );

        userActivities.add(
                new ConsumptionActivity(
                        UUID.randomUUID().toString(),
                        1250,
                        75,
                        65,
                        95,
                        628,
                        38,
                        Instant.now(),
                        ConsumptionActivityType.EATING
                )
        );

        userActivitiesFileRepository.save(userActivities);
        System.out.println("**** END SETUP ****");
    }

    @Test
    public void createTest() throws IOException {
        UserProfile userProfile = new UserProfile(
                "lex@gmail.com",
                LocalDate.parse("2000-01-01"),
                Gender.MALE,
                "Lex",
                "Smith"
        );

        Instant currentTime = Instant.now();
        UserActivities userActivitiesExpected = new UserActivities(userProfile);
        userActivitiesExpected.add(
                new BurnActivity(
                        UUID.randomUUID().toString(),
                        225,
                        122,
                        70,
                        98,
                        120,
                        38,
                        BurnActivityType.RUNNING,
                        4800,
                        new DateRange(
                                currentTime,
                                currentTime.plus(25, ChronoUnit.MINUTES)
                        )
                )
        );

        userActivitiesExpected.add(
                new ConsumptionActivity(
                        UUID.randomUUID().toString(),
                        1100,
                        70,
                        70,
                        100,
                        120,
                        65,
                        Instant.now().plus(2, ChronoUnit.HOURS),
                        ConsumptionActivityType.EATING
                )
        );

        userActivitiesFileRepository.save(userActivitiesExpected);

        UserActivities userActivitiesActual = userActivitiesFileRepository.fetch(userActivitiesExpected.getUserProfile().getId());

        Assertions.assertEquals(userActivitiesExpected.toString(), userActivitiesActual.toString());
    }

    @Test
    public void updateTest() throws IOException {

        Instant currentTime = Instant.now();
        UserActivities userActivitiesNew = new UserActivities(userProfile);

        userActivitiesNew.add(
                new BurnActivity(
                        UUID.randomUUID().toString(),
                        225,
                        122,
                        70,
                        98,
                        120,
                        38,
                        BurnActivityType.RUNNING,
                        4800,
                        new DateRange(
                                currentTime,
                                currentTime.plus(25, ChronoUnit.MINUTES)
                        )
                )
        );

        UserActivities userActivitiesExpected = new UserActivities(userProfile);
        userActivitiesExpected.add(userActivities.getActivityList());
        userActivitiesExpected.add(userActivitiesNew.getActivityList());

        userActivitiesFileRepository.save(userActivitiesNew);

        UserActivities userActivitiesActual = userActivitiesFileRepository.fetch("billy@gmail.com");

        Assertions.assertEquals(userActivitiesExpected.toString(), userActivitiesActual.toString());
    }

    @Test
    public void deleteTest() throws IOException {
        userActivitiesFileRepository.delete("mandy@gmail.com");

        Assertions.assertThrows(
                IOException.class,
                () -> userActivitiesFileRepository.fetch("mandy@gmail.com"),
                "File doesn't exist."
        );
    }

    @Test
    public void fetchTest() throws IOException {
        UserActivities actualUserActivities = userActivitiesFileRepository.fetch(userActivities.getUserProfile().getId());
        Assertions.assertEquals(userActivities.toString(), actualUserActivities.toString());
    }

    @Test
    public void fetchFilterByTypeTest() throws IOException {
        UserActivities actualUserActivities = userActivitiesFileRepository.fetch(userActivities.getUserProfile().getId(), BurnActivityType.RUNNING);
        Assertions.assertEquals(1, actualUserActivities.getActivityList().size());
    }

    @AfterEach
    public void tearDown() throws IOException {
        userActivitiesFileRepository.delete("billy@gmail.com");
        userActivitiesFileRepository.delete("mandy@gmail.com");
        userActivitiesFileRepository.delete("lex@gmail.com");
    }

    private Path getBasePath() {
        String path = "src/test/resources/data";

        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        System.out.println(absolutePath);
        return Path.of(path);
    }
}
