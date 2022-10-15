package edu.cmis.zfit.model;

import java.time.Instant;

public class UserProfile {
    private String id;
    private Instant birthDate;
    private String sex;
    private String firstName;
    private String lastName;

    public String getId() {
        return id;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public String getSex() {
        return sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}