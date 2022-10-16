package edu.cmis.zfit.model;

import java.time.LocalDate;
import java.util.Objects;

public class UserProfile {
    private String id;
    private LocalDate birthDate;
    private Gender gender;
    private String firstName;
    private String lastName;

    UserProfile() {

    }

    public UserProfile(String id, LocalDate birthDate, Gender gender, String firstName, String lastName) {
        this.id = id;
        this.birthDate = birthDate;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(id, that.id) && Objects.equals(birthDate, that.birthDate) && gender == that.gender && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, birthDate, gender, firstName, lastName);
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id='" + id + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}