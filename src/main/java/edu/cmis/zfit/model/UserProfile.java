package edu.cmis.zfit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record UserProfile(
        String id,
        LocalDate birthDate,
        Gender gender,
        String firstName,
        String lastName) {

    @JsonIgnore
    public int getAge() {
        return (int) ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }
}
