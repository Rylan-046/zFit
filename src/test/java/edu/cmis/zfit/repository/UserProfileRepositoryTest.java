package edu.cmis.zfit.repository;

import edu.cmis.zfit.model.Gender;
import edu.cmis.zfit.model.UserProfile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

public class UserProfileRepositoryTest {
    private UserProfileRepository userProfileRepository;
    private UserProfile userProfile;

    private DBConnectionProperties dbConnectionProperties = new DBConnectionProperties(
            "jdbc:h2:mem:zFit;DB_CLOSE_DELAY=-1",
            null,
            null);

    @BeforeEach
    public void setup() throws IOException {
        System.out.println("**** BEGIN SETUP ****");
        System.out.println(getBasePath());

        InitDBRepository.getInstance(dbConnectionProperties).init();

//        userProfileRepository = new UserProfileFileRepository(getBasePath());
        userProfileRepository = new UserProfileDatabaseRepository(dbConnectionProperties);

        userProfile = new UserProfile(
                "mandy@gmail.com",
                LocalDate.parse("1999-04-01"),
                Gender.FEMALE,
                "Mandy",
                "Jenkins"
        );

        userProfileRepository.save(userProfile);
        System.out.println("**** END SETUP ****");
    }

    @Test
    public void createTest() throws IOException {
        UserProfile userProfileExpected = new UserProfile(
                "lex@gmail.com",
                LocalDate.parse("2000-01-01"),
                Gender.MALE,
                "Lex",
                "Smith"
        );

        userProfileRepository.save(userProfileExpected);

        UserProfile userProfileActual = userProfileRepository.fetchById(userProfileExpected.id());

        Assertions.assertEquals(userProfileExpected, userProfileActual);
    }

    @Test
    public void updateTest() throws IOException {

        System.out.println("Maiden name changed to surname: " + userProfile);

        UserProfile expectedUserProfile = new UserProfile (
                userProfile.id(),
                userProfile.birthDate(),
                userProfile.gender(),
                userProfile.firstName(),
                "Monroe"
        );

        userProfileRepository.save(expectedUserProfile);

        UserProfile userProfileActual = userProfileRepository.fetchById("mandy@gmail.com");

        Assertions.assertEquals(expectedUserProfile, userProfileActual);
    }

    @Test
    public void deleteTest() throws IOException {
        userProfileRepository.delete("mandy@gmail.com");

        Assertions.assertNull(userProfileRepository.fetchById("mandy@gmail.com"));
    }

    @AfterEach
    public void tearDown() throws IOException {
        userProfileRepository.delete("lex@gmail.com");
        userProfileRepository.delete("mandy@gmail.com");
    }

    private Path getBasePath() {
        String path = "src/test/resources/data";

        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        System.out.println(absolutePath);
        return Path.of(path);
    }
}