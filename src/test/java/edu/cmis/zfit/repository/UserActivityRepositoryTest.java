package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserActivityRepositoryTest {
    private UserActivityRepository ActivityFileRepository;
    private String userId;
    private final List<Activity> activityList = new ArrayList<>();

    @BeforeEach
    public void setup() throws IOException {
        System.out.println("**** BEGIN SETUP ****");
        System.out.println(getBasePath());

        ActivityFileRepository = new UserActivityFileRepository(getBasePath());

        userId = "billy@gmail.com";

        Instant currentTime = Instant.now();

        activityList.add(
                new BurnActivity(
                        UUID.randomUUID().toString(),
                        250,
                        125,
                        65,
                        new BloodPressure(74, 124),
                        624,
                        38,
                        72,
                        BurnActivityType.RUNNING,
                        5200,
                        new DateRange(
                                currentTime,
                                currentTime.plus(20, ChronoUnit.MINUTES)
                        )
                )
        );

        activityList.add(
                new BurnActivity(
                        UUID.randomUUID().toString(),
                        5,
                        75,
                        65,
                        new BloodPressure(55, 134),
                        625,
                        38,
                         68,
                        BurnActivityType.RESTING,
                        0,
                        new DateRange(
                                currentTime.plus(10, ChronoUnit.HOURS),
                                currentTime.plus(18, ChronoUnit.HOURS)
                        )
                )
        );

        activityList.add(
                new ConsumptionActivity(
                        UUID.randomUUID().toString(),
                        1250,
                        75,
                        65,
                        new BloodPressure(58, 110),
                        628,
                        38,
                        71,
                        Instant.now(),
                        ConsumptionActivityType.EATING
                )
        );

        ActivityFileRepository.save(userId, activityList);
        System.out.println("**** END SETUP ****");
    }

    @Test
    public void createTest() throws IOException {
        userId = "lex@gmail.com";

        Instant currentTime = Instant.now();
        List<Activity> expectedActivityList = new ArrayList<>();
        expectedActivityList.add(
                new BurnActivity(
                        UUID.randomUUID().toString(),
                        225,
                        122,
                        70,
                        new BloodPressure(66, 111),
                        120,
                        38,
                        73,
                        BurnActivityType.RUNNING,
                        4800,
                        new DateRange(
                                currentTime,
                                currentTime.plus(25, ChronoUnit.MINUTES)
                        )
                )
        );

        expectedActivityList.add(
                new ConsumptionActivity(
                        UUID.randomUUID().toString(),
                        1100,
                        70,
                        70,
                        new BloodPressure(75, 125),
                        120,
                        65,
                        58,
                        Instant.now().plus(2, ChronoUnit.HOURS),
                        ConsumptionActivityType.EATING
                )
        );

        ActivityFileRepository.save(userId, expectedActivityList);

        List<Activity> actualActivityList = ActivityFileRepository.fetch(userId);

        Assertions.assertEquals(expectedActivityList.toString(), actualActivityList.toString());
    }

    @Test
    public void updateTest() throws IOException {

        Instant currentTime = Instant.now();
        List<Activity> newActivityList = new ArrayList<>();

        newActivityList.add(
                new BurnActivity(
                        UUID.randomUUID().toString(),
                        225,
                        122,
                        70,
                        new BloodPressure(76, 155),
                        120,
                        38,
                        64,
                        BurnActivityType.RUNNING,
                        4800,
                        new DateRange(
                                currentTime,
                                currentTime.plus(25, ChronoUnit.MINUTES)
                        )
                )
        );

        List<Activity> expectedActivityList = new ArrayList<>();
        expectedActivityList.addAll(activityList);
        expectedActivityList.addAll(newActivityList);

        ActivityFileRepository.save(userId, newActivityList);

        List<Activity> actualActivityList = ActivityFileRepository.fetch(userId);

        Assertions.assertEquals(expectedActivityList.toString(), actualActivityList.toString());
    }

    @Test
    public void deleteTest() throws IOException {
        ActivityFileRepository.delete("mandy@gmail.com");

        Assertions.assertThrows(
                IOException.class,
                () -> ActivityFileRepository.fetch("mandy@gmail.com"),
                "File doesn't exist."
        );
    }

    @Test
    public void fetchTest() throws IOException {
        List<Activity> actualActivityList = ActivityFileRepository.fetch(userId);
        Assertions.assertEquals(activityList.toString(), actualActivityList.toString());
    }

    @Test
    public void fetchFilterByTypeTest() throws IOException {
        List<Activity> actualActivityList = ActivityFileRepository.fetch(userId, BurnActivityType.RUNNING);
        Assertions.assertEquals(1, actualActivityList.size());
    }

    @AfterEach
    public void tearDown() throws IOException {
        ActivityFileRepository.delete("billy@gmail.com");
        ActivityFileRepository.delete("mandy@gmail.com");
        ActivityFileRepository.delete("lex@gmail.com");
    }

    private Path getBasePath() {
        String path = "src/test/resources/data";

        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        System.out.println(absolutePath);
        return Path.of(path);
    }
}
