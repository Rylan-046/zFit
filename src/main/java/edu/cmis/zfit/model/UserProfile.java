package edu.cmis.zfit.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record UserProfile(
        String id,
        LocalDate birthDate,
        Gender gender,
        String firstName,
        String lastName) {

    public int getAge() {
        return (int) ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }
}
