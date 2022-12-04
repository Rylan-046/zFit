package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserActivityRepositoryTest {
    private UserActivityRepository userActivityRepository;
    private String userId;
    private final List<Activity> activityList = new ArrayList<>();

    private DBConnectionProperties dbConnectionProperties = new DBConnectionProperties(
            "jdbc:h2:mem:zFit;DB_CLOSE_DELAY=-1",
            null,
            null);

    @BeforeEach
    public void setup() throws IOException {
        InitDBRepository.getInstance(dbConnectionProperties).init();

        System.out.println("**** BEGIN SETUP ****");
        System.out.println(getBasePath());

//        userActivityRepository = new UserActivityFileRepository(getBasePath());
        userActivityRepository = new UserActivityDatabaseRepository(dbConnectionProperties);

        userId = "billy@gmail.com";

        Instant currentTime = Instant.now();

        activityList.add(
                new BurnActivity(
                        UUID.randomUUID().toString(),
                        userId,
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
                        userId,
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
                                currentTime.minus(42, ChronoUnit.HOURS),
                                currentTime.minus(36, ChronoUnit.HOURS)
                        )
                )
        );

        activityList.add(
                new ConsumptionActivity(
                        UUID.randomUUID().toString(),
                        userId,
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

        userActivityRepository.save(userId, activityList);
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
                        userId,
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
                        userId,
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

        userActivityRepository.save(userId, expectedActivityList);

        List<Activity> actualActivityList = userActivityRepository.fetch(userId);

        Assertions.assertEquals(expectedActivityList.toString(), actualActivityList.toString());
    }

    @Test
    public void updateTest() throws IOException {

        Instant currentTime = Instant.now();
        List<Activity> newActivityList = new ArrayList<>();

        newActivityList.add(
                new BurnActivity(
                        UUID.randomUUID().toString(),
                        userId,
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

        userActivityRepository.save(userId, newActivityList);

        List<Activity> actualActivityList = userActivityRepository.fetch(userId);

        Assertions.assertEquals(expectedActivityList.toString(), actualActivityList.toString());
    }

    @Test
    public void deleteTest() throws IOException {
        userActivityRepository.delete("mandy@gmail.com");

        Assertions.assertTrue(userActivityRepository.fetch("mandy@gmail.com").isEmpty());
    }

    @Test
    public void fetchTest() throws IOException {
        List<Activity> actualActivityList = userActivityRepository.fetch(userId);
        Assertions.assertEquals(activityList.toString(), actualActivityList.toString());
    }

    @Test
    public void fetchFilterByTypeTest() throws IOException {
        List<Activity> actualActivityList = userActivityRepository.fetch(userId, BurnActivityType.RUNNING);
        Assertions.assertEquals(1, actualActivityList.size());
    }

    @Test
    public void fetchByDateRangeTest() throws IOException {
        Instant currentTime = Instant.now();

        List<Activity> actualActivityList = userActivityRepository.fetch(userId, new DateRange(
                currentTime.minus(48, ChronoUnit.HOURS),
                currentTime.minus(25, ChronoUnit.HOURS)
        ));

        Assertions.assertEquals(1, actualActivityList.size());
    }

    @AfterEach
    public void tearDown() throws IOException {
        userActivityRepository.delete("billy@gmail.com");
        userActivityRepository.delete("mandy@gmail.com");
        userActivityRepository.delete("lex@gmail.com");

        try {
            Connection connection = DriverManager.getConnection(
                    dbConnectionProperties.dbUrl()
            );

            Statement stmt = connection .createStatement();
            stmt.execute("DROP ALL OBJECTS");

            connection.commit();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getBasePath() {
        String path = "src/test/resources/data";

        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        System.out.println(absolutePath);
        return Path.of(path);
    }
}
