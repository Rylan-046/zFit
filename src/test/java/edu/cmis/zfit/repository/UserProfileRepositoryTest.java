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
    private UserProfileFileRepository userFileRepository;
    private UserProfile userProfile;

    @BeforeEach
    public void setup() throws IOException {
        System.out.println("**** BEGIN SETUP ****");
        System.out.println(getBasePath());

        userFileRepository = new UserProfileFileRepository(getBasePath());

        userProfile = new UserProfile(
                "mandy@gmail.com",
                LocalDate.parse("1999-04-01"),
                Gender.FEMALE,
                "Mandy",
                "Jenkins"
        );

        userFileRepository.save(userProfile);
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

        userFileRepository.save(userProfileExpected);

        UserProfile userProfileActual = userFileRepository.fetchById(userProfileExpected.id());

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

        userFileRepository.save(expectedUserProfile);

        UserProfile userProfileActual = userFileRepository.fetchById("mandy@gmail.com");

        Assertions.assertEquals(expectedUserProfile, userProfileActual);
    }

    @Test
    public void deleteTest() throws IOException {
        userFileRepository.delete("mandy@gmail.com");

         Assertions.assertThrows(
                IOException.class,
                () -> userFileRepository.fetchById("mandy@gmail.com"),
                "File doesn't exist."
        );
    }

    /*@Test
    public void fetchAll*/

    @AfterEach
    public void tearDown() throws IOException {
        userFileRepository.delete("lex@gmail.com");
        userFileRepository.delete("mandy@gmail.com");
    }

    private Path getBasePath() {
        String path = "src/test/resources/data";

        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        System.out.println(absolutePath);
        return Path.of(path);
    }
}